package com.voyager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.http.HttpServlet;

/**
 * 数据库操作类
 * 
 * @author wuhaojie
 *
 */
public class UserPwdDao {

	/**
	 * 数据库链接对象
	 */
	private Connection conn;

	public UserPwdDao() {
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(new File(UserPwdDao.class
					.getClassLoader().getResource("config.properties")
					.getPath())));
			System.out.println(properties.getProperty("SQLDriver"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			Class.forName(properties.getProperty("SQLDriver"));
		} catch (ClassNotFoundException e) {
			System.out.println("MySql load failed.");
			e.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(
					properties.getProperty("SQLAddr"),
					properties.getProperty("SQLUserName"),
					properties.getProperty("SQLUserPwd"));

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 添加新用户
	 * 
	 * @param name
	 * @param pwd
	 * @return
	 */
	public boolean addNewUser(String name, String pwd) {
		PreparedStatement statement = null;
		try {
			statement = conn
					.prepareStatement("insert into users (name, pwd) values (?, ?)");
			statement.setString(1, name);
			statement.setString(2, pwd);
			statement.execute();
		} catch (SQLException e) {
			return false;
		} finally {
			try {
				if (statement != null)
					statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	/**
	 * 释放资源
	 */
	public void dispose() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn = null;
		}
	}

}
