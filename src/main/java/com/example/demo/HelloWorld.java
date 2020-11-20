package com.example.demo;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private MyUserDetailsService userDetailService;
	
	@Autowired
	private JwtTokenUtil jwtUtil;

	@GetMapping("/hello")
	public String hello() {
		return "Hellow world..!!";
	}
	@GetMapping("/user")
	public String user() {
		return "Welcome User..!!";
	}
	@GetMapping("/admin")
	public String admin() {
		return "Welcome Admin..!!";
	}
	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authRequest) throws Exception{
		try {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUser(), authRequest.getPassword()));
		}catch(BadCredentialsException ex) {
			throw new Exception("Incorrect username or password "+ ex);
		}
		final UserDetails userDetail = userDetailService.loadUserByUsername(authRequest.getUser());
		final String token = jwtUtil.generateToken(userDetail);
		return ResponseEntity.ok(new AuthenticationResponse(token));
	}
}

