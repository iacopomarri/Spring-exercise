package com.ergon.exercise.controller;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import com.ergon.exercise.model.Task;
import com.ergon.exercise.model.UserTask;
import com.ergon.exercise.model.User;
import com.ergon.exercise.repository.TaskRepo;
import com.ergon.exercise.repository.UserRepo;
import com.ergon.exercise.repository.UserTaskRepo;


@RestController 	
public class UserController {
	    

	    @Autowired
	    UserRepo userRepo;
	    
	    @Autowired
	    TaskRepo taskRepo;
	    
	    @Autowired
	    UserTaskRepo userTaskRepo;

	    
	    //Add a user to the DB
	    @PostMapping("/users")
	    public ResponseEntity<User> addUser(@RequestBody User user){
	        try {
	            User savedUser = userRepo.save(user);
	            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
	        } catch(Exception e){
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }

	    //Return all the users in the DB
	    @GetMapping("/users")
		public ResponseEntity<List<User>> getAllUsers() {
			 try {
		        	List<User> list = userRepo.findAll();
		        	if(list.isEmpty() || list.size() == 0) {
		        		return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
		        	}
		        	return new ResponseEntity<List<User>>(list, HttpStatus.OK);
		        } catch(Exception e){
		            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		        }
		}
		
	    //Retrieve and return a user by its Id
		@GetMapping("/users/{userId}")
		public ResponseEntity<User> getUser(@PathVariable Long userId) {
			Optional<User> user = userRepo.findById(userId);
			
			if (user.isPresent()) {
				return new ResponseEntity<User>(user.get(), HttpStatus.OK);
			}
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		
		//Retrieve a user by its Id and update its record with the new values.
		@PutMapping("/users/{userId}")
		public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable Long userId){
			try {
		        Optional<User> userData = userRepo.findById(userId);
	
		        if (userData.isPresent()) {
		            User existingUser = userData.get();
		            existingUser.setName(user.getName());
		            existingUser.setUsername(user.getUsername());
		            existingUser.setPassword(user.getPassword());
		            // Update other fields as needed
	
		            return new ResponseEntity<User>(userRepo.save(existingUser), HttpStatus.OK);
		        } else {
		            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		        }
		    } catch(Exception e) {
		        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		    }
		}
		
		//Delete the user given its Id. The behaviour of the linked comments and tasks has not been handled.
		@DeleteMapping("/users/{id}")
		public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id){
			Optional<User> user = userRepo.findById(id);
			try {
			if(user.isPresent()) {
				
				userRepo.delete(user.get());
				return new ResponseEntity<HttpStatus>(HttpStatus.OK);
				
				}
			return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT); 
			} catch(Exception e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		//Assign a user to a task, given their Ids
		@PutMapping("/{taskId}/users/{userId}")
		public User assignTask(@PathVariable Long userId, @PathVariable Long taskId) {
			Task task = taskRepo.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
			User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
			
			UserTask userTask = new UserTask();
			userTask.setUser(user);
			userTask.setTask(task);
			userTask.setHours(0);
			
			user.getUserTasks().add(userTask);
			task.getUserTasks().add(userTask);

			userTaskRepo.save(userTask); // Save the new UserTask object
			
			taskRepo.save(task);
			return userRepo.save(user);
		}
	
}
