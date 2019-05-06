package com.xywei.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class MyCustomRealm extends AuthorizingRealm {

	String realmName = "myCustomRealm11";
	// 模拟从数据库或者缓存中获取的password
	String password = "admin";

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		return null;
	}

	/**
	 * 返回的是什么<br/>
	 * TODO realmName好像没有什么用！
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal();
		System.out.println(
				"token.getPrincipal() " + token.getPrincipal() + " token.getCredentials() " + token.getCredentials());
		// 然后呢？卡住在这里
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, password, realmName);
		return authenticationInfo;
	}

}
