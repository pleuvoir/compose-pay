package io.github.pleuvoir.manager.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.code.kaptcha.Producer;

import io.github.pleuvoir.manager.common.Const;
import io.github.pleuvoir.manager.common.taglib.AlertMessage;
import io.github.pleuvoir.manager.exception.KtaAuthenticationException;

@Controller
@RequestMapping
public class AuthController {
	
	@Autowired
	private Producer kaptcha;

	/**
	 * 登录页面
	 */
	@RequestMapping("/auth")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("login");
		return mv;
	}

	/**
	 * 登录失败
	 */
	@RequestMapping("/login")
	public String login(HttpServletRequest request, RedirectAttributes ra) {
		Object error = request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		if (KtaAuthenticationException.class.getName().equals(error)) {
			AlertMessage.error("验证码错误").flashAttribute(ra);
		}else if (ExcessiveAttemptsException.class.getName().equals(error)) {
			AlertMessage.error("超过最大尝试次数").flashAttribute(ra);
		} else if (LockedAccountException.class.getName().equals(error)) {
			AlertMessage.error("用户已锁定").flashAttribute(ra);
		} else if (UnknownAccountException.class.getName().equals(error)) {
			AlertMessage.error("用户不存在").flashAttribute(ra);
		} else if (IncorrectCredentialsException.class.getName().equals(error)) {
			AlertMessage.error("用户名或密码有误").flashAttribute(ra);
		} else if (AuthenticationException.class.getName().equals(error)) {
			AlertMessage.error("登录失败").flashAttribute(ra);
		} else if (DisabledAccountException.class.getName().equals(error)) {
			AlertMessage.error("用户账号不可用").flashAttribute(ra);
		}
		// 解决已登录用户切换至登录页重复登录而导致页面一直在登录页的问题
		if (SecurityUtils.getSubject().isAuthenticated()) {
			return "redirect:/main";
		}
		return "redirect:/auth";
	}

	/**
	 * 权限不足
	 */
	@RequestMapping("/refuse")
	public ModelAndView refuse() {
		ModelAndView mv = new ModelAndView("refuse");
		return mv;
	}


	/**
	 * 生成验证码
	 */
	@RequestMapping("/code")
	public void codeImage(HttpSession session, HttpServletResponse resp) throws IOException {
		// 禁止缓存
		resp.setDateHeader("Expires", 0);
		resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		resp.addHeader("Cache-Control", "post-check=0, pre-check=0");
		resp.setHeader("Pragma", "no-cache");
		resp.setContentType("image/jpeg");
		String capText = kaptcha.createText();
		session.setAttribute(Const.SESSION_KAPTCHA, capText);
		BufferedImage bi = kaptcha.createImage(capText);
		ImageIO.write(bi, "jpg", resp.getOutputStream());
	}

}
