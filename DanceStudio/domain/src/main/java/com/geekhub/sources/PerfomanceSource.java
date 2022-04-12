package com.geekhub.sources;

import com.geekhub.models.Attendance;
import com.geekhub.models.Performance;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PerfomanceSource {
    private List<Performance> performances = new ArrayList<>();

    public List<Performance> showPerfomances() {
        return performances;
    }

    public Performance get(int index) {
        return performances.get(index);
    }

    public void add(Performance newPerformance) {
        performances.add(newPerformance);
    }

    public void delete(int performanceIndex) {
        if(!Objects.isNull(performances.get(performanceIndex))) {
            performances.remove(performanceIndex);
            return;
        }
    }
}
