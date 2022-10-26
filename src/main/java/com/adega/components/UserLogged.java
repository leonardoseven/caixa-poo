package com.adega.components;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component("userlogged")
public class UserLogged {

	private int id;
	private String name;
	private String role;
	
}
