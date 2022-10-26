package com.adega.domains;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Transient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {

	public Product() {}
		
	public Product(int id, String description, String provider, int currentQuantity, int alertQuantity,
			LocalDateTime validity,BigDecimal price, BigDecimal truePrice, String gain,
			String barcode) {
		super();
		this.id = id;
		this.description = description;
		this.provider = provider;
		this.currentQuantity = currentQuantity;
		this.alertQuantity = alertQuantity;
		this.validity = validity;
		this.price = price;
		this.truePrice = truePrice;
		this.gain = gain;
		this.barcode = barcode;
	}
	
	
	
    public Product(int id, String description, BigDecimal price, String barcode) {
		super();
		this.id = id;
		this.description = description;
		this.price = price;
		this.barcode = barcode;
	}





	private int id;
	private String description;
	private String provider;
	private int currentQuantity;
	private int alertQuantity;
	private LocalDateTime validity;
	private String svalidity;
	private BigDecimal price;
	private BigDecimal truePrice;
	private String gain;
	private String barcode;
	private int active;
	
	
	@Transient
	private String quantity;
	
	@Transient
	private BigDecimal totalPriceQuantity;
	
	@Override
	public String toString() {
		return this.id+"-"+this.quantity;
	}
	
}
