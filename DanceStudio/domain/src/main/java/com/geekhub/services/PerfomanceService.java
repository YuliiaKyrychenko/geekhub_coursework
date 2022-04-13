package com.geekhub.services;

import com.geekhub.config.AppConfig;
import com.geekhub.config.DatabaseConfig;
import com.geekhub.exceptions.ValidationException;
import com.geekhub.models.Performance;
import com.geekhub.sources.PerfomanceSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PerfomanceService {

    private final PerfomanceSource perfomanceSource;
    final String INSERT_QUERY = "INSERT INTO performance (id, name, date, place, price) VALUES (:id, :name, :date, :place, :price)";
    final String GET_QUERY = "SELECT * from performance where id = :id";
    final String DELETE_QUERY = "DELETE from performance where id = :id";
    final String SHOW_ALL_QUERY = "SELECT * from performance";

    public PerfomanceService(PerfomanceSource perfomanceSource) {
        this.perfomanceSource = perfomanceSource;
    }

    public Performance addNewPerformance(String name, String date, String place, double price) {
        if(name.isBlank() || date.isBlank() || place.isBlank()) {
            throw new ValidationException("Validation has failed cause of empty fields");
        }
        int id = (int) (Math.random()*(600+1)) - 200;
        Performance newPerformance = new Performance(id,  name, date, place, price);
        perfomanceSource.add(newPerformance);

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                DatabaseConfig.class, AppConfig.class);
        NamedParameterJdbcTemplate jdbcTemplate = (NamedParameterJdbcTemplate) context.getBean("jdbcTemplate");
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("name", name);
        params.put("date", date);
        params.put("place", place);
        params.put("price", price);
        jdbcTemplate.update(INSERT_QUERY, params);
        return newPerformance;
    }

    public Performance getPerformanceById(int id) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                DatabaseConfig.class, AppConfig.class);
        NamedParameterJdbcTemplate jdbcTemplate = (NamedParameterJdbcTemplate) context.getBean("jdbcTemplate");
        Performance performance = jdbcTemplate.queryForObject(GET_QUERY, Map.of("id", id), (rs, rowNum) ->
                new Performance(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("date"),
                        rs.getString("place"),
                        rs.getInt("price"))
        );
        return performance;
    }

    public void deletePerformance(int id) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                DatabaseConfig.class, AppConfig.class);
        NamedParameterJdbcTemplate jdbcTemplate = (NamedParameterJdbcTemplate) context.getBean("jdbcTemplate");
        jdbcTemplate.update(DELETE_QUERY, Map.of("id", id));
        perfomanceSource.delete(id);
    }

    public String showPerformances() {
        String performances = "";
        for (int i = 0; i < perfomanceSource.showPerfomances().size(); i++) {
            performances += perfomanceSource.get(i).getId() + " " + perfomanceSource.get(i).getName() + " " +
                    perfomanceSource.get(i).getPlace() +
                    perfomanceSource.get(i).getDate() +"\n";
        }

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                DatabaseConfig.class, AppConfig.class);
        NamedParameterJdbcTemplate jdbcTemplate = (NamedParameterJdbcTemplate) context.getBean("jdbcTemplate");
        List<Performance> list = jdbcTemplate.query(SHOW_ALL_QUERY, (rs, rowNum) -> {
            Performance performance = new Performance(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("date"),
                    rs.getString("place"),
                    rs.getInt("price")
            );
            return performance;
        });
        for (Performance performance : list) {
            System.out.println("id: " + performance.getId() + " " + performance.getName());
        }
        return performances;
    }
}
