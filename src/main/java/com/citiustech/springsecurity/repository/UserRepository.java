package com.citiustech.springsecurity.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.citiustech.springsecurity.entity.UserInfo;

public interface UserRepository extends JpaRepository<UserInfo,Integer> {

	Optional<UserInfo> findByUsername(String username);

}
