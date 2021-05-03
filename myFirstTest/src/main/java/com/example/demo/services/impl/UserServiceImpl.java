package com.example.demo.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entites.UserEntity;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.UserService;
import com.example.demo.shered.Utils;
import com.example.demo.shered.dto.AddressDto;
//import com.example.demo.shered.Utils;
import com.example.demo.shered.dto.UserDto;

@Service
public class UserServiceImpl implements UserService {

	@Autowired //// injection
	UserRepository userRepository;/// we can't use object userRepository because not instiasion

	@Autowired
	BCryptPasswordEncoder bCrypePasswordEncoder;
//	
	@Autowired
//
	Utils util;
//	

	@Override
	public UserDto creatUser(UserDto user) {

		UserEntity checkUser = userRepository.findByEmail(user.getEmail());

		if (checkUser != null)
			throw new RuntimeException("User Alrady Exists !");

		//// persist les addres
		for (int i = 0; i < user.getAddresses().size(); i++) {

			AddressDto address = user.getAddresses().get(i);
			address.setUser(user);
			address.setAddressId(util.generateStringId(30));
			user.getAddresses().set(i, address);
		}

		// recupere contactID
		user.getContact().setContactId(util.generateStringId(30));

		user.getContact().setUser(user);

		ModelMapper modelMapper = new ModelMapper();

		UserEntity userEntity = modelMapper.map(user, UserEntity.class);

		userEntity.setEncryptedPassword(bCrypePasswordEncoder.encode(user.getPassword()));

		userEntity.setUserId(util.generateStringId(32));

		UserEntity newUser = userRepository.save(userEntity);

		UserDto userDto = modelMapper.map(newUser, UserDto.class);

		return userDto;
	}



////methode pour recupere les utulisateur authentifié mn DB
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		UserEntity userEntity = userRepository.findByEmail(email);
		if (userEntity == null)
			throw new UsernameNotFoundException(email);

		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
	}

	@Override
	public UserDto getUser(String email) {
		UserEntity userEntity = userRepository.findByEmail(email);
		if (userEntity == null)
			throw new UsernameNotFoundException(email);

		UserDto userDto = new UserDto();

		BeanUtils.copyProperties(userEntity, userDto);
		return userDto;
	}

	@Override
	public UserDto getUserByUserId(String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId);
		if (userEntity == null)
			throw new UsernameNotFoundException(userId);
		UserDto userDto = new UserDto();

		BeanUtils.copyProperties(userEntity, userDto);

		return userDto;
	}

	@Override
	public UserDto updateUser(String userId, UserDto userDto) {
		System.out.println(userId);
		UserEntity userEntity = userRepository.findByUserId(userId);

//		System.out.println(userEntity.ge());
		if (userEntity == null)
			throw new UsernameNotFoundException(userId);
		userEntity.setFirstName(userDto.getFirstName());
		userEntity.setLastName(userDto.getLastName());
		UserEntity userUpdate = userRepository.save(userEntity);
		UserDto user = new UserDto();
		BeanUtils.copyProperties(userUpdate, user);

		return user;
	}

	@Override
	public void deleteUser(String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId);
		if (userEntity == null)
			throw new UsernameNotFoundException(userId);

		userRepository.delete(userEntity);

	}

	@Override
	public List<UserDto> getUsers(int page, int limit, String search, int status) {

		if (page > 0)
			page = page - 1;

		List<UserDto> usersDto = new ArrayList<>();

		Pageable pageableRequest = PageRequest.of(page, limit);

		Page<UserEntity> userPage;

		if (search.isEmpty()) {
			userPage = userRepository.findAllUsers(pageableRequest);
		} else {

			userPage = userRepository.findAllUserByCriteria(pageableRequest, search, status);
		}

		List<UserEntity> users = userPage.getContent();

		for (UserEntity userEntity : users) {

			ModelMapper modelMapper = new ModelMapper();
			UserDto user = modelMapper.map(userEntity, UserDto.class);

			usersDto.add(user);
		}

		return usersDto;
	}

//	@Override
//	public List<UserDto> getUsers(int page, int limit, String search) {	
//		
//		if(page > 0) page = page - 1;
//
//   List<UserDto> usersResponse = new ArrayList();
//   
//	Pageable pageableRequest = PageRequest.of(page, limit);
//	/*recupere tout in DB without pagination so we will add to user repository
//    *  another hirit place CrudRepository add PagingAndSortingRepository */ 
//	Page<UserEntity> userPage;
//	
//	
//	if(search.isEmpty()) {
//		userPage = userRepository.findAllUser(pageableRequest);
//	}else {
//		
//		
//		userPage = userRepository.findAllUserByCriteria(pageableRequest, search);
//	}
//	
//	
//	List<UserEntity> users = userPage.getContent();
//
//	
//	for(UserEntity userEntity: users) {
//		ModelMapper modelMapper = new ModelMapper();
//		UserDto user = modelMapper.map(userEntity, UserDto.class);
////		UserDto user =new UserDto();
////		BeanUtils.copyProperties(userEntity, user);
//		usersResponse.add(user);
//	}
//	
//	return usersResponse;
//	}

}
