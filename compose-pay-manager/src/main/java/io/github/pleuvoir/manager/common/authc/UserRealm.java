package io.github.pleuvoir.manager.common.authc;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import io.github.pleuvoir.manager.model.po.pub.PubPermissionPO;
import io.github.pleuvoir.manager.model.po.pub.PubRolePO;
import io.github.pleuvoir.manager.model.po.pub.PubUserPO;
import io.github.pleuvoir.manager.service.pub.PubPermissionsService;
import io.github.pleuvoir.manager.service.pub.PubRoleService;
import io.github.pleuvoir.manager.service.pub.PubUserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserRealm extends AuthorizingRealm {
	
	@Autowired
	private PubUserService userService;
	@Autowired
	private PubRoleService roleService;
	@Autowired
	private PubPermissionsService permissionsService;

	/**
     * 提供用户信息返回权限信息
     */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		String username = (String)principals.getPrimaryPrincipal();
		
		// 根据用户名查询当前用户拥有的角色
		List<PubRolePO> roles = roleService.findRolesByUsername(username);
		Set<String> roleCodes = new HashSet<>();
        for(PubRolePO role : roles) {
        	roleCodes.add(role.getId());
        }
        // 根据用户名查询当前用户权限
        List<PubPermissionPO> permissions = permissionsService.findPermissionsByUsername(username);
        Set<String> permissionCodes = new HashSet<>();
        for(PubPermissionPO perms : permissions) {
        	permissionCodes.add(perms.getCode());
        }
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.setRoles(roleCodes);
		authorizationInfo.setStringPermissions(permissionCodes);
		return authorizationInfo;
	}

	/**
     * 提供账户信息返回认证信息
     */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		
		String username = (String) token.getPrincipal();
		
		PubUserPO user = userService.getUser(username);
		if(user==null) {
			throw new UnknownAccountException("用户不存在");
		}
		switch (user.getStatus()) {
		case PubUserPO.STATUS_NOT_ACTIVED:
			throw new DisabledAccountException("用户未生效");
		case PubUserPO.STATUS_LOCKED:
			throw new LockedAccountException("用户已锁定");
		case PubUserPO.STATUS_DELETE:
			throw new DisabledAccountException("用户已删除");
		}
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), ByteSource.Util.bytes(user.getSalt()), getName());
		return authenticationInfo;
	}

}
