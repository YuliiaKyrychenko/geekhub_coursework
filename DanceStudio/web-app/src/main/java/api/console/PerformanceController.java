package api.console;

import com.geekhub.models.Performance;
import com.geekhub.services.PerfomanceService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@ComponentScan(basePackages = {"com.geekhub.config"})
@RestController
@RequestMapping("/performances")
public class PerformanceController {
    private final PerfomanceService perfomanceService;


    public PerformanceController(PerfomanceService perfomanceService) {
        this.perfomanceService = perfomanceService;
    }

    @RequestMapping(
            path = {"", "/"},
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE,
            consumes = MediaType.ALL_VALUE
    )

    @GetMapping
    public String showPerformances() {
        return perfomanceService.showPerformances();
    }

    @GetMapping("/{id}")
    public String findPerformanceById(@PathVariable Integer id) {
        Performance performance = perfomanceService.getPerformanceById(id);

        return performance.getId() + " " + performance.getName() + " " + performance.getPlace() + " "
                + performance.getDate() + " " + performance.getPrice();
    }

    @DeleteMapping("/{id}")
    public String deletePerformanceById(@PathVariable Integer id) {
        Performance performance = perfomanceService.getPerformanceById(id);
        int performanceId = performance.getId();
        perfomanceService.deletePerformance(id);
        return "You have deleted: " + performanceId  + " performance";
    }

    @PutMapping("/add")
    public String savePerformance(@RequestParam(name = "name") String name,
                                  @RequestParam (name = "date") String date,
                                  @RequestParam (name = "place") String place,
                                  @RequestParam (name = "price") String price) {
        Performance performance = perfomanceService.addNewPerformance(name, date, place, Double.valueOf(price));
        return "You have added: " + performance.getId() + " performance";
    }
}
