package com.yc.web.util;

import java.lang.reflect.*;
import java.sql.*;
import java.util.*;
import java.util.Map.Entry;

//import oracle.sql.TIMESTAMP;

/**
 * 	结果集封�?
 * 		实体�? 	==> 	�?
 * 		�?个对�?    	==>  	�?条记�?
 * 		属�??		==>		字段
 * 				映射
 * 	组合条件查询
 * 		基于sql语句的动态的拼接条件
 * 		1. 	where 1=1 ==> 无用的查询条�?, 方便拼接sql语句
 * 		2. 	使用List集合保存查询参数
 * 
 * 	日期类型
 * 		Date  ==>  年月�?	==> java.sql.Date	---> 父类 java.util.Date
 * 		datetime   年月日时分秒  ==> java.sql.Timestamp --> 父类 java.util.Date
 * 		timestamp
 * 
 * 		java.sql.Date.valueOf("yyyy-MM-dd"); "20203-3-25";
 * 
 * 	分页查询
 * 
 * 	事务管理
 * 
 * 	DBHelper 封装 : �?�? jdbc 的代�?
 * 	1.加载驱动	只加载一�? ==> 静�??
 * 	2.获取连接
 * 	3.创建语句
 * 	4.执行语句
 * 	5.关闭连接
 * 
 * 	代码冗余 ==> 重复   ==> 提炼 ==> 代码精简
 * 
 * 	DBHelper 用于执行 sql 语句 返回对应的结�?
 * 	1. 增删�?  ==>  executeUpdate  ==> 返回: 数字  int , 增删改的行数
 * 	2. 查询     ==>  executeQuery	 ==> 返回:ResultSet, 
 * 		因为�?要关�?, 应该返回实体�?, 或�?�Map集合
 * 
 */
public class DBHelper {
	
	// 定义共用的连接对�?
	private Connection conn;
	
	private boolean isAutoCommit = true;

