package api.console;

import com.geekhub.config.AppConfig;
import com.geekhub.config.DatabaseConfig;
import org.flywaydb.core.Flyway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class ApplicationStarter {
    public static void main(String[] args) {
        var applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(DatabaseConfig.class, AppConfig.class);
        applicationContext.refresh();
        Flyway flyway = (Flyway) applicationContext.getBean("flyway");
        flyway.migrate();
        SpringApplication.run(ApplicationStarter.class, args);
    }
}
