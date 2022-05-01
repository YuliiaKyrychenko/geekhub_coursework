package com.geekhub.services;

import com.geekhub.config.AppConfig;
import com.geekhub.sources.SalarySource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;

import static org.junit.jupiter.api.Assertions.*;

class SalaryServiceTest {

    private SalaryService salaryService;

    @BeforeEach
    void setUp() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        salaryService = new SalaryService(
                applicationContext.getBean(SalarySource.class),
                applicationContext.getBean(PersonService.class),
                applicationContext.getBean(AttendanceService.class),
                applicationContext.getBean(DanceGroupService.class));
    }

    @Test
    void successfully_get_attendance_by_id() {
        assertDoesNotThrow(
                () -> salaryService.getSalaryById(11)
        );
    }

    @Test
    void failed_to_get_an_attendance_by_wrong_id() {
        assertThrows(EmptyResultDataAccessException.class,
                () -> salaryService.getSalaryById(-11)
        );
    }

}