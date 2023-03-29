package com.ergon.exercise.controller;

import java.util.*;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import com.ergon.exercise.model.Status;
import com.ergon.exercise.model.Task;
import com.ergon.exercise.model.User;
import com.ergon.exercise.model.UserTask;
import com.ergon.exercise.repository.StatusRepo;
import com.ergon.exercise.repository.TaskRepo;
import com.ergon.exercise.repository.UserRepo;
import com.ergon.exercise.repository.UserTaskRepo;

@RestController
public class TaskController {
	
	@Autowired
	TaskRepo taskRepo;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	StatusRepo statusRepo;
	
	@Autowired
    UserTaskRepo userTaskRepo;
	
	//Add a new task to the DB, linking it to a user (user is supposed to be the one who creates the task)
	@PostMapping("/{userId}/tasks")
	public Task addTask(@PathVariable Long userId, @RequestBody Task task) {
		
		User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
		Status status = statusRepo.findById(Long.valueOf(1)).orElseThrow(() -> new RuntimeException("Status backlog element not found"));
		
		if (status != null) {
		    task.setStatus(status);
		}
	
		UserTask userTask = new UserTask();
		userTask.setUser(user);
		userTask.setTask(task);
		userTask.setHours(0);
		
		user.getUserTasks().add(userTask);
		task.getUserTasks().add(userTask);
		
		Task savedTask = taskRepo.save(task);
		userRepo.save(user);
		userTaskRepo.save(userTask); // Save the new UserTask object

		return savedTask;
	   }
	
	
	//Update the status of a task.  Three transitions are possible:
	//Backlog -> In progress
	//In progress -> Completed
	//Complete -> In progress
	//So two cases are defined: 1) Status is in progress, then we go to complete. 2) Status is either Backlog or Complete, then we go to In progress.
	@PutMapping("/tasks/{taskId}/status")
	public Task updateStatus(@PathVariable Long taskId) {

		Task task = taskRepo.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
		
		//String statVal
		Status.Value statVal = task.getStatus().getVal();
		Status status;
		
		switch (statVal) {
		   case IN_PROGRESS:
			  status = statusRepo.findById(Long.valueOf(3)).get();
		      break;
		   
		   default:
			  status = statusRepo.findById(Long.valueOf(2)).get();
		      break;
		}

		 task.setStatus(status);
		 return taskRepo.save(task);
	}

	
	//Retrieve and return a task given its Id.
	@GetMapping("/tasks/{taskId}")
	public Task getTask(@PathVariable Long taskId) {
		Task task = taskRepo.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
	
		return(task);
	}

	//Return all tasks.
	@GetMapping("/tasks")
	public List<Task> getAllTasks(){
		List<Task> tasks = taskRepo.findAll();
		
		if(tasks.isEmpty() || tasks.size() == 0) {
			throw new RuntimeException("No tasks found");
		}
		return tasks;
	}

	//Given a user and a status, retrieve and return all the tasks the user is enrolled in, which are in the specified status.
	@GetMapping("/users/{userId}/status/{statusId}")
    public List<Task> getTasksByUserAndStatus(@PathVariable Long userId, @PathVariable Long statusId) {
		
        Optional<User> optionalUser = userRepo.findById(userId);
        Optional<Status> optionalStatus = statusRepo.findById(statusId);
        
        if (optionalUser.isPresent() && optionalStatus.isPresent()) {
            User user = optionalUser.get();
            Status status = optionalStatus.get();
            
            Set<UserTask> userTasks = user.getUserTasks();
            List<Task> result = new ArrayList<>();
            
            for (UserTask userTask : userTasks) {
            	Task task = userTask.getTask();
                if (task.getStatus().getId_status().equals(status.getId_status())) {
                    result.add(task);
                }
            }
            return result;
        } else {
            return Collections.emptyList();
        }
    }
	
	//Given a user and a task assign to him, add the amount of hours that user worked on the task.
	@PutMapping("/tasks/{userId}/{taskId}/{hours}")
	public UserTask addHourToUserTask(@PathVariable Long userId, @PathVariable Long taskId, @PathVariable Float hours) {
		
		Optional<UserTask> optUserTask = userTaskRepo.findByUserIdAndTaskId(userId, taskId);
		
		if(optUserTask.isPresent()) {
			UserTask userTask = optUserTask.get();
			userTask.setHours(hours);
			return userTaskRepo.save(userTask);
		}
		else throw new RuntimeException("UserTask not found with this userId and taskId");
	}

	//Given a task, compute and return the total amount of work hours on that task by any user.
	@GetMapping("/{taskId}/hours")
	public float getHoursByTask(@PathVariable Long taskId) {
		
		Task task = taskRepo.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
		List<Optional<UserTask>> userTasks = userTaskRepo.findByTaskId(taskId);
		
		if(userTasks.isEmpty()) {
			throw new RuntimeException("Task not found in UserTask mapping");
		}
		
		float total = 0;
		for(Optional<UserTask> optUserTask : userTasks) {
			UserTask userTask = optUserTask.get();
			total += userTask.getHours();
		}
		
		return total;
	}
}
