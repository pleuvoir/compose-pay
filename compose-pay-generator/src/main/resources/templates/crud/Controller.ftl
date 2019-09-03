package io.github.pleuvoir.manager.controller.pay;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import io.github.pleuvoir.manager.exception.BusinessException;
import io.github.pleuvoir.manager.model.dto.pay.${dataModel.name}FormDTO;
import io.github.pleuvoir.manager.model.po.pay.${dataModel.name}PO;
import io.github.pleuvoir.manager.model.vo.ResultMessageVO;
import io.github.pleuvoir.manager.model.vo.pay.${dataModel.name}ListVO;
import io.github.pleuvoir.manager.service.pay.${dataModel.name}Service;


@Controller
@RequestMapping("/${dataModel.name?uncap_first}")
public class ${dataModel.name}Controller {
	
	@Autowired
	private ${dataModel.name}Service ${dataModel.name?uncap_first}Service;

	/**
	 * 查询页 
	 */
	@RequiresPermissions("${dataModel.name?uncap_first}:list")
	@RequestMapping("/list")
	public String list() {
		return "/pay/${dataModel.name?uncap_first}/list";
	}
	
	/**
	 * 查询结果 
	 */
	@RequiresPermissions("${dataModel.name?uncap_first}:list")
	@RequestMapping("/query")
	@ResponseBody
	public ${dataModel.name}ListVO query(${dataModel.name}FormDTO form) {
		return ${dataModel.name?uncap_first}Service.queryList(form);
	}
	
	/**
	 * 新增页面
	 */
	@RequiresPermissions("${dataModel.name?uncap_first}:add")
	@RequestMapping("/create")
	public ModelAndView create() {
		ModelAndView view = new ModelAndView("/pay/${dataModel.name?uncap_first}/create");
		return view;
	}
	
	/**
	 * 保存
	 */
	@RequiresPermissions("${dataModel.name?uncap_first}:add")
	@PostMapping("/save")
	public String save(@Validated ${dataModel.name}PO po, BindingResult bindingResult, RedirectAttributes ra) {
		if (bindingResult.hasErrors()) {
			if (bindingResult.getGlobalError() != null) {
				AlertMessage.error(bindingResult.getGlobalError()).flashAttribute(ra);
			} else if (bindingResult.getFieldError() != null) {
				AlertMessage.error(bindingResult.getFieldError()).flashAttribute(ra);
			}
			return "redirect:/${dataModel.name?uncap_first}/create";
		} else {
			try {
				${dataModel.name?uncap_first}Service.save(po);
				AlertMessage.success("保存成功").flashAttribute(ra);
			} catch (BusinessException e) {
				AlertMessage.error(e.getMessage()).flashAttribute(ra);
			}
		}
		return "redirect:/${dataModel.name?uncap_first}/list";
	}
	
	/**
	 * 修改页面
	 */
	@RequiresPermissions("${dataModel.name?uncap_first}:edit")
	@RequestMapping("/edit")
	public ModelAndView edit(String id) {
		ModelAndView view = new ModelAndView("/pay/${dataModel.name?uncap_first}/edit");
		${dataModel.name}PO old = ${dataModel.name?uncap_first}Service.selectById(id);
		view.addObject("old", old);
		return view;
	}
	
	/**
	 * 修改
	 */
	@RequiresPermissions("${dataModel.name?uncap_first}:edit")
	@PostMapping("/update")
	public String edit(@Validated ${dataModel.name}PO po, BindingResult bindingResult, RedirectAttributes ra) {
		if (bindingResult.hasErrors()) {
			if (bindingResult.getGlobalError() != null) {
				AlertMessage.error(bindingResult.getGlobalError()).flashAttribute(ra);
			} else if (bindingResult.getFieldError() != null) {
				AlertMessage.error(bindingResult.getFieldError()).flashAttribute(ra);
			}
			return "redirect:/${dataModel.name?uncap_first}/edit";
		}else {
			try {
				${dataModel.name?uncap_first}Service.modify(po);
				AlertMessage.success("修改成功").flashAttribute(ra);
			} catch (BusinessException e) {
				AlertMessage.error(e.getMessage()).flashAttribute(ra);
			}
		}
		return "redirect:/${dataModel.name?uncap_first}/list";
	}
	
	/**
	 * 删除
	 */
	@RequiresPermissions("${dataModel.name?uncap_first}:delete")
	@PostMapping("/delete")
	@ResponseBody
	public ResultMessageVO<?> delete(String id) {
		if(StringUtils.isBlank(id)) {
			return ResultMessageVO.error("id为空");
		}else {
			try {
				${dataModel.name?uncap_first}Service.remove(id);
				return ResultMessageVO.success("删除成功");
			} catch (BusinessException e) {
				return ResultMessageVO.error(e.getMessage());
			}
		}
	}
	
}
