package com.webosmotic.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.webosmotic.modal.Attemps;
import com.webosmotic.modal.User;
import com.webosmotic.repository.AttempsRepository;
import com.webosmotic.repository.UserRepository;
@Component
public class AuthProvider implements AuthenticationProvider {
	
	 private static final int ATTEMPTS_LIMIT = 3; 

	@Autowired
	private SecurityUserServiceDetail securityUserServiceDetail;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AttempsRepository attempsRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// TODO Auto-generated method stub
		String username = authentication.getName();
		
		Optional<Attemps> userAttemps = attempsRepository.findAttempsByUsername(username);
		
		if(userAttemps.isPresent()) {
			Attemps attemps = userAttemps.get();
			attemps.setAttemps(0);
			attempsRepository.save(attemps);
		}
		return authentication;
		
		
	}
	
	
	public void processFailedAttemps(String username, User user) {
		Optional<Attemps> userAttemps = attempsRepository.findAttempsByUsername(username);
		if (userAttemps !=null){
			Attemps attemps = new Attemps();
			attemps.setUsername(username);
			attemps.setAttemps(1);
			attempsRepository.save(attemps);
		}
		else {
			Attemps attemps =userAttemps.get();
			attemps.setAttemps(attemps.getAttemps() + 1);
			attempsRepository.save(attemps);
		
			 if (attemps.getAttemps() + 1 >
			 ATTEMPTS_LIMIT) {
	            user.setAccountNonLocked(false); 
	            userRepository.save(user); 
	            throw new LockedException("Too many invalid attempts. Account is locked!!"); 
		
			 }
		}
		
	}
	
	
	

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return true;
	}

}
