package com.adega.DTO;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SoldDTO {

	private List<String> list  = new ArrayList<String>();
	private String total;
	private String formPayment;
	private String print = "0";
	private String desconto;
}
