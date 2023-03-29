package com.ergon.exercise.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ergon.exercise.model.UserTask;

public interface UserTaskRepo extends JpaRepository<UserTask, Long> {

	 Optional<UserTask> findByUserIdAndTaskId(Long id_user, Long id_task);
	 List<Optional<UserTask>> findByTaskId(Long id_task);
	}
