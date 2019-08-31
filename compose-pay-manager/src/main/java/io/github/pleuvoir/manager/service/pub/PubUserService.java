package io.github.pleuvoir.manager.service.pub;

import java.io.UnsupportedEncodingException;

import javax.servlet.ServletRequest;

import io.github.pleuvoir.manager.exception.BusinessException;
import io.github.pleuvoir.manager.model.dto.pub.PubLoginPwdDTO;
import io.github.pleuvoir.manager.model.dto.pub.PubUserFormDTO;
import io.github.pleuvoir.manager.model.po.pub.PubUserPO;
import io.github.pleuvoir.manager.model.vo.pub.PubUserListVO;

/**
 * 登录用户
 * @author LaoShu
 *
 */
public interface PubUserService {

	/**
	 * 通过用户名获取用户信息
	 * @param username
	 * @return
	 */
	PubUserPO getUser(String username);
	
	/**
	 * 获取用户信息
	 * @param id
	 * @return
	 */
	PubUserPO getUserById(String id);
	
	/**
	 * 列表查询
	 * @param form
	 * @return
	 */
	PubUserListVO queryList(PubUserFormDTO form);
	
	/**
	 * 保存
	 * @param po
	 */
	void save(PubUserPO po) throws BusinessException;
	
	/**
	 * 修改
	 * @param po
	 * @throws BusinessException
	 */
	void edit(PubUserPO po) throws BusinessException;
	
	/**
	 * 删除
	 * @param id
	 * @throws BusinessException
	 */
	void delete(String id) throws BusinessException;
	
	/**
	 * 修改密码
	 * @param dto
	 * @throws BusinessException
	 * @throws Exception 
	 * @throws UnsupportedEncodingException 
	 */
	void editPwd(PubLoginPwdDTO dto,ServletRequest request) throws BusinessException, Exception;
}
