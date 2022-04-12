package com.geekhub.models;

public class Salary {
    private int id;
    private Person teacher;
    private final int rate = 6000;

    public Salary(int id, Person teacher) {
        this.id = id;
        this.teacher = teacher;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
