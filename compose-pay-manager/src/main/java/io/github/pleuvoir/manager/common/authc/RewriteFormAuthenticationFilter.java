package io.github.pleuvoir.manager.common.authc;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import io.github.pleuvoir.manager.common.Const;
import io.github.pleuvoir.manager.common.util.RequestUtil;
import io.github.pleuvoir.manager.exception.KtaAuthenticationException;
import io.github.pleuvoir.manager.model.po.pub.PubLoginLogPO;
import io.github.pleuvoir.manager.model.po.pub.PubUserPO;
import io.github.pleuvoir.manager.service.pub.PubLoginLogService;
import io.github.pleuvoir.manager.service.pub.PubUserService;

public class RewriteFormAuthenticationFilter extends FormAuthenticationFilter {

	@Autowired
	private PubLoginLogService loginLogService;
	@Autowired
	private PubUserService pubUserService;
	
	@Resource(name = "stringRedisTemplate")
	private StringRedisTemplate redisTemplate;

	private static Logger logger = LoggerFactory.getLogger(RewriteFormAuthenticationFilter.class);

	@Override
	protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
		AuthenticationToken token = createToken(request, response);
		if (token == null) {
			String msg = "createToken method implementation returned null. A valid non-null AuthenticationToken "
					+ "must be created in order to execute a login attempt.";
			throw new IllegalStateException(msg);
		}

		try {
			String userName = new String(request.getParameter("username").getBytes("ISO-8859-1"), "UTF-8");
			String kaptcha = new String(request.getParameter("kaptcha").getBytes("ISO-8859-1"), "UTF-8");

			HttpSession session = ((HttpServletRequest) request).getSession();
			String kaptcha1 = (String) session.getAttribute(Const.SESSION_KAPTCHA);
			boolean f = StringUtils.isNotBlank(kaptcha) && StringUtils.equalsIgnoreCase(kaptcha, kaptcha1);
				if (!f) {
					throw new KtaAuthenticationException("验证码错误");
				}
			PubUserPO userPO = pubUserService.getUser(userName);
			if (userPO == null) {
				throw new UnknownAccountException("用户不存在");
			}

			Subject subject = getSubject(request, response);
			subject.login(token);
			return onLoginSuccess(token, subject, request, response);

		} catch (AuthenticationException e) {
			return onLoginFailure(token, e, request, response);
		}
	}

	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
			ServletResponse response) throws Exception {

		// 日志
		PubLoginLogPO log = new PubLoginLogPO();
		log.setIp(RequestUtil.ipAddress((HttpServletRequest) request));
		log.setAgent(RequestUtil.userAgent((HttpServletRequest) request, 0, 200));
		loginLogService.success(subject.getPrincipal().toString(), log);

		// session
		subject.getSession().setAttribute(Const.SESSION_USER, subject.getPrincipal());
		WebUtils.issueRedirect(request, response, getSuccessUrl());

		return false;
	}

	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
			ServletResponse response) {

		// 日志
		PubLoginLogPO log = new PubLoginLogPO();
		log.setIp(RequestUtil.ipAddress((HttpServletRequest) request));
		log.setAgent(RequestUtil.userAgent((HttpServletRequest) request, 0, 200));
		if (e instanceof IncorrectCredentialsException) {
			log.setRemark("密码错误");
		} else {
			log.setRemark(e.getMessage());
		}
		loginLogService.fail(String.valueOf(token.getPrincipal()), log);

		if (logger.isDebugEnabled()) {
			logger.debug("登录失败", e);
		} else if (logger.isInfoEnabled()) {
			logger.info("登录失败，{}：{}", e.getClass().getName(), e.getMessage());
		}
		setFailureAttribute(request, e);
		// login failed, let request continue back to the login page:
		return true;
	}

}
