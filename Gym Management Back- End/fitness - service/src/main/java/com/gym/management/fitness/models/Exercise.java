package com.gym.management.fitness.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "exercises")
public class Exercise {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false)
	private String name;

	private String category;
	private String muscleGroup;
	private LocalDateTime createdAt = LocalDateTime.now();
	
	@OneToMany(mappedBy = "exercise", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<WorkoutExercise> workoutExercises = new ArrayList<>();

	@Override
	public String toString() {
		return "Exercise [id=" + id + ", name=" + name + ", category=" + category + ", muscleGroup=" + muscleGroup
				+ ", createdAt=" + createdAt + "]";
	}

	
	
	
}