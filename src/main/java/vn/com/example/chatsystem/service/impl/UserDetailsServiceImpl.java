package vn.com.example.chatsystem.service.impl;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import vn.com.example.chatsystem.model.credential.User;
import vn.com.example.chatsystem.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
        	throw new UsernameNotFoundException("User not found");
        } else {
        	Set<SimpleGrantedAuthority> grantedAuthorities = user.getRoles().stream()
        	  .map(role -> new SimpleGrantedAuthority(role.getName()))
        	  .collect(Collectors.toSet());
	
	        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
        }
    }
}
