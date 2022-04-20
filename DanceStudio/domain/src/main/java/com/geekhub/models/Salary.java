package com.geekhub.models;

public class Salary {
    private int id;
    private int teacherId;
    private String firstName;
    private String lastName;
    private String month;
    private final int rate = 6000;

    public Salary(int id, int teacherId, String firstName, String lastName, String month) {
        this.id = id;
        this.teacherId = teacherId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.month = month;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getRate() {
        return rate;
    }
}
