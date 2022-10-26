package com.adega.domains;

import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Role implements GrantedAuthority{

	private static final long serialVersionUID = 1L;
	
	private String name;

	@Override
	public String getAuthority() {
		return this.name;
	}
	
}
