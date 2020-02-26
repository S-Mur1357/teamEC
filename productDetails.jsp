<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="utf-8">
<link rel="stylesheet" href="./css/button.css">
<link rel="stylesheet" href="./css/detail.css">
<link rel="stylesheet" href="./css/error.css">
<link rel="stylesheet" href="./css/header.css">
<link rel="stylesheet" href="./css/title.css">
	<title>商品詳細</title>
	</head>
	<body>
		<jsp:include page="header.jsp" />
		<div id="contents">
				<h1>商品詳細</h1>
				<s:if test="productDetailsDTO != null">
					<s:form action="AddCartAction">
						<div class = "2column-container">
						<div class = "left">
								<img src = '<s:property value = "productDetailsDTO.imageFilePath"/>/<s:property value="productDetailsDTO.imageFileName"/>' class="product-detail-image-box" /><br>
						</div>
						<div class = "right">
							<table class = "vertical-list-table-mini">
									<tr>
										<th scope = "row"><s:label value ="商品名"/></th>
										<td><s:property value="productDetailsDTO.productName" /></td>
									</tr>
									<tr>
										<th scope = "row"><s:label value ="商品名ふりがな"/></th>
										<td><s:property value="productDetailsDTO.productNameKana" /></td>
									</tr>
									<tr>
										<th scope = "row"><s:label value ="値段"/></th>
										<td><s:property value="productDetailsDTO.price" /><span>円</span></td>
									</tr>
									<tr>
										<th scope = "row"><s:label value ="購入個数"/></th>
										<td>
											<select name = "productCount">
													<option value = "1" selected = "selected">1</option>
													<option value = "2">2</option>
													<option value = "3">3</option>
													<option value = "4">4</option>
													<option value = "5">5</option>
											</select>
											<span>個</span>
										</td>
									</tr>
									<tr>
										<th scope = "row"><s:label value ="発売会社名"/></th>
										<td><s:property value="productDetailsDTO.releaseCompany" /></td>
									</tr>
									<tr>
										<th scope = "row"><s:label value ="発売年月日"/></th>
										<td><s:property value="productDetailsDTO.releaseDate" /></td>
									</tr>
									<tr>
										<th scope = "row"><s:label value ="商品詳細情報"/></th>
										<td><s:property value="productDetailsDTO.productDescription" /></td>
									</tr>
								</table>
								<div class="submit_btn_box">
									<s:hidden name = "productId" value = "%{productDetailsDTO.productId}" />
									<s:submit value="カートに追加" class = "submit_btn"/>
								</div>
								</div>
								</div>
							</s:form>
						<s:if test = "relatedProductList != null && relatedProductList.size()>0">
						<div class = "product-details-related-box">
						<h2>【関連商品】</h2>
							<s:iterator value="relatedProductList">
									<div class = "related-box">
										<a href='<s:url action="ProductDetailsAction">
											<s:param name="productId" value="%{productId}"/></s:url>'>
											<img  src='<s:property value="imageFilePath" />/<s:property value="imageFileName" />'class="related-product-image-box" />
													<span><s:property value="productName"/></span>
										</a>
									</div>
							</s:iterator>
						</div>
					</s:if>
				</s:if>
				<s:else>
					<div class = "info">
					<h3>商品の詳細情報がありません。</h3>
					</div>
				</s:else>
			</div>
	</body>
</html>

