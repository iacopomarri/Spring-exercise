package com.ergon.exercise.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import com.ergon.exercise.model.Comment;
import com.ergon.exercise.model.Task;
import com.ergon.exercise.model.User;
import com.ergon.exercise.model.UserTask;
import com.ergon.exercise.repository.CommentRepo;
import com.ergon.exercise.repository.TaskRepo;
import com.ergon.exercise.repository.UserRepo;


@RestController
public class CommentController {
	
	@Autowired
    CommentRepo commentRepo;
	
	@Autowired
    UserRepo userRepo;
	
	@Autowired
    TaskRepo taskRepo;
	
	
	//Add a new comment, linking it to a task and to a user (The user who made it, the post the comment has been posted under)
	@PostMapping("/comments/{userId}/{taskId}")
	   public Comment postComment(@RequestBody Comment comment, @PathVariable Long userId, @PathVariable Long taskId){
	       
			   User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
			   Task task = taskRepo.findById(taskId).orElseThrow(() -> new RuntimeException("Task not found"));
			   
			   comment.setUser(user); // set the user field of the Comment object
			   comment.setTask(task); // set the task field of the Comment object
			    
			   Comment savedComment = commentRepo.save(comment);
			   user.addComment(comment);
			   task.addComment(comment);
	           
			   
			   userRepo.save(user);
	           taskRepo.save(task);
	           return savedComment;
	       
	   }
	
	//Return all the comments in the DB
	@GetMapping("/comments")
	public List<Comment> getAllComments(){
		List<Comment> comments = commentRepo.findAll();
		if(comments.isEmpty() || comments.size() == 0) {
			throw new RuntimeException("No comments found");
		}
		return comments;
	}
	
	//Given  task, retrieve all the related comments, and return them ordered by the CreationDate
	@GetMapping("/comments/{taskId}")
	public List<Comment> getCommentsByTask(@PathVariable Long taskId){
        Optional<Task> optionalTask = taskRepo.findById(taskId);

        if (optionalTask.isPresent()) {
        	Task task = optionalTask.get();
        	Set<Comment> comments = task.getRelatedComments();
        	
        	if(comments.isEmpty() || comments.size() == 0) {
        		 throw new RuntimeException("No comments were found for the specified task");
        	}
        	
        	// Create a new TreeSet with a comparator that sorts comments by createdAt field
        	Set<Comment> sortedComments = new TreeSet<>(Comparator.comparing(Comment::getCreatedAt));

        	// Add all comments to the sorted set
        	sortedComments.addAll(comments);
        	
        	/*List<Comment> sortedList = new ArrayList<>(comments);
        	Collections.sort(sortedList);*/
        	return new ArrayList<>(sortedComments);
        }
		
		  else throw new RuntimeException("Task not found");
		
	}
}
