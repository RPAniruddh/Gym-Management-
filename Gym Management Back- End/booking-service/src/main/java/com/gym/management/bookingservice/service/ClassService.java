package com.gym.management.bookingservice.service;

import java.util.List;

import com.gym.management.bookingservice.model.Class;

/**
 * Service interface for managing classes.
 */
public interface ClassService {

	/**
	 * Retrieves all classes.
	 */
	List<Class> getAllClasses();

	/**
	 * Retrieves a class by ID.
	 */
	Class getClass(int id);

	/**
	 * Creates a new class.
	 */
	Class createClass(Class classEntity);

	/**
	 * Updates an existing class.
	 */
	Class updateClass(int id, Class updatedClass);
}