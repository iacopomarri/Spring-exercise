/*package com.ergon.exercise.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Set;
import java.util.HashSet;

@Entity
@Table(name = "task_assignment")
@Getter
@Setter
@ToString
public class TaskAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_task")
    private Task task;
    
	@Column(name = "hours_spent")
    private int hoursSpent;
	
    
    
    


	// Getters and setters
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public int getHoursSpent() {
		return hoursSpent;
	}

	public void setHoursSpent(int hoursSpent) {
		this.hoursSpent = hoursSpent;
	}



 
}*/