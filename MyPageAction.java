package com.internousdev.bianco.action;

import java.sql.SQLException;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.bianco.dao.UserInfoDAO;
import com.internousdev.bianco.dto.UserInfoDTO;
import com.opensymphony.xwork2.ActionSupport;

public class MyPageAction extends ActionSupport implements SessionAware {

	UserInfoDTO userInfoDTO;
	private Map<String, Object> session;

	public String execute() throws SQLException {

		String result = SUCCESS;

		String tmpLogined = String.valueOf(session.get("logined"));
		int logined = "null".equals(tmpLogined) ? 0 : Integer.parseInt(tmpLogined);

		if (logined != 1) {
			return "loginError";
		}

		UserInfoDAO userInfoDAO = new UserInfoDAO();
		userInfoDTO = userInfoDAO.getUserInfo(String.valueOf(session.get("userId")));
		return result;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public UserInfoDTO getUserInfoDTO() {
		return userInfoDTO;
	}

	public void setUserInfoDTO(UserInfoDTO userInfoDTO) {
		this.userInfoDTO = userInfoDTO;
	}
}
