package com.gym.management.fitness;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import com.gym.management.fitness.dto.MemberDTO;
import com.gym.management.fitness.models.Exercise;
import com.gym.management.fitness.models.Workout;
import com.gym.management.fitness.models.WorkoutExercise;
import com.gym.management.fitness.repository.ExerciseRepository;
import com.gym.management.fitness.repository.WorkoutRepository;
import com.gym.management.fitness.service.FitnessServiceImpl;

public class FitnessServiceImplTest {

    @Mock
    private WorkoutRepository workoutRepository;

    @Mock
    private ExerciseRepository exerciseRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private FitnessServiceImpl fitnessService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateWorkout() {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(1);
        memberDTO.setFirstName("John");
        memberDTO.setLastName("Doe");

        when(restTemplate.getForObject(anyString(), eq(MemberDTO.class))).thenReturn(memberDTO);
        when(workoutRepository.save(any(Workout.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Workout workout = fitnessService.createWorkout(1, "Morning Workout");

        assertNotNull(workout);
        assertEquals(1, workout.getMemberId());
        assertEquals("John", workout.getMemberFirstName());
        assertEquals("Doe", workout.getMemberLastName());
        assertEquals("Morning Workout", workout.getWorkoutName());
    }

    @Test
    void testAddExerciseToWorkout() {
        Workout workout = new Workout();
        workout.setId(1);

        Exercise exercise = new Exercise();
        exercise.setId(1);

        when(workoutRepository.findById(anyInt())).thenReturn(Optional.of(workout));
        when(exerciseRepository.findById(anyInt())).thenReturn(Optional.of(exercise));
        when(workoutRepository.save(any(Workout.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Workout updatedWorkout = fitnessService.addExerciseToWorkout(1, 1, 3, 10, 50.0);

        assertNotNull(updatedWorkout);
        assertEquals(1, updatedWorkout.getExercises().size());
        WorkoutExercise workoutExercise = updatedWorkout.getExercises().get(0);
        assertEquals(3, workoutExercise.getSets());
        assertEquals(10, workoutExercise.getReps());
        assertEquals(50.0, workoutExercise.getWeight());
    }

    @Test
    void testGetMemberWorkouts() {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(1);
        memberDTO.setFirstName("John");
        memberDTO.setLastName("Doe");

        Workout workout = new Workout();
        workout.setMemberId(1);

        when(restTemplate.getForObject(anyString(), eq(MemberDTO.class))).thenReturn(memberDTO);
        when(workoutRepository.findByMemberId(anyInt())).thenReturn(Arrays.asList(workout));

        var workouts = fitnessService.getMemberWorkouts(1);

        assertNotNull(workouts);
        assertEquals(1, workouts.size());
        assertEquals("John", workouts.get(0).getMemberFirstName());
        assertEquals("Doe", workouts.get(0).getMemberLastName());
    }

    @Test
    void testDeleteMemberWorkouts() {
        Workout workout = new Workout();
        workout.setMemberId(1);

        when(workoutRepository.findByMemberId(anyInt())).thenReturn(Arrays.asList(workout));

        fitnessService.deleteMemberWorkouts(1);

        verify(workoutRepository, times(1)).deleteAll(anyList());
    }

    @Test
    void testCreateExercise() {
        Exercise exercise = new Exercise();
        exercise.setName("Push Up");

        when(exerciseRepository.save(any(Exercise.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Exercise createdExercise = fitnessService.createExercise(exercise);

        assertNotNull(createdExercise);
        assertEquals("Push Up", createdExercise.getName());
    }

    @Test
    void testGetAllExercises() {
        Exercise exercise = new Exercise();
        exercise.setName("Push Up");

        when(exerciseRepository.findAll()).thenReturn(Arrays.asList(exercise));

        var exercises = fitnessService.getAllExercises();

        assertNotNull(exercises);
        assertEquals(1, exercises.size());
        assertEquals("Push Up", exercises.get(0).getName());
    }

    @Test
    void testGetAllWorkouts() {
        Workout workout = new Workout();
        workout.setWorkoutName("Morning Workout");

        when(workoutRepository.findAll()).thenReturn(Arrays.asList(workout));

        var workouts = fitnessService.getAllWorkouts();

        assertNotNull(workouts);
        assertEquals(1, workouts.size());
        assertEquals("Morning Workout", workouts.get(0).getWorkoutName());
    }

    @Test
    void testRemoveExerciseFromWorkout() {
        Workout workout = new Workout();
        workout.setId(1);

        WorkoutExercise workoutExercise = new WorkoutExercise();
        workoutExercise.setExercise(new Exercise());
        workoutExercise.getExercise().setId(1);
        workout.getExercises().add(workoutExercise);

        when(workoutRepository.findById(anyInt())).thenReturn(Optional.of(workout));
        when(workoutRepository.save(any(Workout.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Workout updatedWorkout = fitnessService.removeExerciseFromWorkout(1, 1);

        assertNotNull(updatedWorkout);
        assertTrue(updatedWorkout.getExercises().isEmpty());
    }
}