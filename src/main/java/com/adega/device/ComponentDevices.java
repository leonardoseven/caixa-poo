package com.adega.device;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.adega.enuns.DeviceType;

@Controller
@RequestMapping("/device")
public class ComponentDevices  {
	private static String reponseJson = "";
	private static int contador = 0;
	
	@GetMapping("/execute")
	public static synchronized ResponseEntity<String> executeDevice(IDevice device, DeviceType type ) {
		try {
			if(!reponseJson.equals("") && contador >= 2) {
				reponseJson = "";
				contador = 0;
			}
			if(contador == 1) {
				contador++; 
			}
			JSONObject json = new JSONObject();
			if(type != null) {
				json.put("Type", type);
				json.put("Device", device.getJson() );
				reponseJson = json.toString();
				contador++;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>(reponseJson, HttpStatus.OK);
	}
	
	
	@GetMapping("/getresult")
	public static ResponseEntity<String> deviceResult(HttpServletRequest request) {
		String nome = request.getParameter("nome");
		
		System.out.println(nome);
		return new ResponseEntity<String>(reponseJson, HttpStatus.OK);
	}
	
	
	
}
