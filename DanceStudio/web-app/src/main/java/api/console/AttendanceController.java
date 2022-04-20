package api.console;

import com.geekhub.models.Attendance;
import com.geekhub.services.AttendanceService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@ComponentScan(basePackages = {"com.geekhub.config"})
@RestController
@RequestMapping("/attendances")
public class AttendanceController {
    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @RequestMapping(
            path = {"", "/"},
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE,
            consumes = MediaType.ALL_VALUE
    )

    @GetMapping
    public String showAttendances() {
        return attendanceService.showAttendances();
    }

    @GetMapping("/{id}")
    public String findAttendanceById(@PathVariable Integer id) {
        Attendance attendanceById = attendanceService.getAttendanceById(id);

        return attendanceById.getId() + " " + attendanceById.getStudentId() + " " + attendanceById.getFirstName() + " "
                + attendanceById.getLastName() + " " + attendanceById.getMonth();
    }

    @DeleteMapping("/{id}")
    public String deleteAttendanceById(@PathVariable Integer id) {
        Attendance attendanceById = attendanceService.getAttendanceById(id);
        int attendanceId = attendanceById.getId();
        attendanceService.deleteAttendanceById(id);
        return "You have deleted: " + attendanceId  + " attendance";
    }

    @PutMapping("/addAttendanceId")
    public String addAttendanceId(@RequestParam(name = "studentId") String studentId,
                                 @RequestParam (name = "month") String month) {
        Attendance attendance = attendanceService.addNewAttendance(Integer.parseInt(studentId), month);
        return "You have added: " + attendance.getId() + " attendance";
    }

    @PutMapping("/updateGroupId")
    public String updateGroupId(@RequestParam(name = "attendanceId") String attendanceId,
                                @RequestParam(name = "groupId") String groupId,
                                @RequestParam(name = "studentId") String studentId) {
        attendanceService.changeGroupId(
                Integer.parseInt(attendanceId),
                Integer.parseInt(groupId),
                Integer.parseInt(studentId));
        return "You have updated: " + attendanceId + " attendance";
    }

    @PutMapping("/addAttendance")
    public String addAttendance(@RequestParam(name = "attandanceId") String attandanceId,
                                          @RequestParam (name = "date") String date,
                                          @RequestParam (name = "isPresent") String isPresent) {
        String presence = "";
        Attendance attendance = attendanceService.getAttendanceById(Integer.parseInt(attandanceId));
        attendance.getPersonAttendance().put(date, Boolean.valueOf(isPresent));
        if(Boolean.parseBoolean(isPresent)) {
            presence += " was in training";
        }
        else {
            presence += " wasn`t in training";
        }
        return date + " student with id: " + attendance.getStudentId() + presence;
    }

    @PutMapping("/add-current-attendance")
    public String updateCurrentAttendance(@RequestParam(name = "groupId") String groupId,
                                          @RequestParam (name = "studentId") String studentId,
                                          @RequestParam (name = "attandanceId") String attandanceId) {
        int currentAttendance = attendanceService.generateCurrentAttendance(
                Integer.parseInt(groupId),
                Integer.parseInt(studentId),
                Integer.parseInt(attandanceId));
        return "Current attendance for a student with id: " + studentId + " in group with id: " + groupId + " is: " +
                currentAttendance;
    }

    @PutMapping("/add-general-attendance")
    public String updateGeneralAttendance(@RequestParam(name = "groupId") String groupId,
                                          @RequestParam (name = "studentId") String studentId,
                                          @RequestParam (name = "attandanceId") String attandanceId) {
        int generalAttendance = attendanceService.generateGeneralAttendance(
                Integer.parseInt(groupId),
                Integer.parseInt(studentId),
                Integer.parseInt(attandanceId));
        return "General attendance for a student with id: " + studentId + " in group with id: " + groupId + " is: " +
                generalAttendance;
    }

    @PutMapping("/add-general-sum")
    public String updateGeneralSum(@RequestParam(name = "groupId") String groupId,
                                          @RequestParam (name = "studentId") String studentId,
                                          @RequestParam (name = "attandanceId") String attandanceId) {
        int generalAttendance = attendanceService.generateGeneralSum(
                Integer.parseInt(groupId),
                Integer.parseInt(studentId),
                Integer.parseInt(attandanceId));
        return "General attendance for a student with id: " + studentId + " in group with id: " + groupId + " is: " +
                generalAttendance;
    }
}
