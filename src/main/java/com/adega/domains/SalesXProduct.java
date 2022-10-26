package com.adega.domains;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesXProduct {

	
	
	
	public SalesXProduct(int id, int salesId, int productId, int quantity) {
		super();
		this.id = id;
		this.salesId = salesId;
		this.productId = productId;
		this.quantity = quantity;
	}
	private int id;
	private int salesId;
	private int productId;
	private int quantity;
	
}
