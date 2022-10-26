package com.adega.device;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Impressora implements IDevice{
	
	private String id;
	private String html;
	private String pathLogo;
	
	@Override
	public JSONObject getJson() throws JSONException  {
		JSONObject json = new JSONObject();
		json.put("id", "1");
		json.put("html", html);
		json.put("pathLogo", pathLogo);
		return json;
	}

}
