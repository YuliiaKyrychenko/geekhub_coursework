package com.geekhub.services;

import com.geekhub.config.AppConfig;
import com.geekhub.sources.DanceGroupSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;

import static org.junit.jupiter.api.Assertions.*;

class DanceGroupServiceTest {

    private DanceGroupService danceGroupService;

    @BeforeEach
    void setUp() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        danceGroupService = new DanceGroupService(applicationContext.getBean(DanceGroupSource.class),
                applicationContext.getBean(PersonService.class));
    }

    @Test
    void successfully_get_dance_group_by_id() {
        assertDoesNotThrow(
                () -> danceGroupService.getDanceGroupById(12)
        );
    }

    @Test
    void failed_to_get_an_dance_group_by_wrong_id() {
        assertThrows(EmptyResultDataAccessException.class,
                () -> danceGroupService.getDanceGroupById(-12)
        );
    }

    @Test
    void successfully_creating_dance_group() {
        assertDoesNotThrow(
                () -> danceGroupService.addNewDanceGroup(
                        "Hip-hop",
                        12,
                        "PRESCHOOLERS",
                        "PINK",
                        "Monday, Thursday",
                        "15:00")
        );
    }

    @Test
    void successfully_delete_dance_group() {
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