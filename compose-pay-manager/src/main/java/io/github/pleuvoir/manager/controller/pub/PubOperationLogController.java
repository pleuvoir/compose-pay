package io.github.pleuvoir.manager.controller.pub;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.github.pleuvoir.manager.model.dto.pub.PubOperationLogFormDTO;
import io.github.pleuvoir.manager.model.vo.ResultMessageVO;
import io.github.pleuvoir.manager.model.vo.pub.PubOperationLogListVO;
import io.github.pleuvoir.manager.service.pub.PubOperationLogService;

/**
 * 日志查询
 * @author abeir
 *
 */
@Controller
@RequestMapping("/pubOperationLog")
public class PubOperationLogController {
	
	@Autowired
	private PubOperationLogService operationLogService;

	@RequiresPermissions("pubOperationLog:list")
	@RequestMapping("/list")
	public String list() {
		return "/pub/pubOperationLog/list";
	}
	
	@RequiresPermissions("pubOperationLog:list")
	@RequestMapping("/query")
	@ResponseBody
	public PubOperationLogListVO query(PubOperationLogFormDTO form) {
		return operationLogService.query(form);
	}
	
	@RequiresPermissions("pubOperationLog:list")
	@RequestMapping("/remark")
	@ResponseBody
	public ResultMessageVO<String> remark(String id) {
		String remark = operationLogService.getRemark(id);
		return ResultMessageVO.<String>success(null).setData(remark);
	}
}
