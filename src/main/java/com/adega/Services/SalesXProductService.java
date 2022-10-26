package com.adega.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adega.DAO.SalesXProductDAO;
import com.adega.domains.Product;
import com.adega.domains.SalesXProduct;

@Service
public class SalesXProductService {
	
	@Autowired
	SalesXProductDAO salesXProductDAO;

	public void save(int id, List<Product> listProd) {
		for (Product product : listProd) {
			salesXProductDAO.save(id, product.getId(), product.getQuantity());
		}
		
	}

	public List<SalesXProduct> getListBySalesId(int id) {
		return salesXProductDAO.getListBySalesId(id);
	}

	public void deleteBySaleId(String saleId) {
		salesXProductDAO.deleteBySaleId(saleId);
	}

}
