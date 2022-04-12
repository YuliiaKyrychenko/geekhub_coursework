package com.geekhub.sources;

import com.geekhub.models.Salary;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SalarySource {
    private List<Salary> salaries = new ArrayList<>();

    public List<Salary> showSalaries() {
        return salaries;
    }

    public Salary get(int index) {
        return salaries.get(index);
    }

    public void add(Salary newSalary) {
        salaries.add(newSalary);
    }

    public void delete(int salaryIndex) {
        if(!Objects.isNull(salaries.get(salaryIndex))) {
            salaries.remove(salaryIndex);
            return;
        }
    }
}
