package com.adega.domains;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Transient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Sales {

	
	
	public Sales(int id, String userName, String price, String payment, LocalDateTime time, String desconto) {
		super();
		this.id = id;
		this.userName = userName;
		this.price = price;
		this.payment = payment;
		this.time = time;
		this.desconto = desconto;
	}
	private int id;
	private String  userName;
	private String price;
	private String payment;
	private String desconto;
	private LocalDateTime time;
	
	@Transient
	private List<Product> listProduct;
	
}
