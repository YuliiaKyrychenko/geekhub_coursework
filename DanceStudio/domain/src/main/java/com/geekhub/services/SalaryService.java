package com.geekhub.services;

import com.geekhub.config.AppConfig;
import com.geekhub.config.DatabaseConfig;
import com.geekhub.exceptions.ValidationException;
import com.geekhub.models.Person;
import com.geekhub.models.Salary;
import com.geekhub.sources.SalarySource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalaryService {

    private final SalarySource salarySource;
    private final PersonService personService;

    final String INSERT_QUERY = "INSERT INTO salary (id, teacherId, firstName, lastName, month)" +
            " VALUES (:id, :teacherId, :firstName, :lastName, :month)";
    final String GET_QUERY = "SELECT * from salary where id = :id";
    final String DELETE_QUERY = "DELETE from salary where id = :id";
    final String SHOW_ALL_QUERY = "SELECT * from salary";
    final String UPDATE_SALARY = "UPDATE salary SET salary = :salary WHERE teacherId = :teacherId";
    final String GET_ALL_ATTENDANCE =
            "SELECT SUM (generalSum) from attendance where groupId = :groupId AND month = :month";

    public SalaryService(SalarySource salarySource, PersonService personService) {
        this.salarySource = salarySource;
        this.personService = personService;
    }

    public Salary addNewSalary(int teacherId, String month) {
        if(month.isBlank()) {
            throw new ValidationException("Validation has failed cause of empty fields");
        }
        int id = (int) (Math.random()*(600+1)) - 200;
        Person teacher = personService.getPersonById(teacherId);
        Salary newSalary = new Salary(id, teacherId, teacher.getFirstName(), teacher.getLastName(), month);
        salarySource.add(newSalary);

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                DatabaseConfig.class, AppConfig.class);
        NamedParameterJdbcTemplate jdbcTemplate = (NamedParameterJdbcTemplate) context.getBean("jdbcTemplate");
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("teacherId", teacherId);
        params.put("firstName", teacher.getFirstName());
        params.put("lastName", teacher.getLastName());
        params.put("month", month);
        jdbcTemplate.update(INSERT_QUERY, params);
        return newSalary;
    }

    public Salary getSalaryById(int id) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                DatabaseConfig.class, AppConfig.class);
        NamedParameterJdbcTemplate jdbcTemplate = (NamedParameterJdbcTemplate) context.getBean("jdbcTemplate");
        Salary salary = jdbcTemplate.queryForObject(GET_QUERY, Map.of("id", id), (rs, rowNum) ->
                new Salary(
                        rs.getInt("id"),
                        rs.getInt("teacherId"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("month")
                )
        );
        return salary;
    }

    public void deleteSalary(int id) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                DatabaseConfig.class, AppConfig.class);
        NamedParameterJdbcTemplate jdbcTemplate = (NamedParameterJdbcTemplate) context.getBean("jdbcTemplate");
        jdbcTemplate.update(DELETE_QUERY, Map.of("id", id));
        salarySource.delete(id);
    }

    public String showSalaries() {
        String salaries = "";
        for (int i = 0; i < salarySource.showSalaries().size(); i++) {
            salaries += salarySource.get(i).getId() + " " + salarySource.get(i).getTeacherId() + " " +
                    salarySource.get(i).getFirstName() + " " + salarySource.get(i).getLastName() +"\n";
        }

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                DatabaseConfig.class, AppConfig.class);
        NamedParameterJdbcTemplate jdbcTemplate = (NamedParameterJdbcTemplate) context.getBean("jdbcTemplate");
        List<Salary> list = jdbcTemplate.query(SHOW_ALL_QUERY, (rs, rowNum) -> {
            Salary salary = new Salary(
                    rs.getInt("id"),
                    rs.getInt("teacherId"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("month")
            );
            return salary;
        });
        for (Salary salary : list) {
            System.out.println("id: " + salary.getId() + " " + salary.getTeacherId() + " " +
                    salary.getFirstName() + " " + salary.getFirstName());
        }
        return salaries;
    }

    public double updateSalary(int groupId, String month, int teacherId) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                DatabaseConfig.class, AppConfig.class);
        NamedParameterJdbcTemplate jdbcTemplate = (NamedParameterJdbcTemplate) context.getBean("jdbcTemplate");
        int generalAttendance =
                jdbcTemplate.update(GET_ALL_ATTENDANCE, Map.of("groupId", groupId, "month", month));
        double salary = 6000 + generalAttendance;
        jdbcTemplate.update(UPDATE_SALARY, Map.of("salary", salary, "teacherId", teacherId));
        return salary;
    }
}
