package com.adega.domains;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User implements UserDetails{
	
	private static final long serialVersionUID = 1L;

	public User() {};
	
	public User(int id, String name, String email, String password , String access) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.access = access;
	}

	private int id;

	private String name;
	
	private String email;
	
	private String password;
	
	private String access;
	
	private int active = 1;
	
	List<Role> roles = new ArrayList<Role>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
}
