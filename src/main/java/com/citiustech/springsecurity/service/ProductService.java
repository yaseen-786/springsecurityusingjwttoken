package com.citiustech.springsecurity.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.citiustech.springsecurity.entity.UserInfo;
import com.citiustech.springsecurity.model.Product;
import com.citiustech.springsecurity.repository.UserRepository;

import jakarta.annotation.PostConstruct;

@Service
public class ProductService {
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	List<Product> productList = new ArrayList<>();
	@PostConstruct
    public void loadProductsFromDB() {
        productList.add(new Product(1,"smartphone",20,2000.0));
        productList.add(new Product(2,"phone",30,3000.0));
        
     
               
    }


    public List<Product> getProducts() {
        return productList;
    }
    
    
    public String adduser(UserInfo user) {
    	user.setPassword(encoder.encode(user.getPassword()));
    	repo.save(user);
    	return "useradded to system";
    }
    
}
