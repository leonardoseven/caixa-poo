package com.adega.Services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.DateUtils;

import com.adega.DAO.ProductDAO;
import com.adega.domains.Product;

@Service
public class ProductService {

	
	@Autowired
	ProductDAO productDAO;

	public List<Product> getListProduct() {
		List<Product> listProduct = productDAO.getListProduct();
		return listProduct;
	}

	public Product findProduct(String productId) {
		return productDAO.findProduct(productId);
	}

	public void deleteProduct(String productId) {
		productDAO.deleteProduct(productId);
	}

	public void save(Product product) {
		String date = product.getSvalidity().replace("/", "");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
		LocalDateTime validity = LocalDate.parse(date, formatter).atTime(1, 0);
		product.setValidity(validity);
		if(product.getId() == 0) {
			productDAO.save(product);
		}else {
			productDAO.update(product);
		}
		
	}

	public Product findProductByBarcode(String barcode) {
		return productDAO.findProductByBarcode(barcode);
	}
	
	public void updateCurrentQuantity(String productId, int currentQuantity) {
		productDAO.updateQuantity(productId, currentQuantity);
	}

	public List<Product> getLowQuantity() {
		return productDAO.getLowQuantity();
	}

	public List<Product> getByValidity() {
		return productDAO.getByValidity(LocalDateTime.now().minusMonths(1));
	}
}
