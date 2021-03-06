package com.xywei.authentication;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

import com.xywei.realm.MyCustomRealm;
import com.xywei.realm.MyCustomRealmMD5;

/**
 * 使用ini文件进行的认证
 * 
 * @author wodoo
 *
 */
public class MyAuthentication_ini {

	private String username = "admin";
	// 一次 MD5 44002f12830afeefd56ea3724f9279df
	// 十次 MD5 c918541f66cd05f0a81e6ddab2efd52f
	private String password = "admin";

	/***
	 * 使用自定义的简单的ini文件认证
	 * <b>顺序不能乱<b>，必须是：1，初始化realm,securityManager，2，设置securityManager，3，初始化主体subject，4，subject登录
	 */
	@Test
	public void testAuthenticationIni() {

		String resourcePath = "classpath:authentication/first_ini.ini";

		// 加载realm
		Factory<SecurityManager> securityManagerFactory = new IniSecurityManagerFactory(resourcePath);
		// 获取securityManager
		SecurityManager securityManager = securityManagerFactory.getInstance();

		// 方法二：
//		 DefaultSecurityManager securityManager2 = new DefaultSecurityManager();
		// 方法三：使用simpleAccountRealm直接adduser 但是不支持添加权限，还是ini比较强大
//		SimpleAccountRealm iniRealm=new SimpleAccountRealm();
//		iniRealm.addAccount(username, password);
		// IniRealm iniRealm = new IniRealm(resourcePath);
//		 securityManager2.setRealm(iniRealm);
		// 第一次练习，第一步,到这里卡住了，不知道如何写下去
		// 设置运行环境
		// 设置使用的subjectmanager
		SecurityUtils.setSecurityManager(securityManager);
//		 SecurityUtils.setSecurityManager(securityManager2);
		// 获取主体
		Subject subject = SecurityUtils.getSubject();

		// 不用做任何设置吗？
		UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);

		try {
			subject.login(usernamePasswordToken);
			System.out.println("登录后" + subject.isAuthenticated());
			subject.logout();
			System.out.println("退出登录后" + subject.isAuthenticated());

		} catch (AuthenticationException e) {
			e.printStackTrace();
		}
	}

	/***
	 * 自定义realm，明白为什么要自定义realm <br/>
	 * 自定义realm，可以不用硬编码用户名和密码，这样就能加密以及通过数据库或者缓存中得到用户名密码 ，对比用户输入的
	 */
	@Test
	public void testAuthenticationCustomRealm() {

		// 文件里面的realm如何编写
		String iniResourcePath = "classpath:authentication/custom_realm.ini";
		// String iniResourcePath = "classpath:authentication/first_ini.ini";
		Factory<SecurityManager> securityManagerFactory = new IniSecurityManagerFactory(iniResourcePath);
		SecurityManager securityManager = securityManagerFactory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		// 方法二
		// MyCustomRealm myCustomRealm=new MyCustomRealm();
		// DefaultSecurityManager securityManager=new DefaultSecurityManager();
		// securityManager.setRealm(myCustomRealm);
		// SecurityUtils.setSecurityManager(securityManager);

		Subject subject = SecurityUtils.getSubject();
		// 自定义realm需要这句不？
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);

		try {
			subject.login(token);
			System.out.println("登录后：" + subject.isAuthenticated());
			subject.logout();
			System.out.println("登出后：" + subject.isAuthenticated());

		} catch (AuthenticationException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 使用MD5加密
	 * 
	 * @return
	 */
	@Test
	public void authenticationTestMD5() {

		// String iniResourcePath = "classpath:authentication/custom_realm_md5.ini";
		// Factory<SecurityManager> factory = new
		// IniSecurityManagerFactory(iniResourcePath);
		// SecurityManager securityManager = factory.getInstance();
		// SecurityUtils.setSecurityManager(securityManager);

		// 方法二
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
		matcher.setHashAlgorithmName("md5");
		matcher.setHashIterations(10);
		MyCustomRealmMD5 myCustomRealmMD5 = new MyCustomRealmMD5();
		myCustomRealmMD5.setCredentialsMatcher(matcher);
		DefaultSecurityManager securityManager = new DefaultSecurityManager();
		securityManager.setRealm(myCustomRealmMD5);
		SecurityUtils.setSecurityManager(securityManager);
		Subject subject = SecurityUtils.getSubject();

		UsernamePasswordToken token = new UsernamePasswordToken(username, password);

		try {
			subject.login(token);
			System.out.println("登录后：" + subject.isAuthenticated());
			subject.logout();
			System.out.println("登出后：" + subject.isAuthenticated());

		} catch (AuthenticationException e) {
			e.printStackTrace();
		}

	}

}
