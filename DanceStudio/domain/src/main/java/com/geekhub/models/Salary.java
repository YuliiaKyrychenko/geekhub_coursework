package com.geekhub.models;

public class Salary {
    private Person teacher;
    private final int rate = 6000;

    public Salary(Person teacher) {
        this.teacher = teacher;
    }

    public Person getTeacher() {
        return teacher;
    }

    public void setTeacher(Person teacher) {
        this.teacher = teacher;
    }

    public int getRate() {
        return rate;
    }
}
