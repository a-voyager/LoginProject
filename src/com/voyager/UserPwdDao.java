package com.voyager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
			closeStatement(statement);
		}
		return true;
	}

	/**
	 * 查询用户名密码是否正确
	 * 
	 * @param inputName
	 * @param inputPwd
	 * @return
	 */
	public boolean isUserQualifiedQuery(String inputName, String inputPwd) {
		boolean flag = false;
		Statement statement = null;
		try {
			statement = conn.createStatement();
			ResultSet set = statement
					.executeQuery("select name,pwd from users");
			System.out.println("开始查询！");
			while (set.next()) {
				String name = set.getString("name");
				String pwd = set.getString("pwd");
				System.out.println("name = " + name + "; pwd = " + pwd);
				if (name.equals(inputName) && pwd.equals(inputPwd)) {
					System.out.println("密码正确！");
					flag = true;
					break;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeStatement(statement);
		}

		return flag;
	}

	private void closeStatement(Statement statement) {
		if (statement != null)
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				statement = null;
			}
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
