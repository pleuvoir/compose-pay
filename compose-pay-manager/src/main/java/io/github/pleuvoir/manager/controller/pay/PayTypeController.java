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
import io.github.pleuvoir.manager.model.dto.pay.PayTypeFormDTO;
import io.github.pleuvoir.manager.model.po.pay.PayTypePO;
import io.github.pleuvoir.manager.model.vo.ResultMessageVO;
import io.github.pleuvoir.manager.model.vo.pay.PayTypeListVO;
import io.github.pleuvoir.manager.service.pay.PayTypeService;

/**
 * 支付种类
 * @author pleuvoir
 *
 */
@Controller
@RequestMapping("/payType")
public class PayTypeController {
	
	@Autowired
	private PayTypeService payTypeService;

	/**
	 * 查询页 
	 */
	@RequiresPermissions("payType:list")
	@RequestMapping("/list")
	public String list() {
		return "/pay/payType/list";
	}
	
	/**
	 * 查询结果 
	 */
	@RequiresPermissions("payType:list")
	@RequestMapping("/query")
	@ResponseBody
	public PayTypeListVO query(PayTypeFormDTO form) {
		return payTypeService.queryList(form);
	}
	
	/**
	 * 新增页面
	 */
	@RequiresPermissions("payType:add")
	@RequestMapping("/create")
	public ModelAndView create() {
		ModelAndView view = new ModelAndView("/pay/payType/create");
		return view;
	}
	
	/**
	 * 保存用户 
	 */
	@RequiresPermissions("payType:add")
	@PostMapping("/save")
	public String save(@Validated PayTypePO po, BindingResult bindingResult, RedirectAttributes ra) {
		if (bindingResult.hasErrors()) {
			if (bindingResult.getGlobalError() != null) {
				AlertMessage.error(bindingResult.getGlobalError()).flashAttribute(ra);
			} else if (bindingResult.getFieldError() != null) {
				AlertMessage.error(bindingResult.getFieldError()).flashAttribute(ra);
			}
			return "redirect:/payType/create";
		} else {
			try {
				payTypeService.save(po);
				AlertMessage.success("保存成功").flashAttribute(ra);
			} catch (BusinessException e) {
				AlertMessage.error(e.getMessage()).flashAttribute(ra);
			}
		}
		return "redirect:/payType/list";
	}
	
	/**
	 * 修改页面
	 */
	@RequiresPermissions("payType:edit")
	@RequestMapping("/edit")
	public ModelAndView edit(String id) {
		ModelAndView view = new ModelAndView("/pay/payType/edit");
		return view;
	}
	
	/**
	 * 修改用户 
	 */
	@RequiresPermissions("payType:edit")
	@PostMapping("/update")
	public String edit(@Validated PayTypePO po, BindingResult bindingResult, RedirectAttributes ra) {
		if (bindingResult.hasErrors()) {
			if (bindingResult.getGlobalError() != null) {
				AlertMessage.error(bindingResult.getGlobalError()).flashAttribute(ra);
			} else if (bindingResult.getFieldError() != null) {
				AlertMessage.error(bindingResult.getFieldError()).flashAttribute(ra);
			}
			return "redirect:/payType/edit";
		}else {
			try {
				payTypeService.modify(po);
				AlertMessage.success("修改成功").flashAttribute(ra);
			} catch (BusinessException e) {
				AlertMessage.error(e.getMessage()).flashAttribute(ra);
			}
		}
		return "redirect:/payType/list";
	}
	
	/**
	 * 删除
	 */
	@RequiresPermissions("payType:delete")
	@PostMapping("/delete")
	@ResponseBody
	public ResultMessageVO<?> delete(String id) {
		if(StringUtils.isBlank(id)) {
			return ResultMessageVO.error("id为空");
		}else {
			try {
				payTypeService.remove(id);
				return ResultMessageVO.success("删除成功");
			} catch (BusinessException e) {
				return ResultMessageVO.error(e.getMessage());
			}
		}
	}
	
}
