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
import io.github.pleuvoir.manager.model.dto.pay.PayProductFormDTO;
import io.github.pleuvoir.manager.model.po.pay.PayProductPO;
import io.github.pleuvoir.manager.model.vo.ResultMessageVO;
import io.github.pleuvoir.manager.model.vo.pay.PayProductListVO;
import io.github.pleuvoir.manager.service.pay.PayProductService;


@Controller
@RequestMapping("/payProduct")
public class PayProductController {
	
	@Autowired
	private PayProductService payProductService;

	/**
	 * 查询页 
	 */
	@RequiresPermissions("payProduct:list")
	@RequestMapping("/list")
	public String list() {
		return "/pay/payProduct/list";
	}
	
	/**
	 * 查询结果 
	 */
	@RequiresPermissions("payProduct:list")
	@RequestMapping("/query")
	@ResponseBody
	public PayProductListVO query(PayProductFormDTO form) {
		return payProductService.queryList(form);
	}
	
	/**
	 * 新增页面
	 */
	@RequiresPermissions("payProduct:add")
	@RequestMapping("/create")
	public ModelAndView create() {
		ModelAndView view = new ModelAndView("/pay/payProduct/create");
		return view;
	}
	
	/**
	 * 保存
	 */
	@RequiresPermissions("payProduct:add")
	@PostMapping("/save")
	public String save(@Validated PayProductPO po, BindingResult bindingResult, RedirectAttributes ra) {
		if (bindingResult.hasErrors()) {
			if (bindingResult.getGlobalError() != null) {
				AlertMessage.error(bindingResult.getGlobalError()).flashAttribute(ra);
			} else if (bindingResult.getFieldError() != null) {
				AlertMessage.error(bindingResult.getFieldError()).flashAttribute(ra);
			}
			return "redirect:/payProduct/create";
		} else {
			try {
				payProductService.save(po);
				AlertMessage.success("保存成功").flashAttribute(ra);
			} catch (BusinessException e) {
				AlertMessage.error(e.getMessage()).flashAttribute(ra);
			}
		}
		return "redirect:/payProduct/list";
	}
	
	/**
	 * 修改页面
	 */
	@RequiresPermissions("payProduct:edit")
	@RequestMapping("/edit")
	public ModelAndView edit(String id) {
		ModelAndView view = new ModelAndView("/pay/payProduct/edit");
		PayProductPO old = payProductService.selectById(id);
		view.addObject("old", old);
		return view;
	}
	
	/**
	 * 修改
	 */
	@RequiresPermissions("payProduct:edit")
	@PostMapping("/update")
	public String edit(@Validated PayProductPO po, BindingResult bindingResult, RedirectAttributes ra) {
		if (bindingResult.hasErrors()) {
			if (bindingResult.getGlobalError() != null) {
				AlertMessage.error(bindingResult.getGlobalError()).flashAttribute(ra);
			} else if (bindingResult.getFieldError() != null) {
				AlertMessage.error(bindingResult.getFieldError()).flashAttribute(ra);
			}
			return "redirect:/payProduct/edit";
		}else {
			try {
				payProductService.modify(po);
				AlertMessage.success("修改成功").flashAttribute(ra);
			} catch (BusinessException e) {
				AlertMessage.error(e.getMessage()).flashAttribute(ra);
			}
		}
		return "redirect:/payProduct/list";
	}
	
	/**
	 * 删除
	 */
	@RequiresPermissions("payProduct:delete")
	@PostMapping("/delete")
	@ResponseBody
	public ResultMessageVO<?> delete(String id) {
		if(StringUtils.isBlank(id)) {
			return ResultMessageVO.error("id为空");
		}else {
			try {
				payProductService.remove(id);
				return ResultMessageVO.success("删除成功");
			} catch (BusinessException e) {
				return ResultMessageVO.error(e.getMessage());
			}
		}
	}
	
}
