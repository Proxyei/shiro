package com.xywei.authentication;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

import com.alibaba.druid.pool.DruidDataSource;

public class MyAuthentication_jdbc {

	// 数据源连接数据库这里卡住了
	// private DataSource datasource;因为这里写错了
	private DruidDataSource dataSource;
	private JdbcRealm jdbcRealm = new JdbcRealm();

	@Before
	public void initDataSource() {

		dataSource = new DruidDataSource();
		String jdbcUrl = "jdbc:mysql://localhost:3306/imooc_shiro";
		String username = "root";
		String password = "xieSHI123321";
		dataSource.setUrl(jdbcUrl);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
	}

	@Test
	public void testMyAuthenticationJDBCRealm() {

		String username = "admin";
		String password = "admin1";

		jdbcRealm.setDataSource(dataSource);
		DefaultSecurityManager securityManager = new DefaultSecurityManager();
		securityManager.setRealm(jdbcRealm);

		SecurityUtils.setSecurityManager(securityManager);
		Subject subject = SecurityUtils.getSubject();

		UsernamePasswordToken token = new UsernamePasswordToken(username, password);

		try {
			subject.login(token);
			System.out.println("登录认证：" + subject.isAuthenticated());
			subject.logout();
			System.out.println("登出后认证：" + subject.isAuthenticated());
		} catch (AuthenticationException e) {
			e.printStackTrace();
		}

	}

}
