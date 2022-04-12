package com.geekhub.services;

import com.geekhub.exceptions.ValidationException;
import com.geekhub.models.Person;
import com.geekhub.models.Salary;
import com.geekhub.sources.SalarySource;

public class SalaryService {

    private final SalarySource salarySource;

    public SalaryService(SalarySource salarySource) {
        this.salarySource = salarySource;
    }

    public Salary addNewSalary(Person teacher) {
        if(teacher == null) {
            throw new ValidationException("Validation has failed cause of empty fields");
        }
        int id = (int) (Math.random()*(600+1)) - 200;
        Salary newSalary = new Salary(id, teacher);
        salarySource.add(newSalary);
        return newSalary;
    }

    public Salary getSalaryById(int id) {
        return salarySource.get(id);
    }

    public void deleteSalary(int id) {
        salarySource.delete(id);
    }

    public String showSalaries() {
        String salaries = "";
        for (int i = 0; i < salarySource.showSalaries().size(); i++) {
            salaries += salarySource.get(i).getId() + " " + salarySource.get(i).getTeacher().getFirstName() + " " +
                    salarySource.get(i).getTeacher().getLastName() +"\n";
        }
        return salaries;
    }

//    public double countSalary(int generalAttendance) {
////        double salary = 6000 + generalAttendance
////        return salary;
//    }
}
