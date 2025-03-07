package com.gym.management.bookingservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.gym.management.bookingservice.model.Class;
import com.gym.management.bookingservice.repository.ClassRepository;
import com.gym.management.bookingservice.service.ClassServiceimpl;

class ClassServiceImplTest {

    @Mock
    private ClassRepository classRepository;

    @InjectMocks
    private ClassServiceimpl classService;

    private Class classEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        classEntity = new Class();
        classEntity.setId(1);
        classEntity.setName("Yoga");
        classEntity.setScheduleTime(LocalDateTime.now());
        classEntity.setCapacity(20);
    }

    @Test
    void testGetAllClasses() {
        when(classRepository.findAll()).thenReturn(Arrays.asList(classEntity));

        List<Class> classes = classService.getAllClasses();

        assertNotNull(classes);
        assertEquals(1, classes.size());
        verify(classRepository, times(1)).findAll();
    }

    @Test
    void testGetClass_Success() {
        when(classRepository.findById(anyInt())).thenReturn(Optional.of(classEntity));

        Class foundClass = classService.getClass(1);

        assertNotNull(foundClass);
        assertEquals("Yoga", foundClass.getName());
        verify(classRepository, times(1)).findById(anyInt());
    }

    @Test
    void testGetClass_NotFound() {
        when(classRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> classService.getClass(1));
    }

    @Test
    void testCreateClass() {
        when(classRepository.save(any(Class.class))).thenReturn(classEntity);

        Class createdClass = classService.createClass(classEntity);

        assertNotNull(createdClass);
        assertEquals("Yoga", createdClass.getName());
        verify(classRepository, times(1)).save(any(Class.class));
    }

    @Test
    void testUpdateClass_Success() {
        Class updatedClass = new Class();
        updatedClass.setName("Pilates");
        updatedClass.setScheduleTime(LocalDateTime.now().plusDays(1));
        updatedClass.setCapacity(15);

        when(classRepository.findById(anyInt())).thenReturn(Optional.of(classEntity));
        when(classRepository.save(any(Class.class))).thenReturn(updatedClass);

        Class result = classService.updateClass(1, updatedClass);

        assertNotNull(result);
        assertEquals("Pilates", result.getName());
        assertEquals(15, result.getCapacity());
        verify(classRepository, times(1)).findById(anyInt());
        verify(classRepository, times(1)).save(any(Class.class));
    }

    @Test
    void testUpdateClass_NotFound() {
        when(classRepository.findById(anyInt())).thenReturn(Optional.empty());

        Class updatedClass = new Class();
        updatedClass.setName("Pilates");
        updatedClass.setScheduleTime(LocalDateTime.now().plusDays(1));
        updatedClass.setCapacity(15);

        assertThrows(RuntimeException.class, () -> classService.updateClass(1, updatedClass));
    }
}
