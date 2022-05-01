package com.geekhub.services;

import com.geekhub.config.AppConfig;
import com.geekhub.models.Attendance;
import com.geekhub.sources.AttendanceSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;

import static org.junit.jupiter.api.Assertions.*;

@ComponentScan(basePackages = {"com.geekhub.config"})
class AttendanceServiceTest {

    private AttendanceService attendanceService;

    @BeforeEach
    void setUp() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        attendanceService = new AttendanceService(applicationContext.getBean(AttendanceSource.class),
                applicationContext.getBean(PersonService.class));
    }

    @Test
    void successfully_get_attendance_by_id() {
        assertDoesNotThrow(
                () -> attendanceService.getAttendanceById(11)
        );
    }

    @Test
    void failed_to_get_an_attendance_by_wrong_id() {
        assertThrows(EmptyResultDataAccessException.class,
                () -> attendanceService.getAttendanceById(-11)
        );
    }

    @Test
    void successfully_creating_attendance() {
        assertDoesNotThrow(
                () -> attendanceService.addNewAttendance(11, "April").getId()
        );
    }

    @Test
    void successfully_delete_attendance() {
        int id= attendanceService.addNewAttendance(11, "April").getId();
        assertDoesNotThrow(
                () -> attendanceService.deleteAttendanceById(id)
        );
    }
}