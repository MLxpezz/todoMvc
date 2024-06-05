package com.todo.app.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todo.app.model.entities.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{

    Optional<UserEntity> findUserEntityByUsername(String username);
    Optional<UserEntity> findUserEntityByEmail(String email);
}
