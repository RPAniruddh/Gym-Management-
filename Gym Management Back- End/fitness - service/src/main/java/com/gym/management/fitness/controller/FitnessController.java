package com.gym.management.fitness.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gym.management.fitness.models.Exercise;
import com.gym.management.fitness.models.Workout;
import com.gym.management.fitness.service.FitnessService;

import lombok.RequiredArgsConstructor;

/**
 * REST controller for managing fitness-related operations. Provides endpoints
 * for creating, retrieving, updating, and deleting workouts and exercises.
 */
@RestController
@RequestMapping("/fitness")
@RequiredArgsConstructor
public class FitnessController {
	private final FitnessService fitnessService;

	/**
	 * Creates a new workout for a member.
	 *
	 * @param memberId    the ID of the member
	 * @param workoutName the name of the workout
	 * @return the created Workout entity
	 */
	@PostMapping("/workouts")
	public ResponseEntity<Workout> createWorkout(@RequestParam int memberId, @RequestParam String workoutName) {
		return ResponseEntity.ok(fitnessService.createWorkout(memberId, workoutName));
	}

	/**
	 * Retrieves all workouts.
	 *
	 * @return a list of all Workout entities
	 */
	@GetMapping("/workouts")
	public ResponseEntity<List<Workout>> getAllWorkouts() {
		return ResponseEntity.ok(fitnessService.getAllWorkouts());
	}

	/**
	 * Adds an exercise to an existing workout.
	 *
	 * @param workoutId  the ID of the workout
	 * @param exerciseId the ID of the exercise
	 * @param sets       the number of sets
	 * @param reps       the number of repetitions
	 * @param weight     the weight used in the exercise
	 * @return the updated Workout entity
	 */
	@PostMapping("/workouts/{workoutId}/exercises")
	public ResponseEntity<Workout> addExerciseToWorkout(@PathVariable int workoutId, @RequestParam int exerciseId,
			@RequestParam Integer sets, @RequestParam Integer reps, @RequestParam Double weight) {
		return ResponseEntity.ok(fitnessService.addExerciseToWorkout(workoutId, exerciseId, sets, reps, weight));
	}

	/**
	 * Retrieves all workouts for a specific member.
	 *
	 * @param memberId the ID of the member
	 * @return a list of Workout entities for the specified member
	 */
	@GetMapping("/workouts/member/{memberId}")
	public ResponseEntity<List<Workout>> getMemberWorkouts(@PathVariable int memberId) {
		return ResponseEntity.ok(fitnessService.getMemberWorkouts(memberId));
	}

	/**
	 * Deletes all workouts for a specific member.
	 *
	 * @param memberId the ID of the member
	 * @return a ResponseEntity with no content
	 */
	@DeleteMapping("/workouts/member/{memberId}")
	public ResponseEntity<Void> deleteMemberWorkouts(@PathVariable int memberId) {
		fitnessService.deleteMemberWorkouts(memberId);
		return ResponseEntity.noContent().build();
	}

	/**
	 * Creates a new exercise.
	 *
	 * @param exercise the Exercise entity to be created
	 * @return the created Exercise entity
	 */
	@PostMapping("/exercises")
	public ResponseEntity<Exercise> createExercise(@RequestBody Exercise exercise) {
		return ResponseEntity.ok(fitnessService.createExercise(exercise));
	}

	/**
	 * Retrieves all exercises.
	 *
	 * @return a list of all Exercise entities
	 */
	@GetMapping("/exercises")
	public ResponseEntity<List<Exercise>> getAllExercises() {
		return ResponseEntity.ok(fitnessService.getAllExercises());
	}

	/**
	 * Removes an exercise from a workout.
	 *
	 * @param workoutId  the ID of the workout
	 * @param exerciseId the ID of the exercise
	 * @return the updated Workout entity
	 */
	@DeleteMapping("/workouts/{workoutId}/exercises/{exerciseId}")
	public ResponseEntity<Workout> removeExerciseFromWorkout(@PathVariable int workoutId,
			@PathVariable int exerciseId) {
		return ResponseEntity.ok(fitnessService.removeExerciseFromWorkout(workoutId, exerciseId));
	}
}