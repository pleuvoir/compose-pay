package io.github.pleuvoir.manager.service.impl.pub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import io.github.pleuvoir.manager.common.extension.SequenceGenerator;
import io.github.pleuvoir.manager.common.util.AssertUtil;
import io.github.pleuvoir.manager.common.util.SessionUtil;
import io.github.pleuvoir.manager.dao.pub.PubPermissionDao;
import io.github.pleuvoir.manager.dao.pub.PubRolePermissionDao;
import io.github.pleuvoir.manager.exception.BusinessException;
import io.github.pleuvoir.manager.model.dto.pub.PubMenuFormDTO;
import io.github.pleuvoir.manager.model.po.pub.PubPermissionPO;
import io.github.pleuvoir.manager.model.po.pub.PubRolePO;
import io.github.pleuvoir.manager.model.po.pub.PubRolePermissionPO;
import io.github.pleuvoir.manager.service.pub.PubPermissionsService;
import io.github.pleuvoir.manager.service.pub.PubRoleService;

/**
 * 权限
 * @author LaoShu
 *
 */
@Service("pubPermissionsService")
public class PubPermissionsServiceImpl implements PubPermissionsService {
	
	@Autowired
	private PubPermissionDao permissionDao;
	@Autowired
	private PubRolePermissionDao rolePermissionDao;
	@Autowired
	private PubRoleService roleService;

	/**
	 * 用户拥有的权限
	 * @param username
	 * @return code集合
	 */
	@Override
	public List<PubPermissionPO> findPermissionsByUsername(String username) {
		return permissionDao.findPermissionsByUsername(username);
	}

	/**
	 * 根据权限code查询
	 * @param code
	 * @return
	 */
	@Override
	public PubPermissionPO getPermission(String code) {
		return permissionDao.getByCode(StringUtils.trim(code));
	}

	/**
	 * 根据权限id查询
	 * @param id
	 * @return
	 */
	@Override
	public PubPermissionPO getPermissionById(String id) {
		return permissionDao.selectById(id);
	}

	/**
	 * 菜单拥有的权限
	 * @param menuId
	 * @return
	 */
	@Override
	public List<PubPermissionPO> findPermissionsByMenuId(String menuId) {
		return permissionDao.findPermissionsByMenuId(menuId);
	}

	/**
	 * 保存
	 * @param dto
	 * @throws BusinessException
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRED,rollbackFor=Throwable.class)
	public void save(PubMenuFormDTO dto) throws BusinessException {
		if(StringUtils.isBlank(dto.getId())) {
			throw new BusinessException("菜单id空");
		}
		Map<String, PubPermissionPO> formMap = dto.getPerMap();
		List<PubPermissionPO> dataList = this.findPermissionsByMenuId(dto.getId());	//数据库查出的数据
		Map<String, PubPermissionPO> dataMap = new HashMap<>();	//key:id   value:PermissionPO
		for(PubPermissionPO pPo : dataList) {
			dataMap.put(pPo.getId(), pPo);
		}
		List<String> sameKeyList = new ArrayList<>();
		for(Map.Entry<String, PubPermissionPO> formMapEntry : formMap.entrySet()) {
			for(Map.Entry<String, PubPermissionPO> dataMapEntry : dataMap.entrySet()) {
				if(StringUtils.equals(formMapEntry.getKey(), dataMapEntry.getKey())) {
					PubPermissionPO upPerPo = formMapEntry.getValue();
					upPerPo.setId(formMapEntry.getKey());
					Integer upPerCount = permissionDao.updateById(upPerPo);	//修改原权限信息
					AssertUtil.assertOne(upPerCount, "修改权限失败");
					sameKeyList.add(formMapEntry.getKey());
				}
			}
		}
		for(String key : sameKeyList) {
			formMap.remove(key);
			dataMap.remove(key);
		}
		for(Map.Entry<String, PubPermissionPO> formMapEntry : formMap.entrySet()) {
			PubPermissionPO nPo = formMapEntry.getValue();
			nPo.setId("UP" + SequenceGenerator.next("SEQ_PERMISSION_ID"));
			nPo.setMenuId(dto.getId());
			Integer iip = permissionDao.insert(nPo);
			AssertUtil.assertOne(iip, "保存权限失败");
			
			List<PubRolePO> rpList = roleService.findRolesByUsername(SessionUtil.getUserName());
			for(PubRolePO rp : rpList) {
				PubRolePermissionPO rpp = new PubRolePermissionPO();
				rpp.setRoleId(rp.getId());
				rpp.setPermissionId(nPo.getId());
				Integer irp = rolePermissionDao.insert(rpp);
				AssertUtil.assertOne(irp, "保存角色权限关联关系失败");
			}
			
		}
		for(Map.Entry<String, PubPermissionPO> dataMapEntry : dataMap.entrySet()) {
			rolePermissionDao.deleteByPermissionId(dataMapEntry.getKey());
			Integer dep = permissionDao.deleteById(dataMapEntry.getKey());
			AssertUtil.assertOne(dep, "删除权限失败");
		}
	}
	

}
