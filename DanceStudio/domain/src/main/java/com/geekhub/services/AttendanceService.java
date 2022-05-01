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
    final String INSERT_QUERY = "INSERT INTO attendance (id, studentId, firstName, lastName, month) VALUES (" +
            ":id, :studentId, :firstName, :lastName, :month)";
    final String GET_QUERY = "SELECT * from attendance where id = :id";
    final String DELETE_QUERY = "DELETE from attendance where id = :id";
    final String SHOW_ALL_QUERY = "SELECT * from attendance";
    final String UPDATE_CURRENT_ATTENDANCE = "UPDATE attendance SET currentAttendance = :currentAttendance " +
            "WHERE groupId = :groupId AND studentId = :studentId AND id = :id";
    final String UPDATE_GENERAL_ATTENDANCE = "UPDATE attendance SET generalAttendance = :generalAttendance " +
            "WHERE groupId = :groupId AND studentId = :studentId AND id = :id";
    final String UPDATE_GENERAL_SUM = "UPDATE attendance SET generalSum = :generalSum " +
            "WHERE groupId = :groupId AND studentId = :studentId AND id = :id";
    final String UPDATE_GROUP_ID = "UPDATE attendance SET groupId = :groupId " +
            "WHERE id = :id AND studentId = :studentId AND id = :id";
    final String GET_GENERAL_SUM = "SELECT SUM (generalAttendance) from attendance where id = :id";


    public AttendanceService(AttendanceSource attendanceSource, PersonService personService) {
        this.attendanceSource = attendanceSource;
        this.personService = personService;
    }

    public Attendance addNewAttendance(int studentId, String month) {
        if(month.isBlank()) {
            throw new ValidationException("Validation has failed cause of empty fields");
        }
        Person student = personService.getPersonById(studentId);
        int id = (int) (Math.random()*(600+1)) - 200;
        Attendance newAttendance = new Attendance(id, studentId, student.getFirstName(), student.getLastName(),
                month);
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
                        rs.getString("month")
                )
        );
        return attendance;
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
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                DatabaseConfig.class, AppConfig.class);
        NamedParameterJdbcTemplate jdbcTemplate = (NamedParameterJdbcTemplate) context.getBean("jdbcTemplate");
        List<Attendance> list = jdbcTemplate.query(SHOW_ALL_QUERY, (rs, rowNum) -> {
            Attendance attendance = new Attendance(
                    rs.getInt("id"),
                    rs.getInt("studentId"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("month")
            );
            return attendance;
        });
        for (Attendance attendance : list) {
            System.out.println("id: " + attendance.getId() + " student id: " + attendance.getStudentId());
            attendances += attendance.getId() + " " + attendance.getStudentId() + " " + attendance.getFirstName() + " "
                    + attendance.getLastName() + " " + attendance.getMonth();
        }
        return attendances;
    }

    public int updateCurrentAttendance(int attandanceId, int groupId, int studentId, int currentAttendanceNumber) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DatabaseConfig.class,
                AppConfig.class);
        NamedParameterJdbcTemplate jdbcTemplate = (NamedParameterJdbcTemplate) context.getBean("jdbcTemplate");
        return jdbcTemplate.update(UPDATE_CURRENT_ATTENDANCE, Map.of(
                "currentAttendance", currentAttendanceNumber,
                "groupId", groupId,
                "studentId", studentId,
                "id", attandanceId));
    }

    public int updateGeneralAttendance(int attandanceId, int groupId, int studentId, int generalAttendanceNumber) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DatabaseConfig.class,
                AppConfig.class);
        NamedParameterJdbcTemplate jdbcTemplate = (NamedParameterJdbcTemplate) context.getBean("jdbcTemplate");
        return jdbcTemplate.update(UPDATE_GENERAL_ATTENDANCE, Map.of(
                "generalAttendance", generalAttendanceNumber,
                "groupId", groupId,
                "studentId", studentId,
                "id", attandanceId));
    }

    public int updateGeneralSum(int attandanceId, int groupId, int studentId, int attandanceGeneralSum) {
        Attendance attendance = getAttendanceById(attandanceId);
        int attendanceSum = attandanceGeneralSum * attendance.getPricePerLesson();
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DatabaseConfig.class,
                AppConfig.class);
        NamedParameterJdbcTemplate jdbcTemplate = (NamedParameterJdbcTemplate) context.getBean("jdbcTemplate");
        jdbcTemplate.update(UPDATE_GENERAL_SUM, Map.of(
                "generalSum", attendanceSum,
                "groupId", groupId,
                "studentId", studentId,
                "id", attandanceId
        ));
        return attendanceSum;
    }

    public void changeGroupId(int attendanceId, int groupId, int studentId) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DatabaseConfig.class,
                AppConfig.class);
        NamedParameterJdbcTemplate jdbcTemplate = (NamedParameterJdbcTemplate) context.getBean("jdbcTemplate");
        jdbcTemplate.update(UPDATE_GROUP_ID, Map.of("id", attendanceId,"groupId", groupId, "studentId", studentId));
    }

    public int getGeneralSum(int attendanceId) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DatabaseConfig.class,
                AppConfig.class);
        NamedParameterJdbcTemplate jdbcTemplate = (NamedParameterJdbcTemplate) context.getBean("jdbcTemplate");
        return jdbcTemplate.update(GET_GENERAL_SUM, Map.of("id", attendanceId));
    }
}
