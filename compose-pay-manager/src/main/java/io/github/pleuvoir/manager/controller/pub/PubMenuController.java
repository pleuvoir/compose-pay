package io.github.pleuvoir.manager.controller.pub;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.github.pleuvoir.manager.common.enums.MenuUser;
import io.github.pleuvoir.manager.common.taglib.AlertMessage;
import io.github.pleuvoir.manager.common.util.SessionUtil;
import io.github.pleuvoir.manager.exception.BusinessException;
import io.github.pleuvoir.manager.model.dto.pub.PubMenuFormDTO;
import io.github.pleuvoir.manager.model.po.pub.PubMenuPO;
import io.github.pleuvoir.manager.model.vo.ResultMessageVO;
import io.github.pleuvoir.manager.model.vo.pub.PubMenuListVO;
import io.github.pleuvoir.manager.model.vo.pub.PubMenuView;
import io.github.pleuvoir.manager.service.pub.PubMenuService;
import io.github.pleuvoir.manager.service.pub.PubPermissionsService;

import java.util.List;

/**
 * 菜单管理
 * @author LaoShu
 *
 */
@Controller
@RequestMapping("/pubMenu")
@RequiresRoles("100") //普通角色授权也不可操作
public class PubMenuController {
	
	@Autowired
	private PubMenuService menuService;
	@Autowired
	private PubPermissionsService permissionsService;
	
	/**
	 * 查询页 
	 */
	@RequiresPermissions("pubMenu:list")
	@RequestMapping("/list")
	public String list() {
		return "/pub/pubMenu/list";
	}
	
	/**
	 * 查询结果 
	 */
	@RequiresPermissions("pubMenu:list")
	@RequestMapping("/query")
	@ResponseBody
	public PubMenuListVO query(PubMenuFormDTO form) {
		return menuService.queryList(form);
	}
	
	/**
	 * 新增页面
	 */
	@RequiresPermissions("pubMenu:add")
	@RequestMapping("/create")
	public String create() {
		return "/pub/pubMenu/create";
	}
	
	/**
	 * 保存菜单
	 */
	@RequiresPermissions("pubMenu:add")
	@PostMapping("/save")
	public String save(@Validated PubMenuPO po, RedirectAttributes ra) {
		try {
			menuService.save(po);
			AlertMessage.success("保存菜单成功").flashAttribute(ra);
		} catch (BusinessException e) {
			AlertMessage.error(e.getMessage()).flashAttribute(ra);
		}
		return "redirect:/pubMenu/list";
	}
	
	/**
	 * 修改页面
	 */
	@RequiresPermissions("pubMenu:edit")
	@RequestMapping("/edit")
	public ModelAndView edit(String id) {
		ModelAndView view = new ModelAndView("/pub/pubMenu/edit");
		PubMenuPO po = menuService.getById(id);
		view.addObject("menu", po);
		return view;
	}
	
	/**
	 * 修改菜单
	 */
	@RequiresPermissions("pubMenu:edit")
	@PostMapping("/update")
	public String update(@Validated PubMenuPO po, RedirectAttributes ra) {
		try {
			menuService.update(po);
			AlertMessage.success("修改菜单成功").flashAttribute(ra);
		} catch (BusinessException e) {
			AlertMessage.error(e.getMessage()).flashAttribute(ra);
		}
		return "redirect:/pubMenu/list";
	}
	
	/**
	 * 删除菜单
	 */
	@RequiresPermissions("pubMenu:delete")
	@PostMapping("/delete")
	@ResponseBody
	public ResultMessageVO<?> delete(String id) {
		if(StringUtils.isBlank(id)) {
			return ResultMessageVO.error("菜单id为空");
		}else {
			try {
				menuService.delete(id);
				return ResultMessageVO.success("菜单已删除");
			} catch (BusinessException e) {
				return ResultMessageVO.error(e.getMessage());
			}
			
		}
	}
	
	/**
	 * 排序页面
	 */
	@RequiresPermissions("pubMenu:sort")
	@RequestMapping("/sort")
	public ModelAndView sort() {
		ModelAndView view = new ModelAndView("/pub/pubMenu/sort");
		String username = SessionUtil.getUserName();
		if(StringUtils.isNotBlank(username)) {
			List<PubMenuView> menuViewList = menuService.menuViewList(username,MenuUser.MENU_SERVICE);
			view.addObject("menuView", menuViewList.get(0));
		}
		return view;
	}
	
	/**
	 * 排序保存
	 */
	@RequiresPermissions("pubMenu:sort")
	@PostMapping("/sortSave")
	public String sortSave(String jsonStr, RedirectAttributes ra) {
		try {
			menuService.sort(jsonStr);
			AlertMessage.success("修改排序成功").flashAttribute(ra);
		} catch (BusinessException e) {
			AlertMessage.error(e.getMessage()).flashAttribute(ra);
		}
		return "redirect:/pubMenu/list";
	}
	
	/**
	 * 权限绑定页面
	 */
	@RequiresPermissions("pubMenu:permission")
	@RequestMapping("/permission")
	public ModelAndView permission(String id) {
		ModelAndView view = new ModelAndView("/pub/pubMenu/permission");
		view.addObject("perList", permissionsService.findPermissionsByMenuId(id));
		PubMenuPO po = menuService.getById(id);
		view.addObject("menu", po);
		return view;
	}

	/**
	 * 权限绑定保存
	 */
	@RequiresPermissions("pubMenu:permission")
	@PostMapping("/permissionSave")
	public String permissionSave(PubMenuFormDTO dto, RedirectAttributes ra) {
		try {
			permissionsService.save(dto);;
			AlertMessage.success("权限绑定成功").flashAttribute(ra);
		} catch (BusinessException e) {
			AlertMessage.error(e.getMessage()).flashAttribute(ra);
		}
		return "redirect:/pubMenu/list";
	}
}
