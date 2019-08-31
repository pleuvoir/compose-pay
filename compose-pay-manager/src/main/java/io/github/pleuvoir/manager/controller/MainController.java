package io.github.pleuvoir.manager.controller;

import java.util.List;

import javax.servlet.ServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import io.github.pleuvoir.manager.common.enums.MenuUser;
import io.github.pleuvoir.manager.common.taglib.AlertMessage;
import io.github.pleuvoir.manager.common.util.SessionUtil;
import io.github.pleuvoir.manager.model.dto.pub.PubLoginPwdDTO;
import io.github.pleuvoir.manager.model.po.pub.PubRolePO;
import io.github.pleuvoir.manager.model.vo.pub.PubMenuView;
import io.github.pleuvoir.manager.service.pub.PubMenuService;
import io.github.pleuvoir.manager.service.pub.PubRoleService;
import io.github.pleuvoir.manager.service.pub.PubUserService;

/**
 * 主页面
 */
@Controller
@RequestMapping("main")
public class MainController {
	
	@Autowired
	private PubMenuService menuService;
	@Autowired
	private PubRoleService roleService;
	@Autowired
	private PubUserService userService;
	
	@RequestMapping
	public ModelAndView index(String refresh){
		ModelAndView v = new ModelAndView("main");
		String username = SessionUtil.getUserName();
		if(StringUtils.isNotBlank(username)) {
			List<PubMenuView> menuViewList = menuService.menuViewList(username,MenuUser.MAIN);
			v.addObject("menuView", CollectionUtils.isEmpty(menuViewList) ? null : menuViewList.get(0));
			v.addObject("username", username);
			v.addObject("roleName", formatRoleName(username));
			v.addObject("platform", "平台");
		}
		return v;
	}
	
	@RequestMapping("editPwd")
	@ResponseBody
	public AlertMessage editPwd(@Validated PubLoginPwdDTO dto,ServletRequest request, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			if(bindingResult.getGlobalError()!=null) {
				return AlertMessage.error(bindingResult.getGlobalError());
			}else if(bindingResult.getFieldError()!=null) {
				return AlertMessage.error(bindingResult.getFieldError());
			}
		}else {
			try {
				userService.editPwd(dto,request);
				return AlertMessage.success("修改密码成功");
			} catch (Exception e) {
				return AlertMessage.error(e.getMessage());
			}
		}
		return null;
	}
	
	private String formatRoleName(String username) {
		StringBuffer stb = new StringBuffer();
		List<PubRolePO> roleList = roleService.findRolesByUsername(username);
		for(PubRolePO rolePo : roleList) {
			stb.append(rolePo.getName());
			stb.append(",");
		}
		return StringUtils.substring(stb.toString(), 0,stb.length()-1);
	}
	
}
