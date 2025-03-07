package com.gym.management.fitness.service;

import java.util.List;
import com.gym.management.fitness.models.Exercise;
import com.gym.management.fitness.models.Workout;
import com.gym.management.fitness.exception.ResourceNotFoundException;

/**
 * Service interface for managing fitness-related operations. Defines methods
 * for creating, retrieving, updating, and deleting workouts and exercises.
 */
public interface FitnessService {

	/**
	 * Creates a workout for a member.
	 */
	Workout createWorkout(int memberId, String workoutName) throws ResourceNotFoundException;

	/**
	 * Adds an exercise to a workout.
	 */
	Workout addExerciseToWorkout(int workoutId, int exerciseId, Integer sets, Integer reps, Double weight)
			throws ResourceNotFoundException;

	/**
	 * Adds an exercise to a workout.
	 */
	List<Workout> getMemberWorkouts(int memberId) throws ResourceNotFoundException;

	/**
	 * Deletes workouts for a member.
	 */
	void deleteMemberWorkouts(int memberId) throws ResourceNotFoundException;

	/**
	 * Creates an exercise.
	 */
	Exercise createExercise(Exercise exercise);

	/**
	 * Retrieves all exercises.
	 */
	List<Exercise> getAllExercises();

	/**
	 * Retrieves all workouts.
	 */
	List<Workout> getAllWorkouts();

	/**
	 * Removes an exercise from a workout.
	 */
	Workout removeExerciseFromWorkout(int workoutId, int exerciseId) throws ResourceNotFoundException;
}