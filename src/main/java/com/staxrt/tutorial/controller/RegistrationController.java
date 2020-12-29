package com.staxrt.tutorial.controller;

import com.staxrt.tutorial.data.UserDto;
import com.staxrt.tutorial.entity.UserEntity;
import com.staxrt.tutorial.exception.ResourceNotFoundException;
import com.staxrt.tutorial.repository.UserRepository;
import com.staxrt.tutorial.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type UserEntity controller.
 *
 * @author Michael Martin\
 */
@RestController
@RequestMapping("/api/v1")
public class RegistrationController {

  private static final String REGISTRATION_CONFIRMATION_PAGE ="registrationConfirmation";

  @Autowired
  private UserService userService;

  @GetMapping("/register")
  public String getRegistrationView(){
    return "registration";
  }

  @PostMapping("/register")
  public String customerRegistration(final UserDto user, final Model model){
    userService.saveCustomer(user);
    return REGISTRATION_CONFIRMATION_PAGE;
  }

  //TODO move below code to service class

  /**
   * Update user response entity.
   *
   * @param userId the user id
   * @param userDto the user details
   * @return the response entity
   * @throws ResourceNotFoundException the resource not found exception
   */
  @PutMapping("/update-user/{id}")
  public ResponseEntity<UserEntity> updateUser(
      @PathVariable(value = "id") Long userId, @Valid @RequestBody UserDto userDto)
      throws ResourceNotFoundException {

    UserEntity userEntity =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("UserEntity not found on :: " + userId));

    if (userDto.getFirstName() != null) {
      userEntity.setFirstName(userDto.getFirstName());
    }
    if (userDto.getLastName() != null) {
      userEntity.setLastName(userDto.getLastName());
    }
    if (userDto.getEmail() != null) {
      userEntity.setEmail(userDto.getEmail());
    }
    if (userDto.getCreatedAt() != null) {
      userEntity.setCreatedAt(userDto.getCreatedAt());
    }
    if (userDto.getCreatedBy() != null) {
      userEntity.setCreatedBy(userDto.getCreatedBy());
    }
    if (userDto.getUpdatedAt() != null) {
      userEntity.setUpdatedAt(userDto.getUpdatedAt());
    }
    if (userDto.getUpdatedBy() != null) {
      userEntity.setUpdatedBy(userDto.getUpdatedBy());
    }
    final UserEntity updatedUserEntity = userRepository.save(userEntity);
    return ResponseEntity.ok(updatedUserEntity);
  }

  /**
   * Delete user map.
   *
   * @param userId the user id
   * @return the map
   * @throws Exception the exception
   */
  @DeleteMapping("/delete-account/{id}")
  public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId) throws Exception {
    UserEntity userEntity =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("UserEntity not found on :: " + userId));

    userRepository.delete(userEntity);
    Map<String, Boolean> response = new HashMap<>();
    response.put("deleted", Boolean.TRUE);
    return response;
  }
}
