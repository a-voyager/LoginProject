package com.voyager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * ���ݿ������
 * 
 * @author wuhaojie
 *
 */
public class UserPwdDao {

	/**
	 * ���ݿ����Ӷ���
	 */
	private Connection conn;

	public UserPwdDao() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("MySql load failed.");
			e.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/users", "root", "root");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * ������û�
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
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	/**
	 * �ͷ���Դ
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
