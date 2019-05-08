package com.xywei.realm;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class MyCustomRealm_Authorization extends AuthorizingRealm {

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

		//
		String username = (String) principals.getPrimaryPrincipal();

		System.out.println("username===" + username);
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

		Set<String> roles = new HashSet<String>();
		roles.add("user");
		roles.add("admin");
		simpleAuthorizationInfo.setRoles(roles);

		Set<String> stringPermissions = new HashSet<String>();
		stringPermissions.add("user:select");
		stringPermissions.add("admin:select");

		simpleAuthorizationInfo.setStringPermissions(stringPermissions);

		return simpleAuthorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		String username = (String) token.getPrincipal();
		// 模拟获取的密码
		String password = "admin";

		String realmName = "myCustomRealm_Authorization";
		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username, password, realmName);
		return simpleAuthenticationInfo;
	}

}