	/**
	 * 	类的代码�?:
	 * 	静�?�块: 
	 * 		static {}
	 * 		特点: 会在类被加载到虚拟机�?,执行�?�?(  例如: import �?)
	 * 	实例�?
	 * 		{}
	 * 		特点: 会在对象被创建时执行�?�?, 在构造方法前
	 * 	块不是方�?,不能抛出编译期异�?
	 */
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver"); // 按住 ctrl + 鼠标
		} catch (ClassNotFoundException e) {
			// 异常转型 ==> 异常�?
			// 未捕获的运行期异常将导致程序的终�?
			RuntimeException re = new RuntimeException("数据库驱动加载失�?!", e);
			throw re;
		}
	}
	
	/**
	 * 使用 isAutoCommit 决定是否自动提交
	 * 
	 * 如果是自动提�?, 则意味着每次执行 update 方法都要获取新的连接, 在执行之后关闭连�?
	 * 否则, 不关闭连�?
	 * @param isAutoCommit  自动提交  true
	 */
	public DBHelper(boolean isAutoCommit) {
		this.isAutoCommit = isAutoCommit;
		if(isAutoCommit == false) {
			conn = openConnection();
		}
	}
	
	/**
	 * JDBC 连接默认是自动提�?, 也就是每次执行完增删改都会自动提�?
	 * 无参的构造方�?, 可以注释掉了
	 */
	public DBHelper() {
		// 在构造方法中创建连接
		//conn = openConnection();
	}
	
	// 关闭连接
	public void closeConnection() {
		IOHelper.close(conn);
	}
	
	// 返回连接对象
	public Connection getConn() {
		return conn;
	}

	/**
	 * 	获取连接
	 * @return
	 */
	public Connection openConnection() {
		String url = "jdbc:mysql://127.0.0.1/c85-s2-phm-damai"; // 数据库的地址
		String user = "root"; // 数据的用�?
		String password = "a";
		try {
			if(isAutoCommit) {
				return DriverManager.getConnection(url, user, password);
			} else {
				if(conn == null) {
					// 禁止自动提交
					conn = DriverManager.getConnection(url, user, password);
					conn.setAutoCommit(isAutoCommit);
				}
				return conn;
			}
		} catch (SQLException e) {
			throw new RuntimeException("获取数据库连接失�?!", e);
		}
	}

	/**
	 * 	执行修改数据库的语句
	 * sql = "update emp set ename = ? where empno=?"
	 * update(sql,2,3,)
	 * @param sql		执行的sql语句
	 * @param params	可变参数数组
	 * @return
	 */
	public int update(String sql, Object... params) {
		try {
			// 每次都会通过open方法获取连接
			conn = openConnection();
			System.out.println("SQL: " + sql);
			PreparedStatement ps = conn.prepareStatement(sql);
			// alrt + /
			System.out.println("参数: " + Arrays.toString(params));
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i + 1, params[i]);
			}
			return ps.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("执行SQL语句失败!", e);
		} finally {
			if(isAutoCommit == true) {
				IOHelper.close(conn);
			}
		}
	}

	/**
	 * 	执行查询语句
	 * @param sql
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> query(String sql, Object... params) {
		try {
			conn = openConnection();
			System.out.println("SQL: " + sql);
			PreparedStatement ps = conn.prepareStatement(sql);
			// alrt + /
			System.out.println("参数: " + Arrays.toString(params));
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i + 1, params[i]);
			}
			ResultSet rs = ps.executeQuery();

			// 获取结果集元数据对象, �?(Meta)数据(data): 描述数据的数�?
			ResultSetMetaData rsmd = rs.getMetaData();
			// 创建返回结果对象
			List<Map<String, Object>> ret = new ArrayList<>();
			while (rs.next()) {
				// 创建 map 集合
				/**
				 * 1. HashMap   	无序不重�?
				 * 2 LinkedHashMap, 有序不重�?
				 * 3. TreeMap 		排序不重�?
				 */
				Map<String, Object> row = new LinkedHashMap<>();
				// 获取每一个字段�??, 设置到一个map�?
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					String columnName = rsmd.getColumnName(i + 1);
					Object columnValue = rs.getObject(columnName);
					/**
					 * 判断当前的列值的类型, 如果�? oracle 的TIMESTAMP, 那么就是使用
					 * rs.getTimestamp 重新取�??
					 */
					/*if(columnValue!= null && columnValue instanceof TIMESTAMP) {
						columnValue = rs.getTimestamp(columnName);
					}*/
					row.put(columnName, columnValue);
				}
				// �? map 添加�? ret �?
				ret.add(row);
			}
			return ret;
		} catch (SQLException e) {
			throw new RuntimeException("执行SQL语句失败!", e);
		} finally {
			if(isAutoCommit == true) {
				IOHelper.close(conn);
			}
		}
	}
	
	/**
	 * 	返回值的类型是可变的类型, �?有的集合==> 泛型�?
	 * 	query 方法改�?�成 泛型方法	: 语法的定�?: 在方法前�? <E>
	 * 
	 * @param sql
	 * @param cls		类对�?, 表示 E 类的类对�?, Java 反射�?�?
	 * @param params
	 * @return
	 */
	public <E> List<E> query(String sql, Class<E> cls, Object... params) {
		try {
			conn = openConnection();
			System.out.println("SQL: " + sql);
			PreparedStatement ps = conn.prepareStatement(sql);
			// alrt + /
			System.out.println("参数: " + Arrays.toString(params));
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i + 1, params[i]);
			}
			ResultSet rs = ps.executeQuery();

			// 获取结果集元数据对象, �?(Meta)数据(data): 描述数据的数�?
			ResultSetMetaData rsmd = rs.getMetaData();
			// 创建返回结果对象
			List<E> ret = new ArrayList<>();
			while (rs.next()) {
				// 创建 实体对象集合( 通过反射机制创建实体对象  == new 实体�?()   )
				E e;
				try {
					e = cls.newInstance();
				} catch (Exception e2) {
					// 异常转型
					throw new RuntimeException(e2);
				}
				
				// 通过反射进行属�?��?�的设置
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					try {
						// 根据当前的列名找对应的属�?
						String columnName = rsmd.getColumnName(i+1); // ID, NAME, AUTHER ...
						columnName = columnName.toLowerCase(); // 转小�?
						// 获取该类定义的属�?(包括私有)
						Field field = cls.getDeclaredField(columnName);
						// 获取当前列的�?
						/**
						 *  ID ==> JDBC 数据类型 : BigDecimal 大实�? 表示任意大小的数�?
						 *  	  	    实体类类�?: Long
						 *  .getType 获取属�?�的类型  ==> LONG  String  Integer
						 */
						// 要转换的数�??
						Object destValue = null;
						if(field.getType().equals(Long.class)) {
							destValue = rs.getLong(i+1);
						} else if(field.getType().equals(Integer.class)) {
							destValue = rs.getInt(i+1);
						} else if(field.getType().equals(Double.class)) {
							destValue = rs.getDouble(i+1);
						} else if(field.getType().equals(Byte.class)) {
							destValue = rs.getByte(i+1);
						} else if(field.getType().equals(Boolean.class)) {
							destValue = rs.getBoolean(i+1);
						// 其他数据类型请自行添�?
						} else if(field.getType().equals(Timestamp.class)) {
							// 返回timestamp字段值的方法
							destValue = rs.getTimestamp(i+1);
							/**
							 * 	其他未判断的类型,请自行添�?
							 */
						} else {
							// 使用默认的类�?
							destValue = rs.getObject(i+1);
						}
						// 设置强制访问私有属�??
						field.setAccessible(true);
						// 将�?�设置到该属性中
						field.set(e, destValue);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				// �? map 添加�? ret �?
				ret.add(e);
			}
			return ret;
		} catch (SQLException e) {
			throw new RuntimeException("执行SQL语句失败!", e);
		} finally {
			if(isAutoCommit == true) {
				IOHelper.close(conn);
			}
		}
	}

	/**
	 * 	分页查询: 翻书 
	 * @param sql
	 * @param pageNumber	第几�?	=>  1,2,3 大于0
	 * @param pageSize		每页行数	=>  3,4 大于0
	 * @param params		可变参数数组: 只能放在�?后一个参数位�?
	 * @return
	 */
	public List<Map<String, Object>> queryPage(String sql, int pageNumber, int pageSize, Object... params) {
		// 参数顺序: 原参数数�? , 截止行数, �?始行�?

		// number = 1, size = 5; => begin = 1, end =5
		// number = 2, size = 5; => begin = 6, end = 10
		// number = 2, size = 5; => begin = 11, end = 15

		int begin = (pageNumber - 1) * pageSize + 1;
		int end = pageNumber * pageSize;
		// 创建新参数数�?
		Object[] newParams = new Object[params.length + 2];

		// 数组的复�?: 1,引用复制, 2克隆复制 , 3 system.arraycopy
		System.arraycopy(params, 0, newParams, 0, params.length);

		// 给新的参数数组最�?2个元素赋�?
		newParams[newParams.length - 2] = end;
		newParams[newParams.length - 1] = begin;

		sql = "select *\n" + "  from (select a.*, rownum rn\n" + "          from (" + sql + ") a\n"
				+ "         where rownum <= ?)\n" + " where rn >= ? ";

		// 调用 query 查询方法
		return query(sql, newParams);
	}

	/**
	 * 	作业: 请返回该语句结果集的行数
	 * @param sql
	 * @param params
	 * @return
	 */
	public int count(String sql, Object... params) {
		// select * from emp where ename like '%A%'
		// return query(sql, params).size();
		// 子查�? => select count(*) cnt from (select * from emp where ename like
		// '%A%') ;
		// mysql 的子查询必须加别�?
		sql = "select count(*) cnt from (" + sql + ") a";
		// mysql 的字段名不会被转�? 大写, oracle �?
		Object cnt = query(sql, params).get(0).get("cnt");
		// Object ==> int   强制类型转换 ==> 类型匹配    String =>  int
		// int ret = (int) cnt; // 注定失败 cnt 类型是未�? ??   Integer Long BigDecimal 大实�?
		int ret = Integer.valueOf("" + cnt);
		
		return ret;
	}

	/**
	 * 	作业: 返回结果集中, 第一�?,第一列的�?
	 * 	例如: select count(*) from emp;
	 * @return
	 */
	public Object getValue(String sql, Object...params) {
		List<Map<String,Object>> list = query(sql,params);
		Map<String,Object> row = list.get(0);
		for( Entry<String,Object> entry : row.entrySet() ) {
			return entry.getValue();
		}
		return null;
	}

	public static void main(String[] args) {

		DBHelper dbhelper = new DBHelper();

		// params = {1,2}
		// dbhelper.update("update dept set dname=? where deptno=?", "人力�?", 60);
		// params = {1,2,3}
		// dbhelper.update("update dept set dname=?,loc=? where deptno=?",
		// "人力�?", "衡阳", 60);
		// params = {}
		// dbhelper.update("update dept set dname='�?术部',loc='师院' where
		// deptno=60");
		System.out.println("=========================");
		List<Map<String, Object>> list = dbhelper.query("select * from emp");
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}

		System.out.println("=========================");
		list = dbhelper.query("select * from emp where ename like ?", "%S%");
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}

		System.out.println("=========================");
		list = dbhelper.query("select * from emp where ename like ? " + " and sal > ? ", "%S%", 1250);
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}

		System.out.println("==========1====5===========");
		list = dbhelper.queryPage("select * from emp", 1, 5);
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}

		System.out.println("==========2====5===========");
		list = dbhelper.queryPage("select * from emp", 2, 5);
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}

		System.out.println("==========3====5===========");
		list = dbhelper.queryPage("select * from emp", 3, 5);
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}

		System.out.println("==========1====5====7521=======");
		list = dbhelper.queryPage("select * from emp where empno > ? ", 1, 5, 7521);
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}
	}

}