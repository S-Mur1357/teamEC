package com.internousdev.bianco.action;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.bianco.dao.CartInfoDAO;
import com.internousdev.bianco.dto.CartInfoDTO;
import com.opensymphony.xwork2.ActionSupport;

public class AddCartAction extends ActionSupport implements SessionAware {

	private int productId;
	private int productCount;
	private int totalPrice;
	private Map<String, Object> session;
	private List<CartInfoDTO> cartInfoDTOList;

	public String execute() throws SQLException {

		String result = ERROR;

		String tmpLogined = String.valueOf(session.get("logined"));

		int logined;
		if ("null".equals(tmpLogined)) {
			logined = 0;
		} else {
			logined = Integer.parseInt(tmpLogined);
		}

		String userId = null;
		if (logined == 1) {
			userId = session.get("userId").toString();
			result = SUCCESS;
		} else {
			userId = String.valueOf(session.get("tmpUserId"));
			result = SUCCESS;
		}

		if (!session.containsKey("tmpUserId") && !session.containsKey("userId")) {
			result = "sessionTimeout";
		}
		//カートに商品を新規登録する
		CartInfoDAO cartInfoDAO = new CartInfoDAO();
		int count = 0;

		if (CartInfoDAO.isExistCart(userId, productId)) {
			//追加する商品がすでにカートのデータベースにある場合、個数を更新する
			count = cartInfoDAO.productUpDate(userId, productId, productCount);
		} else {
			//カートのデータベースにない場合、個数を新しく登録する
			count = cartInfoDAO.regist(userId, productId, productCount);
		}

		if (count > 0) {
			cartInfoDTOList = cartInfoDAO.getCartInfo(userId);
			totalPrice = cartInfoDAO.getTotalPrice(userId);
			result = SUCCESS;
		}
		return result;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getProductCount() {
		return productCount;
	}

	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public List<CartInfoDTO> getCartInfoDTOList() {
		return cartInfoDTOList;
	}

	public void setCartInfoDTOList(List<CartInfoDTO> cartInfoDTOList) {
		this.cartInfoDTOList = cartInfoDTOList;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
}
