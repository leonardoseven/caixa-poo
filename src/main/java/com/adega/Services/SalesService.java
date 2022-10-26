package com.adega.Services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adega.DAO.SalesDAO;
import com.adega.domains.Sales;
import com.adega.utils.ConvertDate;

@Service
public class SalesService {
	
	@Autowired
	SalesDAO salesDAO;

	public Sales save(LocalDateTime now, String name, String total, String formPayment,String desconto) {
		return salesDAO.save(now, name, total, formPayment, desconto);
	}

	public List<Sales> getListSales(String dateInitial, String dateFinal) {
		String date = dateInitial.replace("-", "");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDateTime begin = LocalDate.parse(date, formatter).atStartOfDay();
		
		String date2 = dateFinal.replace("-", "");
		DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyyMMdd");
		LocalDateTime end = LocalDate.parse(date2, formatter2).atStartOfDay();
		return salesDAO.getListSales(begin, end);
	}

	public void deleteSale(String saleId) {  
		salesDAO.deleteSale(saleId);
	}

	
	
}
