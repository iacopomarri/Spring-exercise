package com.ergon.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import com.ergon.exercise.model.Status;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ergon.exercise.repository.StatusRepo;

@SpringBootApplication
public class ExerciseApplication implements CommandLineRunner {	

	@Autowired
	private StatusRepo statusRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(ExerciseApplication.class, args);
	}

	 @Override
	   public void run(String... args) throws Exception {
	      //CREATE AND SAVE A STATE OBJECT REPRESENTING BACKLOG
	      Status status = new Status();
	      status.setId_status(Long.valueOf(1));
	      status.setVal(Status.Value.BACKLOG);
	      status.setColor("red");
	      statusRepo.save(status);

	      //STATE IN PROGRESS
	      status = new Status();
	      status.setId_status(Long.valueOf(2));
	      status.setVal(Status.Value.IN_PROGRESS);
	      status.setColor("yellow");
	      statusRepo.save(status);
	      
	      //STATE COMPLETE
	      status = new Status();
	      status.setId_status(Long.valueOf(3));
	      status.setVal(Status.Value.COMPLETED);
	      status.setColor("green");
	      statusRepo.save(status);
	   }
	}