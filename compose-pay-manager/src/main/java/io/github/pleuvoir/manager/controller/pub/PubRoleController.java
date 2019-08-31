package io.github.pleuvoir.manager.controller.pub;


import com.alibaba.fastjson.JSONArray;

import io.github.pleuvoir.manager.common.taglib.AlertMessage;
import io.github.pleuvoir.manager.exception.BusinessException;
import io.github.pleuvoir.manager.model.dto.pub.PubPermissionFormDTO;
import io.github.pleuvoir.manager.model.dto.pub.PubRoleFormDTO;
import io.github.pleuvoir.manager.model.po.pub.PubRolePO;
import io.github.pleuvoir.manager.model.vo.ResultMessageVO;
import io.github.pleuvoir.manager.model.vo.pub.PubRoleListVO;
import io.github.pleuvoir.manager.service.pub.PubRoleService;

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

import java.util.List;
import java.util.Map;

/**
 * 角色管理
 * @author LaoShu
 *
 */
@Controller
@RequestMapping("/pubRole")
@RequiresRoles("100") //普通角色授权也不可操作
public class PubRoleController {
	
	@Autowired
	private PubRoleService roleService;
	
	/**
	 * 查询页 
	 */
	@RequiresPermissions("pubRole:list")
	@RequestMapping("/list")
	public String list() {
		return "/pub/pubRole/list";
	}
	

	/**
	 * 查询结果 
	 */
	@RequiresPermissions("pubRole:list")
	@RequestMapping("/query")
	@ResponseBody
	public PubRoleListVO query(PubRoleFormDTO form) {
		return roleService.queryList(form);     
	}
	
	/**
	 * 新增页面
	 */
	@RequiresPermissions("pubRole:add")
	@RequestMapping("/create")
	public String create() {
		return "/pub/pubRole/create";
	}
	
	/**
	 * 保存角色
	 */
	@RequiresPermissions("pubRole:add")
	@PostMapping("/save")
	public String save(@Validated PubRolePO po, BindingResult bindingResult, RedirectAttributes ra) {
		if(bindingResult.hasErrors()) {
			if(bindingResult.getGlobalError()!=null) {
				AlertMessage.error(bindingResult.getGlobalError()).flashAttribute(ra);
			}else if(bindingResult.getFieldError()!=null) {
				AlertMessage.error(bindingResult.getFieldError()).flashAttribute(ra);
			}
			return "redirect:/pubRole/create";
		}else {
			try {
				roleService.save(po);
				AlertMessage.success("保存角色成功").flashAttribute(ra);
			} catch (BusinessException e) {
				AlertMessage.error(e.getMessage()).flashAttribute(ra);
			}
		}
		return "redirect:/pubRole/list";
	}
	
	/**
	 * 修改页面
	 */
	@RequiresPermissions("pubRole:edit")
	@RequestMapping("/edit")
	public ModelAndView edit(String id) {
		ModelAndView view = new ModelAndView("/pub/pubRole/edit");
		PubRolePO po = roleService.getById(id);
		view.addObject("role", po);
		return view;
	}
	
	/**
	 * 修改角色
	 */
	@RequiresPermissions("pubRole:edit")
	@PostMapping("/update")
	public String edit(@Validated PubRolePO po, BindingResult bindingResult, RedirectAttributes ra) {
		if(bindingResult.hasErrors()) {
			if(bindingResult.getGlobalError()!=null) {
				AlertMessage.error(bindingResult.getGlobalError()).flashAttribute(ra);
			}else if(bindingResult.getFieldError()!=null) {
				AlertMessage.error(bindingResult.getFieldError()).flashAttribute(ra);
			}
			return "redirect:/pubRole/edit";
		}else {
			try {
				roleService.edit(po);
				AlertMessage.success("修改角色成功").flashAttribute(ra);
			} catch (BusinessException e) {
				AlertMessage.error(e.getMessage()).flashAttribute(ra);
			}
		}
		return "redirect:/pubRole/list";
	}
	
	/**
	 * 删除角色
	 */
	@RequiresPermissions("pubRole:delete")
	@PostMapping("/delete")
	@ResponseBody
	public ResultMessageVO<?> delete(String id) {
		if(StringUtils.isBlank(id)) {
			return ResultMessageVO.error("角色id为空");
		}else {
			try {
				roleService.delete(id);
				return ResultMessageVO.success("角色已删除");
			} catch (BusinessException e) {
				return ResultMessageVO.error(e.getMessage());
			}
		}
	}
	
	/**
	 * 权限分配页面
	 */
	@RequiresPermissions("pubRole:permission")
	@RequestMapping("/permission")
	public ModelAndView permission(String id) {
		ModelAndView view = new ModelAndView("/pub/pubRole/permission");
		PubRolePO po = roleService.getById(id);
		view.addObject("role", po);
		List<Map<String,Object>> treeList = roleService.getPermissionTree(id);
		view.addObject("treeStr",JSONArray.toJSON(treeList).toString());
		return view;
	}
	
	/**
	 * 权限分配保存
	 */
	@RequiresPermissions("pubRole:permission")
	@RequestMapping("/permissionSave")
	public String permissionSave(PubPermissionFormDTO dto, RedirectAttributes ra) {
		try {
			roleService.permissionSave(dto);
			AlertMessage.success("权限分配成功").flashAttribute(ra);
		} catch (BusinessException e) {
			AlertMessage.error(e.getMessage()).flashAttribute(ra);
		}
		return "redirect:/pubRole/list";
	}
	
}
