package com.citiustech.springsecurity.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.citiustech.springsecurity.entity.UserInfo;
import com.citiustech.springsecurity.repository.UserRepository;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository repo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<UserInfo> User = repo.findByUsername(username);
		return User.map(UserInfoUserDetails::new).orElseThrow(()->new UsernameNotFoundException("user not found"+username) );
		
	}

}
