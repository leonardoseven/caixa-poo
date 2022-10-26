package com.adega.enuns;

import lombok.Getter;

@Getter
public enum DeviceType {
	
	IMPRESSORA("1");

	private String id;
	
	private DeviceType (String id) {
		this.id=id;
	}

}
