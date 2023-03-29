package com.ergon.exercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ergon.exercise.model.Status;

@Repository
public interface StatusRepo extends JpaRepository<Status, Long> {

}
