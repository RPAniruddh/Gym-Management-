package com.gym.management.bookingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gym.management.bookingservice.model.Class;

public interface ClassRepository extends JpaRepository<Class, Integer> {

}
