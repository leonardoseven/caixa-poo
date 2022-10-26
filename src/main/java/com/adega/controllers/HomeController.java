package com.adega.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adega.Services.ProductService;
import com.adega.domains.Product;

@Controller
@RequestMapping("/home")
public class HomeController {

	@Autowired
	ProductService productService;
	
	@RequestMapping()
	public String home(Model model) {
		List<Product> listQuantity = productService.getLowQuantity();
		List<Product> listValidity = productService.getByValidity();
		model.addAttribute("lowQuantity", listQuantity);
		model.addAttribute("validity", listValidity);
		return "home";
	}
	
}
