package com.ergon.exercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ergon.exercise.model.Task;

@Repository
public interface TaskRepo extends JpaRepository<Task, Long> {

}
