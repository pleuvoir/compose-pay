package io.github.pleuvoir.manager.service.impl.pub;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import io.github.pleuvoir.manager.common.authc.PasswordHelper;
import io.github.pleuvoir.manager.common.util.AssertUtil;
import io.github.pleuvoir.manager.common.util.Generator;
import io.github.pleuvoir.manager.common.util.SessionUtil;
import io.github.pleuvoir.manager.dao.pub.PubUserDao;
import io.github.pleuvoir.manager.dao.pub.PubUserRoleDao;
import io.github.pleuvoir.manager.exception.BusinessException;
import io.github.pleuvoir.manager.model.PageCondition;
import io.github.pleuvoir.manager.model.dto.pub.PubLoginPwdDTO;
import io.github.pleuvoir.manager.model.dto.pub.PubUserFormDTO;
import io.github.pleuvoir.manager.model.po.pub.PubRolePO;
import io.github.pleuvoir.manager.model.po.pub.PubUserPO;
import io.github.pleuvoir.manager.model.po.pub.PubUserRolePO;
import io.github.pleuvoir.manager.model.vo.pub.PubUserListVO;
import io.github.pleuvoir.manager.service.pub.PubRoleService;
import io.github.pleuvoir.manager.service.pub.PubUserService;

/**
 * 登录用户
 * @author LaoShu
 *
 */
@Service("pubUserService")
public class PubUserServiceImpl implements PubUserService {
	
	@Autowired
	private PubUserDao userDao;
	@Autowired
	private PubRoleService roleService;
	@Autowired
	private PubUserRoleDao userRoleDao;
	
	
	/**
	 * 获取用户信息
	 * @param username
	 * @return
	 */
	@Override
	public PubUserPO getUser(String username) {
		PubUserPO po = new PubUserPO();
		po.setUsername(username);
		return userDao.selectOne(po);
	}

	/**
	 * 获取用户信息
	 * @param id
	 * @return
	 */
	@Override
	public PubUserPO getUserById(String id) {
		return userDao.selectById(id);
	}

	/**
	 * 列表查询
	 * @param form
	 * @return
	 */
	@Override
	public PubUserListVO queryList(PubUserFormDTO form) {
		String username = SessionUtil.getUserName();
		form.setLoginUserName(username);
		
		PageCondition pageCondition = PageCondition.create(form);
		
		if(StringUtils.isNotBlank(form.getUsername())) {
			form.setUsername("%".concat(form.getUsername()).concat("%"));
		}
		
		//获取用户所有的角色
		List<PubUserPO> userlist = userDao.find(pageCondition, form);
		for(PubUserPO userPo : userlist) {
			StringBuffer stb = new StringBuffer();
			List<PubRolePO> roleList = roleService.findRolesByUsername(userPo.getUsername());
			for(PubRolePO rolePo : roleList) {
				stb.append(rolePo.getName());
				stb.append(",");
			}
			userPo.setRoleNames(StringUtils.substring(stb.toString(), 0,stb.length()-1));
		}

		PubUserListVO userList = new PubUserListVO(pageCondition);
		userList.setRows(userlist);
		return userList;
	}

