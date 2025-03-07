package com.gym.management.fitness.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "workouts")
public class Workout {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false)
	private int memberId;
	private String memberFirstName;
	private String memberLastName;

	private String workoutName;
	private LocalDateTime workoutDate;
	private String notes;
	private LocalDateTime createdAt = LocalDateTime.now();

	@OneToMany(mappedBy = "workout", cascade = CascadeType.ALL)
	private List<WorkoutExercise> exercises = new ArrayList<>();

	@Override
	public String toString() {
		return "Workout [id=" + id + ", memberId=" + memberId + ", workoutName=" + workoutName + ", workoutDate="
				+ workoutDate + ", notes=" + notes + ", createdAt=" + createdAt + "]";
	}
}