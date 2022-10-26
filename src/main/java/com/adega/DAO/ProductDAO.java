package com.adega.DAO;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.adega.domains.Product;

@Repository
public class ProductDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public List<Product> getListProduct() {
		String sql = "SELECT * FROM tbproduct where active = 1 order by description";
		List<Product> listProduct = jdbcTemplate.query(
				  sql, (rs, rowNum) ->
				  new Product(rs.getInt("id"), rs.getString("description"),
				  rs.getString("provider"), rs.getInt("currentQuantity"), rs.getInt("alertQuantity"),
				  rs.getTimestamp("validity").toLocalDateTime(), rs.getBigDecimal("price"),rs.getBigDecimal("trueprice"),rs.getString("gain"),  rs.getString("barcode")));

		return listProduct;
	}

	public Product findProduct(String productId) {
		String sql = "SELECT * FROM tbproduct where id = ? and active = 1";
		Product product = null;
		try {
			product = jdbcTemplate.queryForObject(sql,
					new Object[] {productId},(rs, rowNum) -> 
					new Product(rs.getInt("id"), rs.getString("description"),
					rs.getString("provider"), rs.getInt("currentQuantity"), rs.getInt("alertQuantity"),
					rs.getTimestamp("validity").toLocalDateTime() , rs.getBigDecimal("price"),rs.getBigDecimal("trueprice"),rs.getString("gain"), rs.getString("barcode")));
		}catch (Exception e) {
			
		}
		return product;
	}

	public void deleteProduct(String productId) {
		jdbcTemplate.update("UPDATE tbproduct set active = 0,  barcode = null where id = ?", productId);
	}

	public void save(Product product) {
		jdbcTemplate.update("INSERT INTO tbproduct (description, provider, currentQuantity, alertQuantity, validity, price, trueprice, gain, barcode, active) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
				product.getDescription(), product.getProvider(), product.getCurrentQuantity(), product.getAlertQuantity(), product.getValidity(), product.getPrice(),product.getTruePrice(),product.getGain(), product.getBarcode(), 1);
		
	}

	public void update(Product product) {
		jdbcTemplate.update("UPDATE tbproduct set description = ?, provider = ?, currentQuantity = ?, alertQuantity = ?, validity = ?,price = ? , trueprice = ?, gain = ?, barcode = ? where id = ?",
				product.getDescription(), product.getProvider(), product.getCurrentQuantity(), product.getAlertQuantity(), product.getValidity(), product.getPrice(),product.getTruePrice(),product.getGain(), product.getBarcode(), product.getId());
	}

	public Product findProductByBarcode(String barcode) {
		try {
			String sql = "SELECT id, description, price, barcode  FROM tbproduct where barcode = ? and active = 1";
			Product product = jdbcTemplate.queryForObject(sql,
					new Object[] {barcode},(rs, rowNum) -> 
					new Product(rs.getInt("id"), rs.getString("description"),
					rs.getBigDecimal("price"), rs.getString("barcode")));
			return product;
		}catch(EmptyResultDataAccessException e) {
			return null;
		}
	}

	public void updateQuantity(String productId, int currentQuantity) {
		jdbcTemplate.update("UPDATE tbproduct set currentQuantity = ? where id = ?",
				currentQuantity, productId );
	}
	
	
	public List<Product> getLowQuantity() {
		String sql = "SELECT * FROM tbproduct WHERE alertquantity >= currentquantity and active = 1";
		List<Product> listProduct = jdbcTemplate.query(
				  sql, (rs, rowNum) ->
				  new Product(rs.getInt("id"), rs.getString("description"),
				  rs.getString("provider"), rs.getInt("currentQuantity"), rs.getInt("alertQuantity"),
				  rs.getTimestamp("validity").toLocalDateTime(), rs.getBigDecimal("price"),rs.getBigDecimal("trueprice"),rs.getString("gain"),  rs.getString("barcode")));

		return listProduct;
	}
	
	public List<Product> getByValidity(LocalDateTime time) {
		String sql = "SELECT * FROM tbproduct WHERE validity < ? and active = 1";
		List<Product> listProduct = jdbcTemplate.query(
				  sql,new Object[] {time}, (rs, rowNum) ->
				  new Product(rs.getInt("id"), rs.getString("description"),
				  rs.getString("provider"), rs.getInt("currentQuantity"), rs.getInt("alertQuantity"),
				  rs.getTimestamp("validity").toLocalDateTime(), rs.getBigDecimal("price"),rs.getBigDecimal("trueprice"),rs.getString("gain"),  rs.getString("barcode")));

		return listProduct;
	}
}
