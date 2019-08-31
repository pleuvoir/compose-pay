package io.github.pleuvoir.manager.service.impl.pub;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import io.github.pleuvoir.manager.common.util.Generator;
import io.github.pleuvoir.manager.dao.pub.PubOperationLogDao;
import io.github.pleuvoir.manager.model.PageCondition;
import io.github.pleuvoir.manager.model.dto.pub.PubOperationLogFormDTO;
import io.github.pleuvoir.manager.model.po.pub.PubOperationLogPO;
import io.github.pleuvoir.manager.model.vo.pub.PubOperationLogListVO;
import io.github.pleuvoir.manager.service.pub.PubOperationLogService;

@Service("pubOperationLogService")
public class PubOperationLogServiceImpl implements PubOperationLogService {
	
	private static Logger logger = LoggerFactory.getLogger(PubOperationLogServiceImpl.class);

	@Autowired
	private PubOperationLogDao operationLogDao;
	
	@Override
	public void save(PubOperationLogPO operationLog) {
		operationLog.setId(Generator.nextUUID());
		operationLog.setCreateTime(LocalDateTime.now());
		Integer rs = operationLogDao.insert(operationLog);
		if(rs==null || rs.intValue()!=1) {
			logger.warn("保存操作日志失败，{}", JSON.toJSONString(operationLog));
		}
	}

	@Override
	public PubOperationLogListVO query(PubOperationLogFormDTO form) {
		PageCondition page = PageCondition.create(form);
		
		List<PubOperationLogPO> logList = operationLogDao.find(page, form);
		PubOperationLogListVO vo = new PubOperationLogListVO(page);
		vo.setRows(logList);
		return vo;
	}

	@Override
	public String getRemark(String id) {
		PubOperationLogPO log = operationLogDao.selectById(StringUtils.trim(id));
		if(log!=null) {
			return log.getRemark();
		}
		return null;
	}

}
