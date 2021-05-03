package com.example.demo.shered;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Component;
@Component
public class Utils {
	
 
	private final Random RANDOM= new SecureRandom();
	
    private static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    
    public String generateStringId(int length) {
    	StringBuilder returnValue = new StringBuilder(length);
    	
    	for(int i = 0; i < length; i++) {
    		returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
    		
    	}
    	
    	return new String(returnValue);
    	
    }
}
