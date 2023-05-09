package com.citiustech.springsecurity.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.citiustech.springsecurity.entity.UserInfo;

public class UserInfoUserDetails  implements UserDetails{

	private String username;
	
	private String password;
	
	private List<GrantedAuthority> authorities;
	
	
	
	public UserInfoUserDetails(UserInfo userinfo) {
		username = userinfo.getUsername();
		password = userinfo.getPassword();
		authorities = Arrays.stream(userinfo.getRoles().split(","))
				.map(SimpleGrantedAuthority::new).collect(Collectors.toList());
		// TODO Auto-generated constructor stub
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
