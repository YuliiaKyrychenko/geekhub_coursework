package com.geekhub.services;

import com.geekhub.config.AppConfig;
import com.geekhub.config.DatabaseConfig;
import com.geekhub.enums.Role;
import com.geekhub.exceptions.ValidationException;
import com.geekhub.models.Person;
import com.geekhub.sources.PersonSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonService {

    private final PersonSource personSource;
    final String INSERT_QUERY = "INSERT INTO person (id, firstName, lastName, contacts, birthday, role)" +
            " VALUES (:id, :firstName, :lastName, :contacts, :birthday, :role)";
    final String GET_QUERY = "SELECT * from person where id = :id";
    final String DELETE_QUERY = "DELETE from person where id = :id";
    final String SHOW_ALL_QUERY = "SELECT * from person";

    public PersonService(PersonSource personSource) {
        this.personSource = personSource;
    }

    public Person addNewPerson(String firstName, String lastName, String contacts, String birthday, Role role) {
        if(firstName.isBlank()|| lastName.isBlank() || contacts.isBlank() || birthday.isBlank() ||
                String.valueOf(role).isBlank()) {
            throw new ValidationException("Validation has failed cause of empty fields");
        }
        int id = (int) (Math.random()*(600+1)) - 200;
        Person newPerson = new Person(id, firstName, lastName, contacts, birthday, role);
        personSource.add(newPerson);

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                DatabaseConfig.class, AppConfig.class);
        NamedParameterJdbcTemplate jdbcTemplate = (NamedParameterJdbcTemplate) context.getBean("jdbcTemplate");
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("firstName", firstName);
        params.put("lastName", lastName);
        params.put("contacts", contacts);
        params.put("birthday", birthday);
        params.put("role", role);
        jdbcTemplate.update(INSERT_QUERY, params);
        return newPerson;
    }

    public Person getPersonById(int id) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                DatabaseConfig.class, AppConfig.class);
        NamedParameterJdbcTemplate jdbcTemplate = (NamedParameterJdbcTemplate) context.getBean("jdbcTemplate");
        Person person = jdbcTemplate.queryForObject(GET_QUERY, Map.of("id", id), (rs, rowNum) ->
                new Person(
                        rs.getInt("id"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("contacts"),
                        rs.getString("birthday"),
                        Role.valueOf(rs.getString("role"))
                )
        );
        return person;
    }

    public void deletePerson(int id) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                DatabaseConfig.class, AppConfig.class);
        NamedParameterJdbcTemplate jdbcTemplate = (NamedParameterJdbcTemplate) context.getBean("jdbcTemplate");
        jdbcTemplate.update(DELETE_QUERY, Map.of("id", id));
        personSource.delete(id);
    }

    public String showPeople() {
        String people = "";
        for (int i = 0; i < personSource.showPeople().size(); i++) {
            people += personSource.get(i).getFirstName() + " " + personSource.get(i).getLastName() +"\n";
        }

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                DatabaseConfig.class, AppConfig.class);
        NamedParameterJdbcTemplate jdbcTemplate = (NamedParameterJdbcTemplate) context.getBean("jdbcTemplate");
        List<Person> list = jdbcTemplate.query(SHOW_ALL_QUERY, (rs, rowNum) -> {
            Person person = new Person(
                    rs.getInt("id"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("contacts"),
                    rs.getString("birthday"),
                    Role.valueOf(rs.getString("role"))
            );
            return person;
        });
        for (Person person : list) {
            System.out.println("id: " + person.getId() + " " + person.getFirstName() + " " + person.getFirstName());
        }
        return people;
    }
}
