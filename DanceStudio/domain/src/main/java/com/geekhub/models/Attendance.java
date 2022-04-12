package com.geekhub.models;

import java.util.HashMap;
import java.util.Map;

public class Attendance {
    private Person student;
    private String month;
    private Map<String, Boolean> personAttendance = new HashMap<>();
    private final int pricePerLesson = 60;
    private int discount;

    public Attendance(Person student, String month, Map<String, Boolean> personAttendance, int discount) {
        this.student = student;
        this.month = month;
        this.personAttendance = personAttendance;
        this.discount = discount;
    }

    public Person getStudent() {
        return student;
    }

    public void setStudent(Person student) {
        this.student = student;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Map<String, Boolean> getPersonAttendance() {
        return personAttendance;
    }

    public void setPersonAttendance(Map<String, Boolean> personAttendance) {
        this.personAttendance = personAttendance;
    }

    public int getPricePerLesson() {
        return pricePerLesson;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}
