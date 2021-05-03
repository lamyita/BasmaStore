package com.example.demo.services;

import java.util.List;

import com.example.demo.shered.dto.AddressDto;

public interface AddressService {

	List<AddressDto> getAllAddresses(String email);
    AddressDto createAddress(AddressDto address, String email);
	
   AddressDto getAddress(String addressId);
	
	void deleteAddress(String addressId);
}
