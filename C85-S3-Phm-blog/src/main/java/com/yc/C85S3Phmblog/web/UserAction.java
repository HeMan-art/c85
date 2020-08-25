
package com.yc.C85S3Phmblog.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yc.C85S3Phmblog.bean.Result;
import com.yc.C85S3Phmblog.bean.User;
import com.yc.C85S3Phmblog.biz.BizException;
import com.yc.C85S3Phmblog.biz.UserBiz;



@Controller // 默认控制器方法是执行页面跳转
public class UserAction {

	@Resource
	private UserBiz ubiz;

	/**
	 * 	注册 : 表单提交 ==> 页面跳转
	 * 	Errors 报错所有的验证错误信息,   默认会被推送页面
	 */
	@PostMapping("reg.do")
	public String register(@Valid User user, Errors errors, Model m) {
		if (errors.hasErrors()) {
			m.addAttribute("errors", asMap(errors));
			m.addAttribute("user", user);
			return "reg";
		}
		try {
			ubiz.register(user);
		} catch (BizException e) {
			e.printStackTrace();
			errors.rejectValue("account", "account", e.getMessage());
			m.addAttribute("errors", asMap(errors));
			m.addAttribute("user", user);
			return "reg";
		}
		// index 请求转发方式跳转到 index
		// 使用响应重定向方式跳转
		return "redirect:/"; 
	}

	@GetMapping("toreg")
	public String toreg() {
		return "reg";
	}

	/**
	 * 	登录: Ajax提交 ==> Vue
	 */
	@PostMapping("login.do")
	// 是在 Controller 使用 ==> 方法返回视图名 
	// @ResponseBody 表示该方法的返回值是json数据
	@ResponseBody
	public Result login(@Valid User user, Errors errors, HttpSession session) {
		try {
			if (errors.hasFieldErrors("account") || errors.hasFieldErrors("pwd")) {
				// 将错误结果转换成 Map集合再返回
				Result res = new Result(0, "验证错误!", asMap(errors));
				return res;
			}
			User dbuser = ubiz.login(user);
			session.setAttribute("loginedUser", dbuser);
			return new Result(1, "登录成功!", dbuser);
		} catch (BizException e) {
			e.printStackTrace();
			return new Result(e.getMessage());
		}
	}

	/**
	 * 	将所有的字段验证错写入到一个map
	 * @param errors
	 * @return
	 */
	private Map<String, String> asMap(Errors errors) {
		if (errors.hasErrors()) {
			Map<String, String> ret = new HashMap<String, String>();
			for (FieldError fe : errors.getFieldErrors()) {
				ret.put(fe.getField(), fe.getDefaultMessage());
			}
			return ret;
		} else {
			return null;
		}
	}

}