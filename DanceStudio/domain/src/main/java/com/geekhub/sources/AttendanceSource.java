package com.geekhub.sources;

import com.geekhub.models.Attendance;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AttendanceSource {
    private List<Attendance> attendances = new ArrayList<>();

    public List<Attendance> showAttendances() {
        return attendances;
    }

    public Attendance get(int index) {
        return attendances.get(index);
    }

    public void add(Attendance newAttendance) {
        attendances.add(newAttendance);
    }

    public void delete(int attendanceIndex) {
        if(!Objects.isNull(attendances.get(attendanceIndex))) {
            attendances.remove(attendanceIndex);
            return;
        }
    }
}
