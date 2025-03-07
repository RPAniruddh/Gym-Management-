package com.gym.management.fitness.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.management.fitness.models.Exercise;

public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {

}
