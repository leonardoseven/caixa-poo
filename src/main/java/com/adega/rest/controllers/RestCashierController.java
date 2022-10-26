package com.adega.rest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adega.DTO.CashierDTO;
import com.adega.Services.CashierService;

@RestController
@RequestMapping("/rest/cashier")
public class RestCashierController {
	
	@Autowired
	CashierService cashierService;

	@GetMapping("/product/{quantity:.+}/{barcode:.+}")
	public ResponseEntity<CashierDTO> findProduct(@PathVariable(value="barcode") String barcode,@PathVariable(value="quantity") String quantity){
		CashierDTO product = cashierService.findProduct(barcode, quantity);
		return new ResponseEntity<CashierDTO>(product, HttpStatus.OK);
	}
	
	@GetMapping("/sumAll/{list:.+}")
	public ResponseEntity<String> sumAllProduct(@PathVariable(value="list") String list){
		String value = cashierService.sumAllProduct(list);
		return new ResponseEntity<String>(value, HttpStatus.OK);
	}
}
