package com.adega.DAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.adega.domains.Product;
import com.adega.domains.SalesXProduct;

@Repository
public class SalesXProductDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public void save(int saleid, int productId, String quantity) {
		jdbcTemplate.update("INSERT INTO tbsalesxproduct (idsale, idproduct, quantity) VALUES (?, ?,? )",
				saleid, productId, quantity);
	}

	public List<SalesXProduct> getListBySalesId(int id) {
		String sql = "SELECT * FROM tbsalesxproduct where idsale = ?";
		List<SalesXProduct> listSalesXProduct = jdbcTemplate.query(
				  sql,new Object[] {id}, (rs, rowNum) ->
				  new SalesXProduct(rs.getInt("id"), rs.getInt("idsale"),
						  rs.getInt("idproduct"),
						  rs.getInt("quantity")
				));

		return listSalesXProduct;
	}

	public void deleteBySaleId(String saleId) {
	    String sql = "DELETE FROM tbsalesxproduct WHERE idsale = ?";
	    Object[] args = new Object[] {saleId};
	    jdbcTemplate.update(sql, args);
	}

}
