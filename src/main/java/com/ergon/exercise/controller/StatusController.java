package com.ergon.exercise.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ergon.exercise.model.Comment;
import com.ergon.exercise.model.Status;
import com.ergon.exercise.repository.StatusRepo;

@RestController
public class StatusController {

	@Autowired
	StatusRepo statusRepo;
	
	@GetMapping("/states")
	public List<Status> getAllStates(){
		List<Status> states = statusRepo.findAll();
		if(states.isEmpty() || states.size() == 0) {
			throw new RuntimeException("No comments found");
		}
		return states;
	}
}
