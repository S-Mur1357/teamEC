package com.internousdev.bianco.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.internousdev.bianco.dto.ProductInfoDTO;
import com.internousdev.bianco.util.DBConnector;

public class ProductInfoDAO {

	// 商品IDから商品情報を探す
	public ProductInfoDTO getProductInfoByProductId(int productId) {

		DBConnector db = new DBConnector();
		Connection con = db.getConnection();

		ProductInfoDTO productInfoDTO = new ProductInfoDTO();

		// product_infoテーブルから商品idを選択
		String sql = "select * from product_info where product_id =?";

		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, productId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				productInfoDTO.setId(rs.getInt("id"));
				productInfoDTO.setProductId(rs.getInt("product_id"));
				productInfoDTO.setProductName(rs.getString("product_name"));
				productInfoDTO.setProductNameKana(rs.getString("product_name_kana"));
				productInfoDTO.setImageFilePath(rs.getString("image_file_path"));
				productInfoDTO.setImageFileName(rs.getString("image_file_name"));
				productInfoDTO.setPrice(rs.getInt("price"));
				productInfoDTO.setReleaseCompany(rs.getString("release_company"));
				productInfoDTO.setReleaseDate(rs.getDate("release_date"));
				productInfoDTO.setProductDescription(rs.getString("product_description"));

				productInfoDTO.setCategoryId(rs.getInt("category_id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return productInfoDTO;
	}

	// 関連商品を取得する
	public List<ProductInfoDTO> getRelatedProductList(int categoryId, int productId, int limitOffset, int limitRowCount)
			throws SQLException {

		DBConnector db = new DBConnector();
		Connection con = db.getConnection();

		List<ProductInfoDTO> productInfoDTOList = new ArrayList<>();
		String sql = "select * from product_info where category_id =? and product_id not in(?) order by rand() limit ?,?";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, categoryId);
			ps.setInt(2, productId);
			ps.setInt(3, limitOffset);
			ps.setInt(4, limitRowCount);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				ProductInfoDTO puroductInfoDTO = new ProductInfoDTO();
				puroductInfoDTO.setId(rs.getInt("id"));
				puroductInfoDTO.setProductId(rs.getInt("product_id"));
				puroductInfoDTO.setProductName(rs.getString("product_name"));
				puroductInfoDTO.setProductNameKana(rs.getString("product_name_kana"));
				puroductInfoDTO.setProductDescription(rs.getString("product_description"));
				puroductInfoDTO.setCategoryId(rs.getInt("category_id"));
				puroductInfoDTO.setPrice(rs.getInt("price"));
				puroductInfoDTO.setImageFileName(rs.getString("image_file_name"));
				puroductInfoDTO.setImageFilePath(rs.getString("image_file_path"));
				puroductInfoDTO.setReleaseDate(rs.getDate("release_date"));
				puroductInfoDTO.setReleaseCompany(rs.getString("release_company"));
				productInfoDTOList.add(puroductInfoDTO);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return productInfoDTOList;
	}

	// キーワードを条件に商品情報を取得
	public List<ProductInfoDTO> getProductInfoListByKeyword(String[] keywordsList) {

		DBConnector db = new DBConnector();
		Connection con = db.getConnection();

		List<ProductInfoDTO> productInfoDTOList = new ArrayList<>();
		String sql = "select * from product_info";
		if (!"".equals(keywordsList[0])) {
			for (int i = 0; i < keywordsList.length; i++) {
				if (i == 0) {
					sql += " where (product_name like '%" + keywordsList[i] + "%' or product_name_kana like '%"
							+ keywordsList[i] + "%')";
				} else {
					sql += " or (product_name like '%" + keywordsList[i] + "%' or product_name_kana like '%"
							+ keywordsList[i] + "%')";
				}
			}
		}
		sql += " order by product_id asc ";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				ProductInfoDTO puroductInfoDTO = new ProductInfoDTO();
				puroductInfoDTO.setId(rs.getInt("id"));
				puroductInfoDTO.setProductId(rs.getInt("product_id"));
				puroductInfoDTO.setProductName(rs.getString("product_name"));
				puroductInfoDTO.setProductNameKana(rs.getString("product_name_kana"));
				puroductInfoDTO.setProductDescription(rs.getString("product_description"));
				puroductInfoDTO.setCategoryId(rs.getInt("category_id"));
				puroductInfoDTO.setPrice(rs.getInt("price"));
				puroductInfoDTO.setImageFileName(rs.getString("image_file_name"));
				puroductInfoDTO.setImageFilePath(rs.getString("image_file_path"));
				puroductInfoDTO.setReleaseDate(rs.getDate("release_date"));
				puroductInfoDTO.setReleaseCompany(rs.getString("release_company"));
				productInfoDTOList.add(puroductInfoDTO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return productInfoDTOList;
	}

	// カテゴリーIDとキーワードを条件に商品情報を取得
	public List<ProductInfoDTO> getProductInfoListByCategoryIdAndKeyword(String categoryId, String[] keywordsList) {

		DBConnector db = new DBConnector();
		Connection con = db.getConnection();

		List<ProductInfoDTO> productInfoDTOList = new ArrayList<>();
		String sql = "select * from product_info where category_id=" + categoryId;
		if (!"".equals(keywordsList[0])) {
			for (int i = 0; i < keywordsList.length; i++) {
				if (i == 0) {
					sql += " and ((product_name like '%" + keywordsList[i] + "%' or product_name_kana like '%"
							+ keywordsList[i] + "%')";
				} else {
					sql += " or (product_name like '%" + keywordsList[i] + "%' or product_name_kana like '%"
							+ keywordsList[i] + "%')";
				}
			}
			sql +=")";
		}
		sql += " order by product_id asc ";
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				ProductInfoDTO puroductInfoDTO = new ProductInfoDTO();
				puroductInfoDTO.setId(rs.getInt("id"));
				puroductInfoDTO.setProductId(rs.getInt("product_id"));
				puroductInfoDTO.setProductName(rs.getString("product_name"));
				puroductInfoDTO.setProductNameKana(rs.getString("product_name_kana"));
				puroductInfoDTO.setProductDescription(rs.getString("product_description"));
				puroductInfoDTO.setCategoryId(rs.getInt("category_id"));
				puroductInfoDTO.setPrice(rs.getInt("price"));
				puroductInfoDTO.setImageFileName(rs.getString("image_file_name"));
				puroductInfoDTO.setImageFilePath(rs.getString("image_file_path"));
				puroductInfoDTO.setReleaseDate(rs.getDate("release_date"));
				puroductInfoDTO.setReleaseCompany(rs.getString("release_company"));
				productInfoDTOList.add(puroductInfoDTO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return productInfoDTOList;
	}
}
