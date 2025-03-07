package com.gym.management.bookingservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym.management.bookingservice.model.Class;
import com.gym.management.bookingservice.service.ClassService;

import lombok.RequiredArgsConstructor;

/**
 * REST controller for managing classes-related operations. Provides endpoints
 * for creating, retrieving, updating, and deleting workouts and exercises.
 */
@RestController
@RequestMapping("/classes")
@RequiredArgsConstructor
public class ClassController {
	private final ClassService classService;

	/**
	 * Retrieves all gym classes.
	 *
	 * @return ResponseEntity containing a list of all Class objects
	 */
	@GetMapping
	public ResponseEntity<List<Class>> getAllClasses() {
		return ResponseEntity.ok(classService.getAllClasses());
	}

	/**
	 * Retrieves a specific gym class by its ID.
	 *
	 * @param id the ID of the class to be retrieved
	 * @return ResponseEntity containing the Class object
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Class> getClass(@PathVariable int id) {
		return ResponseEntity.ok(classService.getClass(id));
	}

	/**
	 * Creates a new gym class.
	 *
	 * @param classEntity the Class object to be created
	 * @return ResponseEntity containing the created Class object
	 */
	@PostMapping
	public ResponseEntity<Class> createClass(@RequestBody Class classEntity) {
		return ResponseEntity.ok(classService.createClass(classEntity));
	}

	/**
	 * Updates an existing gym class.
	 *
	 * @param id          the ID of the class to be updated
	 * @param classEntity the updated Class object
	 * @return ResponseEntity containing the updated Class object
	 */
	@PutMapping("/{id}")
	public ResponseEntity<Class> updateClass(@PathVariable int id, @RequestBody Class classEntity) {
		return ResponseEntity.ok(classService.updateClass(id, classEntity));
	}
}