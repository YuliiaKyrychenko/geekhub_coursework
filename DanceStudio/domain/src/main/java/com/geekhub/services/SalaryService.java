package com.geekhub.services;

import com.geekhub.config.AppConfig;
import com.geekhub.config.DatabaseConfig;
import com.geekhub.exceptions.ValidationException;
import com.geekhub.models.DanceGroup;
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
    private final AttendanceService attendanceService;
    private final DanceGroupService danceGroupService;

    final String INSERT_QUERY = "INSERT INTO salary (id, teacherId, firstName, lastName, month)" +
            " VALUES (:id, :teacherId, :firstName, :lastName, :month)";
    final String GET_QUERY = "SELECT * from salary where id = :id";
    final String DELETE_QUERY = "DELETE from salary where id = :id";
    final String SHOW_ALL_QUERY = "SELECT * from salary";
    final String UPDATE_SALARY = "UPDATE salary SET salary = :salary WHERE teacherId = :teacherId AND month = :month";

    public SalaryService(SalarySource salarySource,
                         PersonService personService,
                         AttendanceService attendanceService,
                         DanceGroupService danceGroupService) {
        this.salarySource = salarySource;
        this.personService = personService;
        this.attendanceService = attendanceService;
        this.danceGroupService = danceGroupService;
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
            salaries += salary.getId() + " " + salary.getTeacherId() + " " + salary.getFirstName() + " "
                    + salary.getLastName() + " " + salary.getMonth() + "\n";
        }
        return salaries;
    }

    public int updateSalary(int id, int groupId, int attendanceId) {
        Salary salary = getSalaryById(id);
        DanceGroup danceGroup = danceGroupService.getDanceGroupById(groupId);
        int teacherId = danceGroup.getTeacherId();
        String month = attendanceService.getAttendanceById(attendanceId).getMonth();
        int attendanceSum = attendanceService.getGeneralSum(attendanceId) + salary.getRate();
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                DatabaseConfig.class, AppConfig.class);
        NamedParameterJdbcTemplate jdbcTemplate = (NamedParameterJdbcTemplate) context.getBean("jdbcTemplate");
        int salarySum = jdbcTemplate.update(UPDATE_SALARY, Map.of("salary", attendanceSum, "teacherId", teacherId,
                "month", month));
        return salarySum;
    }
}
