package io.github.pleuvoir.manager.controller.pub;

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
import io.github.pleuvoir.manager.model.dto.pub.PubParamFormDTO;
import io.github.pleuvoir.manager.model.po.pub.PubParamPO;
import io.github.pleuvoir.manager.model.vo.ResultMessageVO;
import io.github.pleuvoir.manager.model.vo.pub.PubParamListVO;
import io.github.pleuvoir.manager.service.pub.PubParamService;

/**
 * 参数管理
 * @author abeir
 *
 */
@Controller
@RequestMapping("/pubParam")
public class PubParamController {
	
	@Autowired
	private PubParamService paramService;

	/**
	 * 查询页 
	 */
	@RequiresPermissions("pubParam:list")
	@RequestMapping("/list")
	public String list() {
		return "/pub/pubParam/list";
	}
	
	/**
	 * 查询结果 
	 */
	@RequiresPermissions("pubParam:list")
	@RequestMapping("/query")
	@ResponseBody
	public PubParamListVO query(PubParamFormDTO form) {
		return paramService.queryList(form);
	}
	
	/**
	 * 查看详情 
	 */
	@RequiresPermissions("pubParam:list")
	@RequestMapping("/show")
	public ModelAndView show(String code) {
		ModelAndView v = new ModelAndView("/pub/pubParam/show");
		v.addObject("params", paramService.getParam(code));
		return v;
	}
	
	/**
	 * 新增页面
	 */
	@RequiresPermissions("pubParam:add")
	@RequestMapping("/create")
	public String create() {
		return "/pub/pubParam/create";
	}
	
	/**
	 * 保存参数 
	 */
	@RequiresPermissions("pubParam:add")
	@PostMapping("/save")
	public String save(@Validated PubParamPO param, BindingResult bindingResult, RedirectAttributes ra) {
		if(bindingResult.hasErrors()) {
			if(bindingResult.getGlobalError()!=null) {
				AlertMessage.error(bindingResult.getGlobalError()).flashAttribute(ra);
			}else if(bindingResult.getFieldError()!=null) {
				AlertMessage.error(bindingResult.getFieldError()).flashAttribute(ra);
			}
		}else {
			try {
				paramService.save(param);
				AlertMessage.success("保存参数成功").flashAttribute(ra);
				return "redirect:/pubParam/list";
			} catch (BusinessException e) {
				AlertMessage.error(e.getMessage()).flashAttribute(ra);
			}
		}
		return "redirect:/pubParam/create";
	}
	
	/**
	 * 参数修改页面
	 */
	@RequiresPermissions("pubParam:edit")
	@RequestMapping("/edit")
	public ModelAndView edit(String code, RedirectAttributes ra) {
		if(StringUtils.isBlank(code)) {
			AlertMessage.error("参数编号为空").flashAttribute(ra);
			return new ModelAndView("redirect:/pub/pubParam/list");
		}
		ModelAndView v = new ModelAndView("/pub/pubParam/edit");
		v.addObject("params", paramService.getParam(code));
		return v;
	}
	
	/**
	 * 更新参数
	 */
	@RequiresPermissions("pubParam:edit")
	@PostMapping("/update")
	public String update(@Validated PubParamPO param, BindingResult bindingResult, RedirectAttributes ra) {
		if(bindingResult.hasErrors()) {
			if(bindingResult.getGlobalError()!=null) {
				AlertMessage.error(bindingResult.getGlobalError()).flashAttribute(ra);
			}else if(bindingResult.getFieldError()!=null) {
				AlertMessage.error(bindingResult.getFieldError()).flashAttribute(ra);
			}
		}else {
			try {
				paramService.modify(param);
				AlertMessage.success("更新参数成功").flashAttribute(ra);
				return "redirect:/pubParam/list";
			} catch (BusinessException e) {
				AlertMessage.error(e.getMessage()).flashAttribute(ra);
			}
		}
		return "redirect:/pubParam/edit?code="+param.getCode();
	}
	
	/**
	 * 删除参数
	 */
	@RequiresPermissions("pubParam:delete")
	@PostMapping("/delete")
	@ResponseBody
	public ResultMessageVO<?> delete(String code, RedirectAttributes ra) {
		if(StringUtils.isBlank(code)) {
			return ResultMessageVO.error("参数编号为空");
		}else {
			try {
				paramService.remove(code);
				return ResultMessageVO.success("参数已删除");
			} catch (BusinessException e) {
				return ResultMessageVO.error(e.getMessage());
			}
		}
	}
}
