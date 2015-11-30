package com.voyager;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Login extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userName = request.getParameter("user");
		String userPwd = request.getParameter("pwd");
		System.out.println("******Login: user = " + userName + "; pwd = "
				+ userPwd);
		if (!Utils.isUserNameQualifiedRule(userName)
				|| !Utils.isUserPwdQualifiedRule(userPwd)) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,
					"user || pwd is not qualified"); // 返回400 客户端句法不合法
			return;
		}
		UserPwdDao userPwdDao = new UserPwdDao();
		boolean isSucceed = userPwdDao.isUserQualifiedQuery(userName, userPwd);
		userPwdDao.dispose();
		if (isSucceed) {
			System.out.println("******Login succeed");
			response.addHeader("result", "1");
		}
	}

}
