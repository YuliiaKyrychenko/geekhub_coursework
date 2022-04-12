package com.geekhub.services;

import com.geekhub.config.AppConfig;
import com.geekhub.config.DatabaseConfig;
import com.geekhub.enums.AgeCategorie;
import com.geekhub.enums.DanceHall;
import com.geekhub.enums.DaysOfWeek;
import com.geekhub.exceptions.ValidationException;
import com.geekhub.models.Attendance;
import com.geekhub.models.DanceGroup;
import com.geekhub.models.Person;
import com.geekhub.sources.DanceGroupSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DanceGroupService {

    private final DanceGroupSource danceGroupSource;
    private final PersonService personService;
    final String INSERT_QUERY = "INSERT INTO dancegroup (id, style, teacherId, " +
            "firstName, lastName, ageCategorie, danceHall, daysOfWeek, danceTime) VALUES (" +
            ":id, :style, :teacherId, :firstName, :lastName, :ageCategorie, :danceHall, :daysOfWeek, :danceTime)";
    final String GET_QUERY = "SELECT * from dancegroup where id = :id";
    final String DELETE_QUERY = "DELETE from dancegroup where id = :id";
    final String SHOW_ALL_QUERY = "SELECT * from dancegroup";

    public DanceGroupService(DanceGroupSource danceGroupSource, PersonService personService) {
        this.danceGroupSource = danceGroupSource;
        this.personService = personService;
    }

    public DanceGroup addNewDanceGroup(String style,
                                       int teacherId,
                                       String firstName,
                                       String lastName,
                                       AgeCategorie ageCategorie,
                                       DanceHall danceHall,
                                       String danceTime) {
        if(style.isBlank() || ageCategorie == null || danceHall == null || danceTime.isBlank()) {
            throw new ValidationException("Validation has failed cause of empty fields");
        }
        List<DaysOfWeek> daysOfWeek = new ArrayList<>();
        List<Person> group = new ArrayList<>();
        int id = (int) (Math.random()*(600+1)) - 200;
        Person teacher = personService.getPersonById(teacherId);
        DanceGroup newDanceGroup = new DanceGroup(id,
                style,
                teacherId,
                teacher.getFirstName(),
                teacher.getLastName(),
                ageCategorie,
                danceHall,
                danceTime);
        newDanceGroup.setDaysOfWeek(daysOfWeek);
        newDanceGroup.setGroup(group);
        danceGroupSource.add(newDanceGroup);

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                DatabaseConfig.class, AppConfig.class);
        NamedParameterJdbcTemplate jdbcTemplate = (NamedParameterJdbcTemplate) context.getBean("jdbcTemplate");
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("style", style);
        params.put("teacherId", teacherId);
        params.put("firstName", teacher.getFirstName());
        params.put("lastName", teacher.getLastName());
        params.put("ageCategorie", ageCategorie);
        params.put("danceHall", danceHall);
        params.put("danceTime", danceTime);
        jdbcTemplate.update(INSERT_QUERY, params);
        return newDanceGroup;
    }

    public DanceGroup getDanceGroupById(int id) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                DatabaseConfig.class, AppConfig.class);
        NamedParameterJdbcTemplate jdbcTemplate = (NamedParameterJdbcTemplate) context.getBean("jdbcTemplate");
        DanceGroup danceGroup = jdbcTemplate.queryForObject(GET_QUERY, Map.of("id", id), (rs, rowNum) ->
                new DanceGroup(
                        rs.getInt("id"),
                        rs.getString("style"),
                        rs.getInt("teacherId"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        AgeCategorie.valueOf(rs.getString("ageCategorie")),
                        DanceHall.valueOf(rs.getString("danceHall")),
                        rs.getString("danceTime")
                )
        );
        return danceGroupSource.get(id);
    }

    public void deleteDanceGroup(int id) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                DatabaseConfig.class, AppConfig.class);
        NamedParameterJdbcTemplate jdbcTemplate = (NamedParameterJdbcTemplate) context.getBean("jdbcTemplate");
        jdbcTemplate.update(DELETE_QUERY, Map.of("id", id));
        danceGroupSource.delete(id);
    }

    public String showDanceGroups() {
        String danceGroups = "";
        for (int i = 0; i < danceGroupSource.showGroups().size(); i++) {
            danceGroups += danceGroupSource.get(i).getId() + " " + danceGroupSource.get(i).getStyle() + " " +
                    danceGroupSource.get(i).getTeacherId() +"\n";
        }

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                DatabaseConfig.class, AppConfig.class);
        NamedParameterJdbcTemplate jdbcTemplate = (NamedParameterJdbcTemplate) context.getBean("jdbcTemplate");
        List<DanceGroup> list = jdbcTemplate.query(SHOW_ALL_QUERY, (rs, rowNum) -> {
            DanceGroup danceGroup = new DanceGroup(
                    rs.getInt("id"),
                    rs.getString("style"),
                    rs.getInt("teacherId"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    AgeCategorie.valueOf(rs.getString("ageCategorie")),
                    DanceHall.valueOf(rs.getString("danceHall")),
                    rs.getString("danceTime")
            );
            return danceGroup;
        });
        for (DanceGroup danceGroup : list) {
            System.out.println("id: " + danceGroup.getId() + " teacher id: " + danceGroup.getTeacherId());
        }
        return danceGroups;
    }
}
