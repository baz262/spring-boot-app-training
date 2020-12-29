package com.staxrt.tutorial.security;

import com.staxrt.tutorial.entity.UserEntity;
import com.staxrt.tutorial.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("customUserService")
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final UserEntity customer = userRepository.findByEmail(username);
        if(customer ==null){
            throw new UsernameNotFoundException(username);
        }
        return User.withUsername(customer.getEmail()).password(customer.getPassword()).authorities("USER").build();
    }
}
