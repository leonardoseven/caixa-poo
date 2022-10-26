package com.adega.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adega.DTO.SoldDTO;
import com.adega.Services.CashierService;

@Controller
@RequestMapping("/cashier")
public class CashierController {
	
	@Autowired
	CashierService cashierService;

	@RequestMapping()
	public String cashier(Model model) {
		model.addAttribute("sold", new SoldDTO());
		model.addAttribute("listProd", cashierService.getListProduct());
		return "cashier";
	}
	
	@RequestMapping("/cancel")
	public String cashierCancel(Model model) {
		model.addAttribute("sold", new SoldDTO());
		model.addAttribute("listProd", cashierService.getListProduct());
		model.addAttribute("cancel", "cancel");
		return "cashier";
	}
	
	@PostMapping("/finish")
	public String cashierFinish(SoldDTO products ,Model model) {
		List<String> listProducts = products.getList();
		cashierService.finish(listProducts,products.getDesconto(), products.getPrint(), products.getTotal(),products.getFormPayment());
		model.addAttribute("listProd", cashierService.getListProduct());
		model.addAttribute("finish", "finish");
		model.addAttribute("sold", new SoldDTO());
		return "cashier";
	}
}
