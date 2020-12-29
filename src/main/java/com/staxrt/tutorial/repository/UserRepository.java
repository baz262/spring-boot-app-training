package com.staxrt.tutorial.repository;

import com.staxrt.tutorial.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface UserEntity repository.
 *
 * @author Michael Martin\
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
}
