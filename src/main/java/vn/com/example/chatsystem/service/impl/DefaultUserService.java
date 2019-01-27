package vn.com.example.chatsystem.service.impl;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.example.chatsystem.model.credential.Role;
import vn.com.example.chatsystem.model.credential.User;
import vn.com.example.chatsystem.repository.UserRepository;
import vn.com.example.chatsystem.service.UserService;

@Service
public class DefaultUserService implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	@Transactional
	public User createUser(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.addRoles(Collections.singletonList(new Role("ROLE_USER")));
		return userRepository.save(user);
	}
}
