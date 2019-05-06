package com.xywei.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;

public class MyCustomRealmMD5 extends AuthorizingRealm {

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		String username = (String) token.getPrincipal();
		// 模拟从数据库中取得密码
		String password = "c918541f66cd05f0a81e6ddab2efd52f";
		// String password = "cd92a26534dba48cd785cdcc0b3e6bd1";
		String salt = "root";
		String realmName = "myCustomRealmMD5";
		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username, password,
				ByteSource.Util.bytes(salt), realmName);

		return simpleAuthenticationInfo;
	}

	@Test
	public void MD5test() {
		String password = "admin";
		String salt = "root";
		int hashIterations = 10;
		Md5Hash md5Hash = new Md5Hash(password, salt, hashIterations);
		System.out.println(md5Hash.toString());

		String algorithmName = "md5";
		SimpleHash simpleHash = new SimpleHash(algorithmName, password, salt, hashIterations);

		System.out.println(simpleHash.toString());
	}

}
