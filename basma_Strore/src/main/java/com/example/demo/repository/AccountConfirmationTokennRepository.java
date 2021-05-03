package com.example.demo.repository;

import org.springframework.stereotype.Repository;

import com.example.demo.models.AccountConfirmationToken;

import org.springframework.data.repository.CrudRepository;

@Repository("accountConfirmationTokennRepository")

public interface AccountConfirmationTokennRepository extends CrudRepository<AccountConfirmationToken, String> {
	
	AccountConfirmationToken findByConfirmationToken(String accountConfirmationToken);


}
