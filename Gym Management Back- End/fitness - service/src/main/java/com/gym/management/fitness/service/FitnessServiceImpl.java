package com.gym.management.fitness.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.gym.management.fitness.dto.MemberDTO;
import com.gym.management.fitness.exception.ResourceNotFoundException;
import com.gym.management.fitness.models.Exercise;
import com.gym.management.fitness.models.Workout;
import com.gym.management.fitness.models.WorkoutExercise;
import com.gym.management.fitness.repository.ExerciseRepository;
import com.gym.management.fitness.repository.WorkoutRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

/**
 * Service implementation for managing fitness-related operations. This class
 * provides methods to create workouts, add exercises to workouts, retrieve
 * member workouts, delete member workouts, and manage exercises.
 */
@Service
@RequiredArgsConstructor
public class FitnessServiceImpl implements FitnessService {
	private final WorkoutRepository workoutRepository;
	private final ExerciseRepository exerciseRepository;
	private static final String MEMBER_SERVICE_URL = "http://localhost:8082/members/get/";
	private final RestTemplate restTemplate;

	/**
	 * Constructor for FitnessServiceImpl.
	 * 
	 * @param restTemplate       RestTemplate for making HTTP requests.
	 * @param workoutRepository  Repository for managing Workout entities.
	 * @param exerciseRepository Repository for managing Exercise entities.
	 */
	@Autowired
	public FitnessServiceImpl(RestTemplate restTemplate, WorkoutRepository workoutRepository,
			ExerciseRepository exerciseRepository) {
		this.restTemplate = restTemplate;
		this.workoutRepository = workoutRepository;
		this.exerciseRepository = exerciseRepository;
	}

	/**
	 * Creates a new workout for a member.
	 * 
	 * @param memberId    ID of the member.
	 * @param workoutName Name of the workout.
	 * @return The created Workout entity.
	 * @throws ResourceNotFoundException if the member is not found.
	 */
	@Transactional
	@Override
	public Workout createWorkout(int memberId, String workoutName) {
		String memberServiceUrl = MEMBER_SERVICE_URL + memberId;
		MemberDTO memberDTO = restTemplate.getForObject(memberServiceUrl, MemberDTO.class);

		if (memberDTO == null) {
			throw new ResourceNotFoundException("Member not found for ID " + memberId);
		}

		Workout workout = new Workout();
		workout.setMemberId(memberDTO.getId());
		workout.setMemberFirstName(memberDTO.getFirstName());
		workout.setMemberLastName(memberDTO.getLastName());
		workout.setWorkoutName(workoutName);
		workout.setWorkoutDate(LocalDateTime.now());
		return workoutRepository.save(workout);
	}

	/**
	 * Adds an exercise to an existing workout.
	 * 
	 * @param workoutId  ID of the workout.
	 * @param exerciseId ID of the exercise.
	 * @param sets       Number of sets.
	 * @param reps       Number of repetitions.
	 * @param weight     Weight used.
	 * @return The updated Workout entity.
	 * @throws ResourceNotFoundException if the workout or exercise is not found.
	 */
	@Transactional
	@Override
	public Workout addExerciseToWorkout(int workoutId, int exerciseId, Integer sets, Integer reps, Double weight) {
		Workout workout = workoutRepository.findById(workoutId)
				.orElseThrow(() -> new ResourceNotFoundException("Workout not found with id: " + workoutId));

		Exercise exercise = exerciseRepository.findById(exerciseId)
				.orElseThrow(() -> new ResourceNotFoundException("Exercise not found with id: " + exerciseId));

		WorkoutExercise workoutExercise = new WorkoutExercise();
		workoutExercise.setWorkout(workout);
		workoutExercise.setExercise(exercise);
		workoutExercise.setSets(sets);
		workoutExercise.setReps(reps);
		workoutExercise.setWeight(weight);

		workout.getExercises().add(workoutExercise);
		return workoutRepository.save(workout);
	}

	/**
	 * Retrieves all workouts for a specific member.
	 * 
	 * @param memberId ID of the member.
	 * @return List of Workout entities.
	 * @throws ResourceNotFoundException if the member is not found.
	 */
	@Override
	public List<Workout> getMemberWorkouts(int memberId) {
		String memberServiceUrl = MEMBER_SERVICE_URL + memberId;
		MemberDTO memberDTO = restTemplate.getForObject(memberServiceUrl, MemberDTO.class);

		if (memberDTO == null) {
			throw new ResourceNotFoundException("Member not found for ID " + memberId);
		}

		List<Workout> workouts = workoutRepository.findByMemberId(memberId);

		for (Workout workout : workouts) {
			workout.setMemberFirstName(memberDTO.getFirstName());
			workout.setMemberLastName(memberDTO.getLastName());
		}

		return workouts;
	}

	/**
	 * Deletes all workouts for a specific member.
	 * 
	 * @param memberId ID of the member.
	 */
	@Override
	public void deleteMemberWorkouts(int memberId) {
		List<Workout> memberWorkouts = workoutRepository.findByMemberId(memberId);
		workoutRepository.deleteAll(memberWorkouts);

	}

	/**
	 * Creates a new exercise.
	 * 
	 * @param exercise Exercise entity to be created.
	 * @return The created Exercise entity.
	 */
	@Override
	public Exercise createExercise(Exercise exercise) {
		return exerciseRepository.save(exercise);
	}

	/**
	 * Retrieves all exercises.
	 * 
	 * @return List of Exercise entities.
	 */
	@Override
	public List<Exercise> getAllExercises() {
		return exerciseRepository.findAll();
	}

	/**
	 * Retrieves all workouts.
	 * 
	 * @return List of Workout entities.
	 */
	@Override
	public List<Workout> getAllWorkouts() {
		return workoutRepository.findAll();
	}

	/**
	 * Removes an exercise from a workout.
	 * 
	 * @param workoutId  ID of the workout.
	 * @param exerciseId ID of the exercise to be removed.
	 * @return The updated Workout entity.
	 * @throws ResourceNotFoundException if the workout is not found.
	 */
	@Transactional
	@Override
	public Workout removeExerciseFromWorkout(int workoutId, int exerciseId) {
		Workout workout = workoutRepository.findById(workoutId)
				.orElseThrow(() -> new ResourceNotFoundException("Workout not found with id: " + workoutId));

		workout.getExercises().removeIf(we -> we.getExercise().getId() == exerciseId);

		return workoutRepository.save(workout);
	}
}