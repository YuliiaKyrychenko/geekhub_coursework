package com.geekhub.sources;

import com.geekhub.models.DanceGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DanceGroupSource {
    private List<DanceGroup> groups = new ArrayList<>();

    public List<DanceGroup> showGroups() {
        return groups;
    }

    public DanceGroup get(int index) {
        return groups.get(index);
    }

    public void add(DanceGroup newGroup) {
        groups.add(newGroup);
    }

    public void delete(int groupIndex) {
        if(!Objects.isNull(groups.get(groupIndex))) {
            groups.remove(groupIndex);
            return;
        }
    }
}
