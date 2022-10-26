package com.adega.DTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.adega.domains.Product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CashierDTO {
	
	public CashierDTO() {}
	
    public CashierDTO(Product product) {
		super();
		this.id = product.getId();
		this.description = product.getDescription();
		this.bigSinglePrice = product.getPrice();
		this.barcode = product.getBarcode();
	}



	private int id;
	private String description;
	private int Quantity;
	private BigDecimal bigSinglePrice;
	private BigDecimal bigTotalPrice;
	private String singlePrice;
	private String totalPrice;
	private String barcode;
	private int active;

}
