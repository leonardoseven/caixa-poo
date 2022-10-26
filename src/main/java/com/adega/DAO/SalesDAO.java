package com.adega.DAO;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.adega.domains.Product;
import com.adega.domains.Sales;

@Repository
public class SalesDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public Sales save(LocalDateTime now, String name, String total, String formPayment, String deconto) {
		jdbcTemplate.update("INSERT INTO tbsales (now, username, total, formpayment, desconto) VALUES (?, ?, ?, ?, ?)",
				now, name,total, formPayment, deconto);
		
		String sql = "SELECT * FROM tbsales where now = ?";
		Sales sales= jdbcTemplate.queryForObject(sql,
				new Object[] {now},(rs, rowNum) -> 
				new Sales(rs.getInt("id"), rs.getString("username"),
				rs.getString("total"), rs.getString("formpayment"), 
				rs.getTimestamp("now").toLocalDateTime(),  rs.getString("desconto")));
		return sales;
		
	}

	public List<Sales> getListSales(LocalDateTime begin, LocalDateTime end) {
		String sql = "SELECT * FROM tbsales where now between ? and ? ";
		List<Sales> listSales = jdbcTemplate.query(
				  sql, new Object[] {begin, end}, (rs, rowNum) ->
				  new Sales(rs.getInt("id"), rs.getString("username"),
							rs.getString("total"), rs.getString("formpayment"), 
							rs.getTimestamp("now").toLocalDateTime(), rs.getString("desconto")));

		return listSales;
	}

	public void deleteSale(String saleId) {
		 String sql = "DELETE FROM tbsales WHERE id = ?";
		 Object[] args = new Object[] {saleId};
		 jdbcTemplate.update(sql, args);
	}
	
}
