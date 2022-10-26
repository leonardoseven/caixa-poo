package com.adega.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adega.Services.ProductService;
import com.adega.domains.Product;
import com.adega.utils.ConvertMoney;

@Controller
@RequestMapping("/product")
public class ProductController {

	@Autowired
	ProductService productService;
	
	@GetMapping("/new")
	public String product(Model model) {
		Product product = new Product();
		model.addAttribute("product", product);
		return "product/form-product";
	}
	
	@GetMapping("/list")
	public String listProduct(Model model) {
		List<Product> listProduct = productService.getListProduct();
		model.addAttribute("listProduct", listProduct);
		return "product/list-product";
	}
	
	@GetMapping("/edit/{productid:.+}")
	public String editProduct(Model model, @PathVariable(value="productid") String productId) {
		Product product = null;
		if(!StringUtils.isEmpty(productId)) {
			product = productService.findProduct(productId);
		}
		model.addAttribute("product", product);
		model.addAttribute("edit", "edit");
		return "product/form-product";
	}
	
	@GetMapping("/delete/{productid:.+}")
	public String deleteProduct(Model model, @PathVariable(value="productid") String productId) {
		if(!StringUtils.isEmpty(productId)) {
			productService.deleteProduct(productId);
		}
		return listProduct(model);
	}
	
	@PostMapping("/save")
	public String saveProduct(Model model,Product product) {
		productService.save(product);
		return listProduct(model);
	}
	
	@GetMapping("/calc/gain/{price:.+}/{trueprice:.+}")
	public ResponseEntity<String> calcGain(@PathVariable(value="price") String price,@ PathVariable(value="trueprice")String truePrice){
		String gain = "0";
		if(!StringUtils.isEmpty(price)) {
			BigDecimal bigPrice = new BigDecimal(price);
			 gain = ConvertMoney.BigDecimalToString(bigPrice);
			if(!StringUtils.isEmpty(truePrice)) {
				bigPrice = bigPrice.subtract(new BigDecimal(truePrice));
				 gain = ConvertMoney.BigDecimalToString(bigPrice);
			}
		}
		return new ResponseEntity<String>(gain, HttpStatus.OK);
	}
	
	@GetMapping("/calc/troco/{price:.+}/{trueprice:.+}")
	public ResponseEntity<String> calcTroco(@PathVariable(value="price") String price,@ PathVariable(value="trueprice")String truePrice){
		String gain = "0";
		if(!StringUtils.isEmpty(truePrice)) {
			BigDecimal bigPrice = new BigDecimal(truePrice);
			 gain = ConvertMoney.BigDecimalToString(bigPrice);
			if(!StringUtils.isEmpty(price)) {
				bigPrice = bigPrice.subtract(new BigDecimal(price));
				 gain = ConvertMoney.BigDecimalToString(bigPrice);
			}
		}
		return new ResponseEntity<String>(gain, HttpStatus.OK);
	}
	
	@GetMapping("/calc/desconto/{price:.+}/{trueprice:.+}")
	public ResponseEntity<String> calcDesconto(@PathVariable(value="price") String price,@ PathVariable(value="trueprice")String truePrice){
		String gain = "0";
		if(!StringUtils.isEmpty(truePrice)) {
			BigDecimal bigPrice = new BigDecimal(truePrice);
			 gain = ConvertMoney.BigDecimalToString(bigPrice);
			if(!StringUtils.isEmpty(price)) {
				bigPrice = bigPrice.subtract(new BigDecimal(price));
				 gain = ConvertMoney.BigDecimalToString(bigPrice);
			}
		}
		return new ResponseEntity<String>(gain, HttpStatus.OK);
	}
	
	@GetMapping("/verify/barcode/{barcode:.+}")
	public ResponseEntity<String> veridyBarCode(@PathVariable(value="barcode") String barcode){
		Product prod = productService.findProductByBarcode(barcode);
		if(!StringUtils.isEmpty(prod)) {
			return new ResponseEntity<String>("true", HttpStatus.OK);
		}
		return new ResponseEntity<String>(HttpStatus.OK);
		
	}
}







