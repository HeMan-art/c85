package com.yc.damai.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;
import org.junit.Test;

import com.yc.web.Dao.DmCategoryMapper;
import com.yc.web.Dao.DmOrderitemMapper;
import com.yc.web.Dao.DmOrdersMapper;
import com.yc.web.Dao.DmProductMapper;
import com.yc.web.bean.DmCategory;
import com.yc.web.bean.DmOrderitem;
import com.yc.web.bean.DmOrders;
import com.yc.web.bean.DmProduct;

public class MapperTest {
	
	private SqlSession session ;
	
	//动态块
	{
		try {
			// mybatis 配置文件
			String resource = "mybatis.xml";
			// 读入配置文件
			InputStream inputStream = Resources.getResourceAsStream(resource);
			// 构建会话工厂 ==> 23 设计模式 工厂模式
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			// 开启会话
			session = sqlSessionFactory.openSession();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	// 测试方法必须加 test 注解
	@Test
	public void test1() throws IOException {
		// session.selectList("xml接口的命名空间 + 英文点号 + 查询的sql的id");
		List<DmProduct> list = session.selectList("com.yc.damai.dao.DmProductMapper.selectAll");
		/**
		 * 	使用断言进行结果的判断
		 *  true 表示的期望值
		 *  list.size()>0 是实际值
		 */
		Assert.assertEquals(true, list.size() > 0);
		for(DmProduct dp : list) {
			System.out.println(dp);
		}

	}
	
	@Test
	public void test2() throws IOException {
		DmCategory dc = new DmCategory();
		dc.setCname("测试分类");
		dc.setPid(1);
		// session.insert("com.yc.damai.dao.DmProductMapper.insert",dc);
		// 获取映射接口实现类 (核心技术) 动态代理(JDK)
		DmCategoryMapper mapper = session.getMapper(DmCategoryMapper.class);
		mapper.insert(dc);
		// 不commit, 会话会在关闭自动回滚
		session.commit();
		session.close();
	}
	
	@Test
	public void test3() throws IOException {
		DmCategory dc = new DmCategory();
		dc.setId(44);
		dc.setCname("修改后的测试分类");
		dc.setPid(1);
		//session.update("com.yc.damai.dao.DmProductMapper.update",dc);
		DmCategoryMapper mapper = session.getMapper(DmCategoryMapper.class);
		mapper.update(dc);
		// 不commit, 会话会在关闭自动回滚
		session.commit();
		session.close();
	}
	
	@Test
	public void test4() throws IOException {
		/*DmCategory dc = new DmCategory();
		dc.setId(44);
		dc.setCname("修改后的测试分类");
		dc.setPid(1);*/
		//session.delete("com.yc.damai.dao.DmProductMapper.delete",dc);
		DmCategoryMapper mapper = session.getMapper(DmCategoryMapper.class);
		mapper.delete(44);
		// 不commit, 会话会在关闭自动回滚
		session.commit();
		session.close();
	}
	
	@Test
	public void test5() throws IOException {
		/**
		 * 1. 先查出一个订单明细记录
		 * 2. 查出该订单明细对应的商品信息
		 */
		/*DmOrderitemMapper dom = session.getMapper(DmOrderitemMapper.class);
		DmProductMapper dpm = session.getMapper(DmProductMapper.class);
		DmOrderitem doi = dom.selectById(59);
		DmProduct dp = dpm.selectById(doi.getPid());*/
		
		/**
		 * 测试驱动开发  ==> 先写好所有的测试代码 ==> 再业务代码 
		 */
		DmOrderitemMapper dom = session.getMapper(DmOrderitemMapper.class);
		DmOrderitem doi = dom.selectById(59);
		// java黑科技  ==> 反射 ==> 动态代理技术
		DmProduct dp = doi.getDmProduct();  // 调用 dmProduct 属性的get方法
		
		System.out.println(dp);
		session.close();
	}
	
	@Test
	public void test6() throws IOException {
		DmCategoryMapper mapper = session.getMapper(DmCategoryMapper.class);
		List<DmCategory> dcList = mapper.selectAll();
		System.out.println("===============1================");
		DmCategory dc = dcList.get(1);
		System.out.println("===============2================");
		Assert.assertEquals("鞋靴箱包", dc.getCname());
		System.out.println("================3================");
		Assert.assertEquals(6, dc.getChildren().size());
		System.out.println("================4================");
	}
	
	@Test
	public void test7() throws IOException {
		DmProductMapper mapper= session.getMapper(DmProductMapper.class);
		System.out.println("=================================");
		mapper.selectByObj(null);
		DmProduct dp = new DmProduct();
		System.out.println("===================================");
		mapper.selectByObj(dp);
		
		dp.setPname("测试");
		System.out.println("===================================");
		mapper.selectByObj(dp);
		
		dp.setPdesc("测试描述");
		System.out.println("===================================");
		mapper.selectByObj(dp);
		
		dp.setIsHot(-1);
		System.out.println("===================================");
		mapper.selectByObj(dp);
		
		dp.setIsHot(1);
		System.out.println("===================================");
		mapper.selectByObj(dp);
		
	}
	
	public void test8() throws IOException {
		DmProductMapper mapper= session.getMapper(DmProductMapper.class);
        int[] cids = {1,2,3};
        mapper.selectByCids(cids);
	}
	
	public void test9() throws IOException {
		DmProductMapper mapper= session.getMapper(DmProductMapper.class);
		DmProduct dp = new DmProduct();
		dp.setId(1);
		dp.setMarketPrice(885d);
		
		mapper.update(dp);
		//
		DmProduct dbdp = mapper.selectById(1);
		
		Assert.assertEquals((Double)885d,dbdp.getMarketPrice());
		Assert.assertEquals((Double)228d,dbdp.getShopPrice());
		Assert.assertEquals("韩版连帽加厚毛衣外套",dbdp.getPname());
		
		
		/**
		 * 解决方案
		 * 1.在update之前先将数据库中该记录的值全部查询出来，设置到db中
		 *      每次修改都是更新所有字段
		 * 2.动态生成更新sql，只更新不为null
		 */
		
	}
	
	@Test
	public void test10() throws IOException {
		DmOrdersMapper dosm = session.getMapper(DmOrdersMapper.class);
		DmOrderitemMapper doim = session.getMapper(DmOrderitemMapper.class);
		
		//生成订单业务
		//定义购买的2个订单明细
		DmOrderitem doi1 = new DmOrderitem();
		doi1.setPid(1);
		doi1.setCount(1);
		doi1.setTotal(100d);
		
		DmOrderitem doi2 = new DmOrderitem();
		doi2.setPid(2);
		doi2.setCount(1);
		doi2.setTotal(200d);
		
		DmOrders dos = new DmOrders();
		dos.setTotal(300d);
		dos.setAid(1);
		dos.setState(1);
		dos.setUid(1);
		
		try {
		//写订单表
		dosm.insert(dos);
		
		doi1.setOid(dos.getId());
	//	doi2.setOid(dos.getId());
		//写订单明细表
		doim.insert(doi1);
		doim.insert(doi2);
		//提交事务
		session.commit();
		}catch (Exception e) {
			e.printStackTrace();
			session.rollback();
		}finally {
			session.close();
		}
	}
	
	public void test11() throws IOException {
		DmProductMapper mapper= session.getMapper(DmProductMapper.class);
        int[] cids = {1,2,3};
        mapper.selectByCids(cids);
        
        mapper.selectByCids(cids);
        mapper.selectByCids(cids);
	}
}