package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entites.AddressEntity;
import com.example.demo.entites.UserEntity;


 @Repository
public interface AddressRepository extends CrudRepository<AddressEntity, Long> {
	
	List<AddressEntity> findByUser(UserEntity currentUser);

	AddressEntity findByAddressId(String addressId);
}
  