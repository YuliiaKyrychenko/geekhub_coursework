package api.console;

import com.geekhub.models.Salary;
import com.geekhub.services.SalaryService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@ComponentScan(basePackages = {"com.geekhub.config"})
@RestController
@RequestMapping("/salary")
public class SalaryController {
    private final SalaryService salaryService;

    public SalaryController(SalaryService salaryService) {
        this.salaryService = salaryService;
    }

    @RequestMapping(
            path = {"", "/"},
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE,
            consumes = MediaType.ALL_VALUE
    )

    @GetMapping
    public String showSalaries() {
        return salaryService.showSalaries();
    }

    @GetMapping("/{id}")
    public String findSalaryById(@PathVariable Integer id) {
        Salary salary = salaryService.getSalaryById(id);

        return salary.getId() + " " + salary.getTeacherId() + " " + salary.getFirstName() + " "
                + salary.getLastName() + " " + salary.getMonth();
    }

    @DeleteMapping("/{id}")
    public String deleteSalaryById(@PathVariable Integer id) {
        Salary salary = salaryService.getSalaryById(id);
        int salaryId = salary.getId();
        salaryService.deleteSalary(id);
        return "You have deleted: " + salaryId  + " salary";
    }

    @PutMapping("/add")
    public String saveSalary(@RequestParam(name = "teacherId") String teacherId,
                             @RequestParam (name = "month") String month) {
        Salary salary = salaryService.addNewSalary(Integer.valueOf(teacherId), month);
        return "You have added: " + salary.getId() + " salary";
    }

    @PutMapping("/update")
    public String updateSalary(@RequestParam(name = "groupId") String groupId,
                               @RequestParam(name = "month") String month,
                               @RequestParam (name = "teacherId") String teacherId) {
        double salary = salaryService.updateSalary(Integer.valueOf(groupId), month, Integer.valueOf(teacherId));
        return "The salary for a teacher with id: " + teacherId + " is: " + salary;
    }
}
