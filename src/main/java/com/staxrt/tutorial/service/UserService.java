package com.staxrt.tutorial.service;

import com.staxrt.tutorial.data.UserDto;
import com.staxrt.tutorial.entity.UserEntity;
import com.staxrt.tutorial.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserEntity saveCustomer(final UserDto userData){
        UserEntity customerModel = populateUserData(userData);
        return customerRepository.save(customerModel);
    }

    private UserEntity populateUserData(final UserDto userData){
        UserEntity customer = new UserEntity();
        customer.setFirstName(userData.getFirstName());
        customer.setLastName(userData.getLastName());
        customer.setEmail(userData.getEmail());
        customer.setPassword(passwordEncoder.encode(userData.getPassword()));
        return customer;
    }
}
