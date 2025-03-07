package com.gym.management.fitness.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.management.fitness.models.Workout;

public interface WorkoutRepository extends JpaRepository<Workout, Integer> {
	List<Workout> findByMemberId(int memberId);
}
