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
import io.github.pleuvoir.manager.model.dto.pay.PayWayFormDTO;
import io.github.pleuvoir.manager.model.po.pay.PayWayPO;
import io.github.pleuvoir.manager.model.vo.ResultMessageVO;
import io.github.pleuvoir.manager.model.vo.pay.PayWayListVO;
import io.github.pleuvoir.manager.service.pay.PayWayService;


@Controller
@RequestMapping("/payWay")
public class PayWayController {
	
	@Autowired
	private PayWayService payWayService;

	/**
	 * 查询页 
	 */
	@RequiresPermissions("payWay:list")
	@RequestMapping("/list")
	public String list() {
		return "/pay/payWay/list";
	}
	
	/**
	 * 查询结果 
	 */
	@RequiresPermissions("payWay:list")
	@RequestMapping("/query")
	@ResponseBody
	public PayWayListVO query(PayWayFormDTO form) {
		return payWayService.queryList(form);
	}
	
	/**
	 * 新增页面
	 */
	@RequiresPermissions("payWay:add")
	@RequestMapping("/create")
	public ModelAndView create() {
		ModelAndView view = new ModelAndView("/pay/payWay/create");
		return view;
	}
	
	/**
	 * 保存
	 */
	@RequiresPermissions("payWay:add")
	@PostMapping("/save")
	public String save(@Validated PayWayPO po, BindingResult bindingResult, RedirectAttributes ra) {
		if (bindingResult.hasErrors()) {
			if (bindingResult.getGlobalError() != null) {
				AlertMessage.error(bindingResult.getGlobalError()).flashAttribute(ra);
			} else if (bindingResult.getFieldError() != null) {
				AlertMessage.error(bindingResult.getFieldError()).flashAttribute(ra);
			}
			return "redirect:/payWay/create";
		} else {
			try {
				payWayService.save(po);
				AlertMessage.success("保存成功").flashAttribute(ra);
			} catch (BusinessException e) {
				AlertMessage.error(e.getMessage()).flashAttribute(ra);
			}
		}
		return "redirect:/payWay/list";
	}
	
	/**
	 * 修改页面
	 */
	@RequiresPermissions("payWay:edit")
	@RequestMapping("/edit")
	public ModelAndView edit(String id) {
		ModelAndView view = new ModelAndView("/pay/payWay/edit");
		PayWayPO old = payWayService.selectById(id);
		view.addObject("old", old);
		return view;
	}
	
	/**
	 * 修改
	 */
	@RequiresPermissions("payWay:edit")
	@PostMapping("/update")
	public String edit(@Validated PayWayPO po, BindingResult bindingResult, RedirectAttributes ra) {
		if (bindingResult.hasErrors()) {
			if (bindingResult.getGlobalError() != null) {
				AlertMessage.error(bindingResult.getGlobalError()).flashAttribute(ra);
			} else if (bindingResult.getFieldError() != null) {
				AlertMessage.error(bindingResult.getFieldError()).flashAttribute(ra);
			}
			return "redirect:/payWay/edit";
		}else {
			try {
				payWayService.modify(po);
				AlertMessage.success("修改成功").flashAttribute(ra);
			} catch (BusinessException e) {
				AlertMessage.error(e.getMessage()).flashAttribute(ra);
			}
		}
		return "redirect:/payWay/list";
	}
	
	/**
	 * 删除
	 */
	@RequiresPermissions("payWay:delete")
	@PostMapping("/delete")
	@ResponseBody
	public ResultMessageVO<?> delete(String id) {
		if(StringUtils.isBlank(id)) {
			return ResultMessageVO.error("id为空");
		}else {
			try {
				payWayService.remove(id);
				return ResultMessageVO.success("删除成功");
			} catch (BusinessException e) {
				return ResultMessageVO.error(e.getMessage());
			}
		}
	}
	
}
