package com.webosmotic.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.webosmotic.modal.User;
import com.webosmotic.repository.UserRepository;

@Service
public class SecurityUserServiceDetail implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = userRepository.findUserByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("user not present"));
		return user;
	}

	public void createUser(UserDetails details) {
		userRepository.save((User) details);
	}

}
