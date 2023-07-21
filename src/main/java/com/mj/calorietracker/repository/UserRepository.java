package com.mj.calorietracker.repository;

import com.mj.calorietracker.repository.dao.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    UserEntity findUserEntityByUsernameEquals(String username);
}
