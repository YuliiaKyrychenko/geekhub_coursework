package com.geekhub.models;

public class Attendance {
    private int id;
    private int studentId;
    private String firstName;
    private String lastName;
    private String month;
    private final int pricePerLesson = 60;

    public Attendance(int id, int studentId, String firstName, String lastName, String month) {
        this.id = id;
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.month = month;
    }

    public int getId() { return id; }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
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

    public int getPricePerLesson() {
        return pricePerLesson;
    }

}
