package com.gym.management.bookingservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gym.management.bookingservice.model.Class;
import com.gym.management.bookingservice.repository.ClassRepository;

import lombok.RequiredArgsConstructor;

/**
 * Service implementation for managing gym classes. Provides methods for
 * creating, retrieving, updating, and deleting classes.
 */
@Service
@RequiredArgsConstructor
public class ClassServiceimpl implements ClassService {
	private final ClassRepository classRepository;

	/**
	 * Retrieves all gym classes.
	 *
	 * @return a list of all Class objects
	 */
	@Override
	public List<Class> getAllClasses() {
		return classRepository.findAll();
	}

	/**
	 * Retrieves a specific gym class by its ID.
	 *
	 * @param id the ID of the class to be retrieved
	 * @return the Class object
	 * @throws RuntimeException if the class is not found
	 */
	@Override
	public Class getClass(int id) {
		return classRepository.findById(id).orElseThrow(() -> new RuntimeException("Class not found"));
	}

	/**
	 * Creates a new gym class.
	 *
	 * @param classEntity the Class object to be created
	 * @return the created Class object
	 */
	@Override
	public Class createClass(Class classEntity) {
		return classRepository.save(classEntity);
	}

	/**
	 * Updates an existing gym class.
	 *
	 * @param id           the ID of the class to be updated
	 * @param updatedClass the updated Class object
	 * @return the updated Class object
	 * @throws RuntimeException if the class is not found
	 */
	@Override
	public Class updateClass(int id, Class updatedClass) {
		Class existingClass = getClass(id);

		existingClass.setName(updatedClass.getName());
		existingClass.setScheduleTime(updatedClass.getScheduleTime());
		existingClass.setCapacity(updatedClass.getCapacity());

		return classRepository.save(existingClass);
	}
}