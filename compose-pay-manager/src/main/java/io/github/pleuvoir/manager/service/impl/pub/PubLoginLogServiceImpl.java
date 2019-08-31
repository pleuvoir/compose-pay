package io.github.pleuvoir.manager.service.impl.pub;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

import io.github.pleuvoir.manager.common.util.Generator;
import io.github.pleuvoir.manager.dao.pub.PubLoginLogDao;
import io.github.pleuvoir.manager.model.PageCondition;
import io.github.pleuvoir.manager.model.dto.pub.PubLoginLogFormDTO;
import io.github.pleuvoir.manager.model.po.pub.PubLoginLogPO;
import io.github.pleuvoir.manager.model.vo.pub.PubLoginLogListVO;
import io.github.pleuvoir.manager.service.pub.PubLoginLogService;
import io.github.pleuvoir.manager.service.pub.PubUserService;

@Service("pubLoginLogService")
public class PubLoginLogServiceImpl implements PubLoginLogService {
	
	private static final Logger logger = LoggerFactory.getLogger(PubLoginLogServiceImpl.class);
	
	@Autowired
	private PubLoginLogDao loginLogDao;
	@Autowired
	private PubUserService userService;

	/**
	 * 登录成功日志
	 * @param username
	 * @param log
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void success(String username, PubLoginLogPO log) {
		log.setStatus(PubLoginLogPO.STATUS_SUCCESS);
		if(userService.getUser(username)!=null)
			save(username, log);
	}

	/**
	 * 登录失败日志
	 * @param username
	 * @param log
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void fail(String username, PubLoginLogPO log) {
		log.setStatus(PubLoginLogPO.STATUS_FAIL);
		if(userService.getUser(username)!=null)
			save(username, log);
	}
	
	private void save(String username, PubLoginLogPO log){
		PubLoginLogPO nlog = new PubLoginLogPO();
		nlog.setId(Generator.nextUUID());
		nlog.setUsername(username);
		nlog.setAgent(log.getAgent());
		nlog.setIp(log.getIp());
		nlog.setLoginDate(LocalDateTime.now());
		nlog.setStatus(log.getStatus());
		nlog.setRemark(log.getRemark());
		Integer i = loginLogDao.insert(nlog);
		if(i==null || i < 1) {
			logger.error("插入日志失败，log:{}",JSON.toJSON(log));
		}
	}

	@Override
	public PubLoginLogListVO queryList(PubLoginLogFormDTO form) {
		
		PageCondition pageCondition = PageCondition.create(form);
		
		if(StringUtils.isNotBlank(form.getUsername())) {
			form.setUsername("%".concat(form.getUsername()).concat("%"));
		}
		List<PubLoginLogPO> list = loginLogDao.find(pageCondition, form);

		PubLoginLogListVO loginLogList = new PubLoginLogListVO(pageCondition);
		loginLogList.setRows(list);
		return loginLogList;
	}

}
