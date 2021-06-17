package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;



import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.controllers.AuthController;
import com.example.demo.request.SignupRequest;

@SpringBootTest
class BasmaStroreApplicationTests {
	
	@Autowired
	private AuthController authController;
	
	@Test
    public void testHomeController() {
		SignupRequest request = new SignupRequest("lamoia","emaiy@gmail.com", "passw2");
        String result = authController.registerUser(request);
        ///assertEquals is static method which from the org.junit.Assert package, and only one of the assertion methods used in JUnit:
        assertEquals(result, "User registered successfully!");
    }
	
//	  @Autowired
//	  private AuthController authController;
//
//	  @Test
//	  void contextLoads() {
//	    User user = new User("lamayaa","lamyaa");
//	    
////	    User savedUser = registerUseCase.registerUser(user);
////	    assertThat(savedUser.getRegistrationDate()).isNotNull();
//	  }

}


