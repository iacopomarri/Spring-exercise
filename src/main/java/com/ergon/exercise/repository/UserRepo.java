package com.ergon.exercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ergon.exercise.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
   
}