package com.gym.management.fitness.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.management.fitness.models.Workout;
import com.gym.management.fitness.models.WorkoutExercise;

public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExercise, Integer> {
	void deleteByExerciseId(int exerciseId);
	List<WorkoutExercise>  getByWorkoutId(int workoutId);
}
