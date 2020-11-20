package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

  @Autowired
  private MyUserDetailsService myUserDetailService;
  
  @Autowired
  private JwtRequestFilter jwtFilter;

@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	// TODO Auto-generated method stub	
	auth.userDetailsService(myUserDetailService);
}	

@Bean
public PasswordEncoder getPasswordEncoder() {
	return NoOpPasswordEncoder.getInstance();
}

@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		//super.configure(http);
	http.csrf().disable().authorizeRequests()	    
	    .antMatchers("/admin").hasRole("ADMIN")
	    .antMatchers("/user").hasAnyRole("USER","ADMIN")	  
	    .antMatchers("/authenticate").permitAll()
	    .anyRequest().authenticated()
	    .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	    
	//filter
	 http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	}

@Override
@Bean
public AuthenticationManager authenticationManagerBean() throws Exception {	
		return super.authenticationManagerBean();
	}
}
