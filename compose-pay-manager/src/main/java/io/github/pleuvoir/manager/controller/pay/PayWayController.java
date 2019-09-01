package io.github.pleuvoir.manager.controller.pay;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.github.pleuvoir.manager.common.taglib.AlertMessage;
import io.github.pleuvoir.manager.common.util.SessionUtil;
import io.github.pleuvoir.manager.exception.BusinessException;
import io.github.pleuvoir.manager.model.dto.pub.PubUserFormDTO;
import io.github.pleuvoir.manager.model.po.pub.PubRolePO;
import io.github.pleuvoir.manager.model.po.pub.PubUserPO;
import io.github.pleuvoir.manager.model.vo.ResultMessageVO;
import io.github.pleuvoir.manager.model.vo.pub.PubUserListVO;
import io.github.pleuvoir.manager.service.pub.PubRoleService;
import io.github.pleuvoir.manager.service.pub.PubUserService;

import java.util.List;

/**
 * 支付方式
 * @author pleuvoir
 *
 */
@Controller
@RequestMapping("/payWay")
public class PayWayController {
	
	@Autowired
	private PubUserService userService;
	@Autowired
	private PubRoleService roleService;

	/**
	 * 查询页 
	 */
	@RequiresPermissions("pubUser:list")
	@RequestMapping("/list")
	public String list() {
		return "/pub/pubUser/list";
	}
	
	/**
	 * 查询结果 
	 */
	@RequiresPermissions("pubUser:list")
	@RequestMapping("/query")
	@ResponseBody
	public PubUserListVO query(PubUserFormDTO form) {
		return userService.queryList(form);
	}
	
	/**
	 * 新增页面
	 */
	@RequiresPermissions("pubUser:add")
	@RequestMapping("/create")
	public ModelAndView create() {
		ModelAndView view = new ModelAndView("/pub/pubUser/create");
		String username = SessionUtil.getUserName();
		view.addObject("roles", roleService.findRliesByCreateBy(username));
		return view;
	}
	
	/**
	 * 保存用户 
	 */
	@RequiresPermissions("pubUser:add")
	@PostMapping("/save")
	public String save(@Validated PubUserPO po, BindingResult bindingResult, RedirectAttributes ra) {
		if(bindingResult.hasErrors()) {
			if(bindingResult.getGlobalError()!=null) {
				AlertMessage.error(bindingResult.getGlobalError()).flashAttribute(ra);
			}else if(bindingResult.getFieldError()!=null) {
				AlertMessage.error(bindingResult.getFieldError()).flashAttribute(ra);
			}
			return "redirect:/pubUser/create";
		}else {
			try {
				userService.save(po);
				AlertMessage.success("保存用户成功").flashAttribute(ra);
			} catch (BusinessException e) {
				AlertMessage.error(e.getMessage()).flashAttribute(ra);
			}
		}
		return "redirect:/pubUser/list";
	}
	
	/**
	 * 修改页面
	 */
	@RequiresPermissions("pubUser:edit")
	@RequestMapping("/edit")
	public ModelAndView edit(String id) {
		ModelAndView view = new ModelAndView("/pub/pubUser/edit");
		String username = SessionUtil.getUserName();
		PubUserPO po = userService.getUserById(id);
		view.addObject("user", po);
		view.addObject("roles", roleService.findRliesByCreateBy(username));
		view.addObject("alreadyRoles", formatAlreadyRoles(po.getUsername()));
		return view;
	}
	
	private String formatAlreadyRoles(String username) {
		StringBuffer sbf = new StringBuffer();
		List<PubRolePO> list = roleService.findRolesByUsername(username);
		for(PubRolePO po : list) {
			sbf.append(po.getId());
			sbf.append(",");
		}
		return sbf.toString();
		
	}
	
	/**
	 * 修改用户 
	 */
	@RequiresPermissions("pubUser:edit")
	@PostMapping("/update")
	public String edit(@Validated PubUserPO po, BindingResult bindingResult, RedirectAttributes ra) {
		if(bindingResult.hasErrors()) {
			if(bindingResult.getGlobalError()!=null) {
				AlertMessage.error(bindingResult.getGlobalError()).flashAttribute(ra);
			}else if(bindingResult.getFieldError()!=null) {
				AlertMessage.error(bindingResult.getFieldError()).flashAttribute(ra);
			}
			return "redirect:/pubUser/edit";
		}else {
			try {
				userService.edit(po);
				AlertMessage.success("修改用户成功").flashAttribute(ra);
			} catch (BusinessException e) {
				AlertMessage.error(e.getMessage()).flashAttribute(ra);
			}
		}
		return "redirect:/pubUser/list";
	}
	
	/**
	 * 删除用户
	 */
	@RequiresPermissions("pubUser:delete")
	@PostMapping("/delete")
	@ResponseBody
	public ResultMessageVO<?> delete(String id) {
		if(StringUtils.isBlank(id)) {
			return ResultMessageVO.error("用户id为空");
		}else {
			try {
				userService.delete(id);
				return ResultMessageVO.success("用户已删除");
			} catch (BusinessException e) {
				return ResultMessageVO.error(e.getMessage());
			}
		}
	}
	
	/**
	 * 判断用户是否存在
	 * @param username
	 * @return
	 */
	@RequestMapping("/isExis")
	@ResponseBody
	public boolean isExis(String username) {
		PubUserPO user = userService.getUser(username);
		if(user==null) {
			return true;
		}
		return false;
	}
	
}
