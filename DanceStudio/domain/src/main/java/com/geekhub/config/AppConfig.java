package com.geekhub.config;

import com.geekhub.services.*;
import com.geekhub.sources.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DatabaseConfig.class)
public class AppConfig {

    @Bean
    public AttendanceSource attendanceSource() {
        return new AttendanceSource();
    }

    @Bean
    public AttendanceService attendanceService(AttendanceSource attendanceSource, PersonService personService) {
        return new AttendanceService(attendanceSource, personService);
    }

    @Bean
    public DanceGroupSource danceGroupSource() {
        return new DanceGroupSource();
    }

    @Bean
    public DanceGroupService danceGroupService(DanceGroupSource danceGroupSource, PersonService personService) {
        return new DanceGroupService(danceGroupSource, personService);
    }

    @Bean
    public PerfomanceSource perfomanceSource() {
        return new PerfomanceSource();
    }

    @Bean
    public PerfomanceService perfomanceService(PerfomanceSource perfomanceSource) {
        return new PerfomanceService(perfomanceSource);
    }

    @Bean
    public PersonSource personSource() {
        return new PersonSource();
    }

    @Bean
    public PersonService personService(PersonSource personSource) {
        return new PersonService(personSource);
    }

    @Bean
    public SalarySource salarySource() {
        return new SalarySource();
    }

    @Bean
    public SalaryService salaryService(SalarySource salarySource) {
        return new SalaryService(salarySource);
    }
}
