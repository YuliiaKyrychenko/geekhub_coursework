package com.geekhub.services;

import com.geekhub.config.AppConfig;
import com.geekhub.sources.PersonSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;

import static org.junit.jupiter.api.Assertions.*;

class PersonServiceTest {

    private PersonService personService;

    @BeforeEach
    void setUp() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        personService = new PersonService(applicationContext.getBean(PersonSource.class));
    }

    @Test
    void successfully_get_attendance_by_id() {
        assertDoesNotThrow(
                () -> personService.getPersonById(12)
        );
    }

    @Test
    void failed_to_get_an_attendance_by_wrong_id() {
        assertThrows(EmptyResultDataAccessException.class,
                () -> personService.getPersonById(-12)
        );
    }

}