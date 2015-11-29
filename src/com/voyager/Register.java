package com.voyager;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Register extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		String user = request.getParameter("user");
		String pwd = request.getParameter("pwd");
		System.out.println("user = " + user + "; pwd = " + pwd);
		if (!Utils.isUserNameQualifiedRule(user)
				|| !Utils.isUserPwdQualifiedRule(pwd)) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,
					"user || pwd is not qualified"); // 返回400 客户端句法不合法
			return;
		}
		UserPwdDao userPwdDao = new UserPwdDao();
		userPwdDao.addNewUser(user, pwd);
		userPwdDao.dispose();
	}

}
