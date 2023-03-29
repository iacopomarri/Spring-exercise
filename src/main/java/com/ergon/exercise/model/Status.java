package com.ergon.exercise.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="tbl_status")
@Getter
@Setter
@ToString
public class Status {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id_status;
	
	
	public enum Value {
        BACKLOG, IN_PROGRESS, COMPLETED
    }

	@Enumerated(EnumType.STRING)
	private Value val;
	
	private String color;
	
	//Define the relationship with Task entity
	@JsonIgnore
	@OneToMany(mappedBy = "status")
    private Set<Task> tasksInStatus = new HashSet<>();
	
	
	
	
	//Getter & setter

	public Long getId_status() {
		return id_status;
	}


	public void setTasksInStatus(Set<Task> tasksInStatus) {
		this.tasksInStatus = tasksInStatus;
	}

	public void setId_status(Long id_status) {
		this.id_status = id_status;
	}

	public Value getVal() {
		return val;
	}

	public void setVal(Value val) {
		this.val = val;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	public Set<Task> getTasksInStatus() {
		return tasksInStatus;
	}
}
