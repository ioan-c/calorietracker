package com.mj.calorietracker.repository;

import com.mj.calorietracker.repository.dao.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID>{
}
