package com.geekhub.services;

import com.geekhub.config.AppConfig;
import com.geekhub.sources.PerfomanceSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;

import static org.junit.jupiter.api.Assertions.*;

class PerformanceServiceTest {

    private PerfomanceService perfomanceService;

    @BeforeEach
    void setUp() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        perfomanceService = new PerfomanceService(applicationContext.getBean(PerfomanceSource.class));
    }

    @Test
    void successfully_get_performance_by_id() {
        assertDoesNotThrow(
                () -> perfomanceService.getPerformanceById(11)
        );
    }

    @Test
    void failed_to_get_an_performance_by_wrong_id() {
        assertThrows(EmptyResultDataAccessException.class,
                () -> perfomanceService.getPerformanceById(-11)
        );
    }

    @Test
    void successfully_creating_performance() {
        assertDoesNotThrow(
                () -> perfomanceService.addNewPerformance(
                        "Test performance",
                        "28.04.2020",
                        "Ukraine, Cherkasy",
                        0)
        );
    }

    @Test
    void successfully_delete_performance() {
        int id = danceGroupService.addNewDanceGroup(
                "Hip-hop",
                12,
                "PRESCHOOLERS",
                "PINK",
                "Monday, Thursday",
                "15:00").getId();
        assertDoesNotThrow(
                () -> danceGroupService.deleteDanceGroup(id)
        );
    }

}