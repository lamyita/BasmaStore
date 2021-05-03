package com.example.demo.controllers;

import java.util.HashSet;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.models.AccountConfirmationToken;
import com.example.demo.models.ERole;
import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.repository.*;
import com.example.demo.request.LoginRequest;
import com.example.demo.request.SignupRequest;
import com.example.demo.response.JwtResponse;
import com.example.demo.response.MessageResponse;
import com.example.demo.security.jwt.*;
import com.example.demo.security.services.UserDetailsImpl;

import com.example.demo.security.services.*;

@CrossOrigin(origins = "*", maxAge = 3600)

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	
	
	  @Autowired
	  AccountConfirmationTokennRepository accountConfirmationTokennRepository;

	  

	    @Autowired
	    EmailService emailService;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, 
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail(), 
												 roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getUsername(), 
							 signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;

				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
			
		
		}

		user.setRoles(roles);
		userRepository.save(user);
		System.out.println(user.getId());
		AccountConfirmationToken confirmationToken = new AccountConfirmationToken(user);
		accountConfirmationTokennRepository.save(confirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setText("To confirm your account, please click here : "
        +"http://localhost:8086/api/auth/confirm-account?token="+confirmationToken.getConfirmationToken());

        emailService.sendEmail(mailMessage);
        

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
	

	
	

    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    
	public ResponseEntity<?> confirmUserAccount(@Valid @RequestBody SignupRequest signUpRequest,@RequestParam("token")String accountConfirmationToken) {
		
		AccountConfirmationToken token = accountConfirmationTokennRepository.findByConfirmationToken(accountConfirmationToken);
	
		if(token != null)
        {
			User user = userRepository.findByEmailIgnoreCase(token.getUserEntity().getEmail()); 
			user.setEnabled(true);
			System.out.println("---------------------------------"+user+"--------------------------------");

			userRepository.save(user);

        }else {
        	System.out.println("message,The link is invalid or broken!");
        }
		
		return ResponseEntity.ok(new MessageResponse("your account confirme succ"));
	
	
	}
	
	
	
}