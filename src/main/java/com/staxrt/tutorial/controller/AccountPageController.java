/*
 *
 *  Copyright (c) 2018-2020 Givantha Kalansuriya, This source is a part of
 *   Staxrt - sample application source code.
 *   http://staxrt.com
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.staxrt.tutorial.controller;

import com.staxrt.tutorial.exception.ResourceNotFoundException;
import com.staxrt.tutorial.entity.UserEntity;
import com.staxrt.tutorial.data.UserDto;
import com.staxrt.tutorial.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class AccountPageController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  /**
   * Get all users list.
   *
   * @return the list
   */
  @GetMapping("/admin-get-users")
  public List<UserEntity> getAllUsers() {
    return userRepository.findAll();
  }

  /**
   * Gets users by id.
   *
   * @param userId the user id
   * @return the users by id
   * @throws ResourceNotFoundException the resource not found exception
   */
  @GetMapping("/user/{id}")
  public ResponseEntity<UserEntity> getUsersById(@PathVariable(value = "id") Long userId)
      throws ResourceNotFoundException {
    UserEntity userEntity =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("UserEntity not found on :: " + userId));
    return ResponseEntity.ok().body(userEntity);
  }

  /**
   * Create userEntity userEntity.
   *
   * @param userEntity the userEntity
   * @return the userEntity
   */
  @PostMapping("/register-user")
  public UserEntity createUser(@Valid @RequestBody UserDto userDto) {
    UserEntity userEntity = new UserEntity();
    userEntity.setFirstName(userDto.getFirstName());
    userEntity.setLastName(userDto.getLastName());
    userEntity.setEmail(userDto.getEmail());
    userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
    userEntity.setCreatedBy(userDto.getCreatedBy());
    userEntity.setUpdatedBy(userDto.getUpdatedBy());
    return userRepository.save(userEntity);
  }

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
