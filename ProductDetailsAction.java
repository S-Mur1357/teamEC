package com.internousdev.bianco.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.internousdev.bianco.dao.ProductInfoDAO;
import com.internousdev.bianco.dto.ProductInfoDTO;
import com.opensymphony.xwork2.ActionSupport;

public class ProductDetailsAction extends ActionSupport implements SessionAware {

	private int productId;

	private ProductInfoDTO productDetailsDTO = new ProductInfoDTO();
	private Map<String, Object> session;
	List<ProductInfoDTO> relatedProductList;
	List<Integer> productCountList;

	public String execute() {

		ProductInfoDAO productInfoDAO = new ProductInfoDAO();
		productDetailsDTO = productInfoDAO.getProductInfoByProductId(productId);

		if (productDetailsDTO.getProductId() == 0) {
			productDetailsDTO = null;
		} else {
			//購入個数のリスト
			productCountList = new ArrayList<Integer>();
			for (int i = 1; i <= 5; i++) {
				productCountList.add(i);
			}
			try {
				//関連商品を探す
				relatedProductList = productInfoDAO.getRelatedProductList(productDetailsDTO.getCategoryId(),
				productDetailsDTO.getProductId(), 0, 3);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public ProductInfoDTO getProductDetailsDTO() {
		return productDetailsDTO;
	}

	public void setProductDetailsDTO(ProductInfoDTO productDetailsDTO) {
		this.productDetailsDTO = productDetailsDTO;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public List<ProductInfoDTO> getRelatedProductList() {
		return relatedProductList;
	}

	public void setRelatedProductList(List<ProductInfoDTO> relatedProductList) {
		this.relatedProductList = relatedProductList;
	}

	public List<Integer> getProductCountList() {
		return productCountList;
	}

	public void setProductCountList(List<Integer> productCountList) {
		this.productCountList = productCountList;
	}
}
