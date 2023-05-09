package com.citiustech.springsecurity.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.citiustech.springsecurity.entity.UserInfo;
import com.citiustech.springsecurity.model.AuthRequest;
import com.citiustech.springsecurity.model.Product;
import com.citiustech.springsecurity.service.JwtService;
import com.citiustech.springsecurity.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
	@Autowired
	private ProductService service;

	@Autowired
	private JwtService jwtservice;

	@Autowired
	private AuthenticationManager authmanager;

	@GetMapping("/welcome")

	public String welcome() {
		return "Welcome to Citiair";
	}

	@GetMapping("/all")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public List<Product> getallproducts() {
		return service.getProducts();
	}

	@PostMapping("/new")
	public String addnewUser(@RequestBody UserInfo user) {
		return service.adduser(user);
	}

	@PostMapping("/authenticate")
	public String authenticateAndGetToken(@RequestBody AuthRequest authrequest) {
		Authentication authen = authmanager.authenticate(
				new UsernamePasswordAuthenticationToken(authrequest.getUsername(), authrequest.getPassword()));
		if (authen.isAuthenticated()) {
			return jwtservice.generateToken(authrequest.getUsername());
		} else {
			throw new UsernameNotFoundException("invalid user");
		}
	}

}
