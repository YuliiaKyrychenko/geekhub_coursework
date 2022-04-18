package api.console;

import com.geekhub.models.Attendance;
import com.geekhub.models.DanceGroup;
import com.geekhub.models.Person;
import com.geekhub.services.AttendanceService;
import com.geekhub.services.DanceGroupService;
import com.geekhub.services.PersonService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@ComponentScan(basePackages = {"com.geekhub.config"})
@RestController
@RequestMapping("/dance-groups")
public class DanceGroupController {
    private final DanceGroupService danceGroupService;
    private final PersonService personService;
    private final AttendanceService attendanceService;

    public DanceGroupController(DanceGroupService danceGroupService, PersonService personService, AttendanceService attendanceService) {
        this.danceGroupService = danceGroupService;
        this.personService = personService;
        this.attendanceService = attendanceService;
    }


    @RequestMapping(
            path = {"", "/"},
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE,
            consumes = MediaType.ALL_VALUE
    )

    @GetMapping
    public String showDanceGroups() {
        return danceGroupService.showDanceGroups();
    }

    @GetMapping("/people-in-group")
    public String showDanceGroups(@PathVariable Integer id) {
        return danceGroupService.showPeopleInGroups(id);
    }

    @GetMapping("/{id}")
    public String findDanceGroupById(@PathVariable Integer id) {
        DanceGroup danceGroup = danceGroupService.getDanceGroupById(id);

        return danceGroup.getId() + " " + danceGroup.getTeacherId() + " " + danceGroup.getFirstName() + " "
                + danceGroup.getLastName() + " " + danceGroup.getStyle() + " " + danceGroup.getAgeCategorie() + " " +
                danceGroup.getDanceHall() + " " + danceGroup.getDaysOfWeek() + " " + danceGroup.getDanceTime();
    }

    @DeleteMapping("/{id}")
    public String deleteDanceGroupById(@PathVariable Integer id) {
        DanceGroup danceGroup = danceGroupService.getDanceGroupById(id);
        int danceGroupId = danceGroup.getId();
        danceGroupService.deleteDanceGroup(id);
        return "You have deleted: " + danceGroupId  + " dance group";
    }

    @PutMapping("/addGroup")
    public String saveDanceGroup(@RequestParam(name = "style") String style,
                                 @RequestParam (name = "teacherId") String teacherId,
                                 @RequestParam (name = "ageCategorie") String ageCategorie,
                                 @RequestParam (name = "danceHall") String danceHall,
                                 @RequestParam (name = "daysOfWeek") String daysOfWeek,
                                 @RequestParam (name = "danceTime") String danceTime) {
        DanceGroup danceGroup = danceGroupService.addNewDanceGroup(style, Integer.parseInt(teacherId),
                ageCategorie, danceHall, daysOfWeek, danceTime);
        return "You have added: " + danceGroup.getId() + " dance group";
    }

    @PutMapping("/addStudent")
    public String saveStudent(@RequestParam(name = "groupId") String groupId,
                              @RequestParam (name = "studentId") String studentId,
                              @RequestParam (name = "attendanceId") String attendanceId) {
        DanceGroup danceGroup = danceGroupService.getDanceGroupById(Integer.parseInt(groupId));
        Person student = personService.getPersonById(Integer.parseInt(studentId));
        danceGroup.getGroup().add(student);
        return "You have added a student with id: " + studentId + " to a dance group with id: " + groupId;
    }
}
