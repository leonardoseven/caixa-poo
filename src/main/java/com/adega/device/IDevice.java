package com.adega.device;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

public interface IDevice {

	JSONObject getJson() throws JSONException;
	
}