	/**
	 * 保存
	 * @param po
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void save(PubUserPO po) throws BusinessException {

		PubUserPO dateUserPo = this.getUser(po.getUsername());
		if(dateUserPo != null) {
			throw new BusinessException("用户名重复");
		}
		
		//密码md5
		PasswordHelper ph = new PasswordHelper();
		ph.encryptPassword(po);
		
		po.setId(Generator.nextUUID());
		po.setStatus(PubUserPO.STATUS_ACTIVED);
		po.setCreateBy(SessionUtil.getUserName());
		po.setCreateTime(LocalDateTime.now());
		
		Integer us = userDao.insert(po);
		AssertUtil.assertOne(us, "保存用户失败");
		
		if(po.getRoles()==null) {
			throw new BusinessException("未选择角色");
		}else {
			for(String s : po.getRoles()) {
				PubUserRolePO urpo = new PubUserRolePO();
				urpo.setRoleId(s);
				urpo.setUserId(po.getId());
				Integer urs = userRoleDao.insert(urpo);
				AssertUtil.assertOne(urs, "保存用户角色关联关系失败");
			}
		}
		
	}

	/**
	 * 修改
	 * @param po
	 * @throws BusinessException
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void edit(PubUserPO po) throws BusinessException {
		PubUserPO dateUserPo = this.getUserById(po.getId());
		
		PasswordHelper ph = new PasswordHelper();
		if(!StringUtils.equals(po.getPassword(), dateUserPo.getPassword())) {
			po.setPassword(ph.encryptPassword(po.getPassword(), dateUserPo.getSalt()));
		}
		if(!StringUtils.equals(po.getSafePassword(), dateUserPo.getSafePassword())) {
			po.setSafePassword(ph.encryptPassword(po.getSafePassword(), dateUserPo.getSalt()));
		}
		
		Integer us = userDao.updateById(po);
		AssertUtil.assertOne(us, "修改用户失败");
		
		if(po.getRoles()==null) {
			throw new BusinessException("未选择角色");
		}else {
			List<PubRolePO> alRuleList = roleService.findRolesByUsername(po.getUsername());
			Map<String, String> alRoleMap = new HashMap<>();	//拥有的角色
			Map<String, String> foRoleMap = new HashMap<>();	//传入的角色
			for(PubRolePO alpo : alRuleList) {
				alRoleMap.put(alpo.getId(), null);
			}
			for(String str : po.getRoles()) {
				foRoleMap.put(str, null);
			}
			List<String> sameKeyList = new ArrayList<>();
			for(Map.Entry<String, String> alEntry : alRoleMap.entrySet()) {
				for(Map.Entry<String, String> foEntry : foRoleMap.entrySet()) {
					if(StringUtils.equals(alEntry.getKey(), foEntry.getKey())) {
						sameKeyList.add(alEntry.getKey());
					}
				}
			}
			for(String sameKey : sameKeyList) {
				alRoleMap.remove(sameKey);
				foRoleMap.remove(sameKey);
			}
			for(Map.Entry<String, String> alEntry : alRoleMap.entrySet()) {
				int de = userRoleDao.deleteByUserIdAndRoleId(po.getId(), alEntry.getKey());
				AssertUtil.assertOne(de, "删除用户角色关联关系失败");
			}
			for(Map.Entry<String, String> foEntry : foRoleMap.entrySet()) {
				PubUserRolePO newUrp = new PubUserRolePO();
				newUrp.setUserId(po.getId());
				newUrp.setRoleId(foEntry.getKey());
				Integer in = userRoleDao.insert(newUrp);
				AssertUtil.assertOne(in, "保存用户角色关联关系失败");
			}
		}
		
	}

	/**
	 * 删除
	 * @param id
	 * @throws BusinessException
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void delete(String id) throws BusinessException {
		userRoleDao.deleteByUserId(id);
		PubUserPO po = new PubUserPO();
		po.setId(id);
		po.setStatus(PubUserPO.STATUS_DELETE);
		Integer up = userDao.updateById(po);
		AssertUtil.assertOne(up, "更新用户状态失败");
	}

	/**
	 * 修改密码
	 * @param dto
	 * @throws Exception 
	 * @throws UnsupportedEncodingException 
	 */
	@Override
	public void editPwd(PubLoginPwdDTO dto,ServletRequest request) throws Exception {
		if(!StringUtils.equals(dto.getNewPass(), dto.getRepeatPass())) {
			throw new BusinessException("两次输入新密码不一致");
		}
		PubUserPO user = this.getUser(SessionUtil.getUserName());
		if(user==null) {
			throw new BusinessException("未找到登录用户");
		}
		PasswordHelper ph = new PasswordHelper();
		if(!StringUtils.equals(user.getPassword(), ph.encryptPassword(dto.getOldPass(), user.getSalt()))) {
			throw new BusinessException("原密码错误");
		}
		user.setPassword(ph.encryptPassword(dto.getNewPass(), user.getSalt()));
		Integer up = userDao.updateById(user);
		AssertUtil.assertOne(up, "修改密码失败");
		
	}
	
}
