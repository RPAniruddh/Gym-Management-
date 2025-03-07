package com.gym.management.fitness.models;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "workout_exercises")
public class WorkoutExercise {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "workout_id")
	@JsonIgnore
	private Workout workout;

	@ManyToOne
	@JoinColumn(name = "exercise_id")
	@JsonIgnore
	private Exercise exercise;

	private Integer sets;
	private Integer reps;
	private Double weight;
	private LocalDateTime createdAt = LocalDateTime.now();
}