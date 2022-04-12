package com.geekhub.services;

import com.geekhub.config.AppConfig;
import com.geekhub.config.DatabaseConfig;
import com.geekhub.exceptions.ValidationException;
import com.geekhub.models.Attendance;
import com.geekhub.models.Person;
import com.geekhub.sources.AttendanceSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceService {

    private final AttendanceSource attendanceSource;
    private final PersonService personService;
    final String INSERT_QUERY = "INSERT INTO attendance (id, studentId, firstName, lastName, month, discount) VALUES (" +
            ":id, :studentId, :firstName, :lastName, :month, :discount)";
    final String GET_QUERY = "SELECT * from attendance where id = :id";
    final String DELETE_QUERY = "DELETE from attendance where id = :id";
    final String SHOW_ALL_QUERY = "SELECT * from attendance";

    public AttendanceService(AttendanceSource attendanceSource, PersonService personService) {
        this.attendanceSource = attendanceSource;
        this.personService = personService;
    }

    public Attendance addNewAttendance(int studentId, String month, int discount) {
        if(month.isBlank()) {
            throw new ValidationException("Validation has failed cause of empty fields");
        }
        Person student = personService.getPersonById(studentId);
        Map<String, Boolean> personAttendance = new HashMap<>();
        int id = (int) (Math.random()*(600+1)) - 200;
        Attendance newAttendance = new Attendance(id, studentId, student.getFirstName(), student.getLastName(),
                month, discount);
        newAttendance.setPersonAttendance(personAttendance);
        attendanceSource.add(newAttendance);

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                DatabaseConfig.class, AppConfig.class);
        NamedParameterJdbcTemplate jdbcTemplate = (NamedParameterJdbcTemplate) context.getBean("jdbcTemplate");
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("studentId", studentId);
        params.put("firstName", student.getFirstName());
        params.put("lastName", student.getLastName());
        params.put("month", month);
        params.put("discount", discount);
        jdbcTemplate.update(INSERT_QUERY, params);
        return newAttendance;
    }

    public Attendance getAttendanceById(int id) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                DatabaseConfig.class, AppConfig.class);
        NamedParameterJdbcTemplate jdbcTemplate = (NamedParameterJdbcTemplate) context.getBean("jdbcTemplate");
        Attendance attendance = jdbcTemplate.queryForObject(GET_QUERY, Map.of("id", id), (rs, rowNum) ->
                new Attendance(
                        rs.getInt("id"),
                        rs.getInt("studentId"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("month"),
                        rs.getInt("discount")
                )
        );
        return attendanceSource.get(id);
    }

    public void deleteAttendanceById(int id) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                DatabaseConfig.class, AppConfig.class);
        NamedParameterJdbcTemplate jdbcTemplate = (NamedParameterJdbcTemplate) context.getBean("jdbcTemplate");
        jdbcTemplate.update(DELETE_QUERY, Map.of("id", id));
        attendanceSource.delete(id);
    }

    public String showAttendances() {
        String attendances = "";
        for (int i = 0; i < attendanceSource.showAttendances().size(); i++) {
            attendances += attendanceSource.get(i).getId() + " " + attendanceSource.get(i).getStudentId() +"\n";
        }
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                DatabaseConfig.class, AppConfig.class);
        NamedParameterJdbcTemplate jdbcTemplate = (NamedParameterJdbcTemplate) context.getBean("jdbcTemplate");
        List<Attendance> list = jdbcTemplate.query(SHOW_ALL_QUERY, (rs, rowNum) -> {
            Attendance attendance = new Attendance(
                    rs.getInt("id"),
                    rs.getInt("studentId"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("month"),
                    rs.getInt("discount")
            );
            return attendance;
        });
        for (Attendance attendance : list) {
            System.out.println("id: " + attendance.getId() + " student id: " + attendance.getStudentId());
        }
        return attendances;
    }

    public Map<String, Boolean> addAttandance(int attandanceId, String date, boolean isPresent) {
        Attendance attendance = attendanceSource.get(attandanceId);
        attendance.getPersonAttendance().put(date, isPresent);
        return attendance.getPersonAttendance();
    }

    public int countAttandance(int attandanceId) {
        Attendance attendance = attendanceSource.get(attandanceId);
        int count = 0;
        for (Map.Entry<String, Boolean> entry : attendance.getPersonAttendance().entrySet()) {
            if(entry.getValue()) {
                count++;
            }
        }
        return count;
    }
}
