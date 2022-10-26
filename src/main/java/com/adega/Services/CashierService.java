package com.adega.Services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.adega.DTO.CashierDTO;
import com.adega.components.UserLogged;
import com.adega.device.ComponentDevices;
import com.adega.device.Impressora;
import com.adega.domains.Product;
import com.adega.domains.Sales;
import com.adega.enuns.DeviceType;
import com.adega.utils.ConvertMoney;

@Service
public class CashierService {
	
	@Autowired
	ProductService productService;
	
	@Autowired
	SalesService salesService;
	
	@Autowired
	UserLogged userLogged;

	
	@Autowired
	SalesXProductService salesXProductService;

	public CashierDTO findProduct(String barcode, String quantity) {
		if(!StringUtils.isEmpty(barcode)) {
			Product product = productService.findProductByBarcode(barcode);
			if(!StringUtils.isEmpty(product)) {
				CashierDTO prod = new CashierDTO(product);
				prod.setQuantity(Integer.valueOf(quantity));
				prod.setSinglePrice(ConvertMoney.BigDecimalToString(prod.getBigSinglePrice()));
				if(Integer.valueOf(quantity) > 1) {
					prod = sumPrice(quantity, prod);
				}else {
					prod.setTotalPrice(ConvertMoney.BigDecimalToString(prod.getBigSinglePrice()));
				}
				return prod;
			}
		}
		return null;
	}

	private CashierDTO sumPrice(String quantity, CashierDTO prod) {
		BigDecimal total = prod.getBigSinglePrice().multiply(new BigDecimal(quantity));
		prod.setTotalPrice(ConvertMoney.BigDecimalToString(total));
		return prod;
	}

	public String sumAllProduct(String values) {
		if(values.contains(",")) {
			String[] arrayValues = values.split(",");
			BigDecimal total = new BigDecimal(0);
			for(int i = 0; i < arrayValues.length; i++) {
				total = total.add(new BigDecimal(arrayValues[i]));
			}
			return ConvertMoney.BigDecimalToString(total);
		}else {
			return ConvertMoney.BigDecimalToString(new BigDecimal(values));
		}
	}

	public void finish(List<String> listProducts,String desconto, String print, String total, String formPayment) {
		List<Product> listProd = new ArrayList<Product>();
		if(!StringUtils.isEmpty(listProducts)) {
			if(listProducts.size() > 0) {
				for (String prod : listProducts) {
					//[0] id, [1] quantity, [2] formpayment
					String[] dadosProd = prod.split("-");
					Product product = productService.findProduct(dadosProd[0]);
					product.setQuantity(dadosProd[1]);
					product.setTotalPriceQuantity(product.getPrice().multiply(new BigDecimal(product.getQuantity())));
					listProd.add(product);
					if(!StringUtils.isEmpty(product)) {
						int currentQuantity = product.getCurrentQuantity() - Integer.parseInt(dadosProd[1]);
						productService.updateCurrentQuantity(dadosProd[0], currentQuantity);
					}
				}
			}
		}
		if(!StringUtils.isEmpty(listProd)) {
			if(listProd.size() > 0) {
				Sales sales = salesService.save(LocalDateTime.now().minusHours(1),userLogged.getName(),total, formPayment, desconto);
				salesXProductService.save(sales.getId(), listProd);
				if(print.equals("1")) {
					Impressora impressora = new Impressora();
					impressora.setId("1");
					String html = treatHTML(listProd, total, formPayment, desconto);
					impressora.setHtml(html);
					ComponentDevices.executeDevice(impressora, DeviceType.IMPRESSORA);
				}
			}
		}
		
	}

	private String treatHTML(List<Product> listProd,String total, String formPayment, String desconto) {
		 	LocalDateTime now = LocalDateTime.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
	        String formatDateTime = now.format(formatter);
	        
			String html = "<h4 style='text-align:center;margin:0px;padding:0px'>BLACK HORSE</h4>"
					+ "<h5 style=\"margin:0px;padding:0px\">LOGRADOURO: Rua ARMANDO POCI Nº 139 </h5>"
					+ "<h5 style=\"margin:0px;padding:0px\">MUNICIPIO: TABOÃO DA SERRA </h5>"
					+ "<h5 style=\"margin:0px;padding:0px\">CEP: 06.786-170 </h5>"
					+ "<h5 style=\"margin:0px;padding:0px\">CNPJ: 35.420.418/0001-32</h5>"
					+ "<h5 style=\"margin:0px;padding:0px\">DATA: "+formatDateTime+"</h5>"
					+ "<h5 style=\"margin:0px;padding:0px\">-------------------------------------------------</h5>"
					+ "<h5 style='text-align:center;margin:0px;padding:0px'>CUPOM NÃO FISCAL</h5>"
					+ "<h5 style=\"margin:0px;padding:0px\">-------------------------------------------------</h5>"
					+ "<table><tbody><tr><th style='width:130px;text-align:center'>PRODUTO</th><th style='text-align:center'>PREÇO</th><th style='text-align:center' >QTD</th><th style='text-align:center'>TOTAL</th></tr>";
			for (Product product : listProd) {
				html += "<tr style=\"margin:0px;padding:0px\">"
						+ "<td><h5 style=\"margin:0px;padding:0px\">"+product.getDescription()+"</h5></td>"
						+ "<td style='text-align:center'> <h5 style=\"margin:0px;padding:0px\">"+product.getPrice()+"</h5></td>"
						+"<td style='text-align:center'><h5 style=\"margin:0px;padding:0px\">x"+product.getQuantity()+"</h5></td>"
						+"<td style='text-align:center'><h5 style=\"margin:0px;padding:0px\">"+product.getTotalPriceQuantity()+"</h5></td></tr>";
						
			}
			if(!StringUtils.isEmpty(desconto)) {
				html += "<tr><td><h5 style=\"margin:0px;padding:0px\">DESCONTO:</h5></td><td></td><td></td><td style='text-align:center'><h5 style=\"margin:0px;padding:0px\">- "+desconto+"</td></tr>";
			}
			html += "<tr><td><h5 style=\"margin:0px;padding:0px\">TOTAL:</h5></td><td></td><td></td><td style='text-align:center'><h5 style=\"margin:0px;padding:0px\">"+total.replace("R$: ", "")+"</td></tbody></table>";
			html += "<h5 style='text-align:center;margin:0px;padding:0px'>OBRIGADO VOLTE SEMPRE!</h5>";
			html += "<h5 style='text-align:center;margin:0px;padding:0px'>M.D.P.F</h5>";
			return html; 
		}

	public List<Product> getListProduct() {
		return productService.getListProduct();
	}
	

}
