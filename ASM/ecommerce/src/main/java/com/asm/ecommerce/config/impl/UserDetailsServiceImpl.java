package com.asm.ecommerce.config.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.asm.ecommerce.config.CustomUser;
import com.asm.ecommerce.model.UserDtls;
import com.asm.ecommerce.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserDtls user = userRepository.findByEmail(username);

		if (user == null) {
			throw new UsernameNotFoundException("user not found");
		}
		return new CustomUser(user);
	}
}
