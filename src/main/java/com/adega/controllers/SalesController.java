package com.adega.controllers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adega.DTO.SalesDTO;
import com.adega.Services.ProductService;
import com.adega.Services.SalesService;
import com.adega.Services.SalesXProductService;
import com.adega.device.ComponentDevices;
import com.adega.device.Impressora;
import com.adega.domains.Product;
import com.adega.domains.Sales;
import com.adega.domains.SalesXProduct;
import com.adega.enuns.DeviceType;

@Controller
@RequestMapping("/sales")
public final class SalesController {

	@Autowired
	SalesService salesService;
	
	@Autowired
	SalesXProductService salesXProductService;
	
	@Autowired
	ProductService productService;
	
	@RequestMapping()
	public String sales(Model model, HttpServletRequest request) {
		String dateInitial = request.getParameter("dateInicial");
		String dateFinal  = request.getParameter("dateFinal");
		SalesDTO dto = new SalesDTO();
		setDtoTime(dateInitial, dateFinal, dto);
		List<Sales> listSales = null;
		if(!StringUtils.isEmpty(dateInitial) && !StringUtils.isEmpty(dateFinal)) {
			listSales = salesService.getListSales(dateInitial, dateFinal);
			if(StringUtils.isEmpty(listSales)) {
				model.addAttribute("empty", "empty");
			}else if(listSales.size() == 0) {
				model.addAttribute("empty", "empty");
			}
			for (Sales sales : listSales) {
				List<SalesXProduct> listRelation = salesXProductService.getListBySalesId(sales.getId());
				List<Product> listProduct = new ArrayList<Product>();
				if(!CollectionUtils.isEmpty(listRelation)) {
					for (SalesXProduct salesxproduct : listRelation) {
						Product product = productService.findProduct(String.valueOf(salesxproduct.getProductId()));
						if(product != null) {
							product.setQuantity(String.valueOf(salesxproduct.getQuantity()));
							setTotalPriceAndGain(product, salesxproduct.getQuantity(), dto);
							listProduct.add(product);
						}
					}
				}
				removeDesconto(sales, dto);
				sales.setListProduct(listProduct);	
			}
			model.addAttribute("listSales", listSales);
			model.addAttribute("total", dto);
		}else {
			if(dateInitial != null) {
				model.addAttribute("empty", "empty");
			}
		}
		return "list-sales";
	}
	
	private void removeDesconto(Sales sales, SalesDTO dto) {
		if(!StringUtils.isEmpty(sales.getDesconto())) {
			BigDecimal desconto = new BigDecimal(Double.valueOf(sales.getDesconto().replace(".", "").replace(",", ".")));
			dto.setTotalGain(dto.getTotalGain().subtract(desconto));
			dto.setTotalPrice(dto.getTotalPrice().subtract(desconto));
		}
	}

	private void setDtoTime(String dateInitial, String dateFinal, SalesDTO dto) {
		if(dateInitial != null) {
			String date = dateInitial.replace("-", "");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
			LocalDateTime begin = LocalDate.parse(date, formatter).atStartOfDay();
			dto.setDtInitial(begin);
		}
	
		if(dateFinal != null) {
			String date2 = dateFinal.replace("-", "");
			DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyyMMdd");
			LocalDateTime end = LocalDate.parse(date2, formatter2).atStartOfDay();
			dto.setDtFinal(end);
		}
	
	}

	private void setTotalPriceAndGain(Product product, int qunatity, SalesDTO dto) {
		if(dto.getTotalPrice() == null && dto.getTotalGain() == null) {
			if(dto.getTotalPrice() == null) {
				dto.setTotalPrice(product.getPrice().multiply(new BigDecimal(qunatity)));
			}
			if(dto.getTotalGain() == null) {
				dto.setTotalGain(subtractBigDecimal(product.getPrice(), product.getTruePrice()).multiply(new BigDecimal(qunatity)));
			}
			return;
		}
		dto.setTotalPrice((dto.getTotalPrice().add(product.getPrice().multiply(new BigDecimal(qunatity)))));
		dto.setTotalGain(dto.getTotalGain().add(subtractBigDecimal(product.getPrice(), product.getTruePrice()).multiply(new BigDecimal(qunatity))));
	}
	
	private BigDecimal subtractBigDecimal(BigDecimal numerador, BigDecimal denomindador) {
		return numerador.subtract(denomindador);
	}

	@GetMapping("/extornar")
	public String extornar(Model model, HttpServletRequest request) {
		String listProd = request.getParameter("prods");
		listProd = listProd.replace("[", "");
		listProd = listProd.replace("]", "");
		listProd = listProd.replace(" ", "");
		String[] prods  = listProd.split(",");
		for(int i = 0; i < prods.length;i++ ) {
			String[] item = prods[i].split("-");
			Product product = productService.findProduct(item[0]);
			productService.updateCurrentQuantity(item[0], product.getCurrentQuantity()+Integer.valueOf(item[1]));
		}
		String saleId= request.getParameter("saleId");
		salesXProductService.deleteBySaleId(saleId);
		salesService.deleteSale(saleId);
		model.addAttribute("ex", "ex");
		return "list-sales";
	}
	
	@GetMapping("/print/{dtInitial:.+}/{dtFinal:.+}/{tvenda:.+}/{tlucro:.+}")
	public ResponseEntity<String> doPrintReport(@PathVariable(value="dtInitial") String dtInitial,@PathVariable(value="dtFinal") String dtFinal,@PathVariable(value="tvenda") String totalVenda,@PathVariable(value="tlucro") String totalLucro ){
		String html = "<h4 style='text-align:center;margin:0px;padding:0px'>BLACK HORSE</h4>"
				+ "<h5 style=\"text-align:center;margin:0px;padding:0px\">DATA: "+dtInitial+" á "+dtFinal+"</h5>"
				+ "<h5 style=\"text-align:center;margin:0px;padding:0px\">TOTAL DE VENDA: R$ "+totalVenda+"</h5>"
				+ "<h5 style=\"text-align:center;margin:0px;padding:0px\">TOTAL DE LUCRO: R$ "+totalLucro+"</h5>";
		Impressora impressora = new Impressora();
		impressora.setId("1");
		impressora.setHtml(html);
		ComponentDevices.executeDevice(impressora, DeviceType.IMPRESSORA);
		return new ResponseEntity<String>(HttpStatus.OK);
	}
}
