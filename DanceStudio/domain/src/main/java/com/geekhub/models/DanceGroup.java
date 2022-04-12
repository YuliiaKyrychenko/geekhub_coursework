package com.geekhub.models;

import com.geekhub.enums.AgeCategorie;
import com.geekhub.enums.DanceHall;
import com.geekhub.enums.DaysOfWeek;

import java.util.ArrayList;
import java.util.List;

public class DanceGroup {
    private int id;
    private String style;
    private int teacherId;
    private String firstName;
    private String lastName;
    private AgeCategorie ageCategorie;
    private DanceHall danceHall;
    private List<DaysOfWeek> daysOfWeek = new ArrayList<>();
    private String danceTime;
    private List<Person> group = new ArrayList<>();

    public DanceGroup(int id,
                      String style,
                      int teacherId,
                      String firstName,
                      String lastName,
                      AgeCategorie ageCategorie,
                      DanceHall danceHall,
                      String danceTime) {
        this.id = id;
        this.style = style;
        this.teacherId = teacherId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.ageCategorie = ageCategorie;
        this.danceHall = danceHall;
        this.danceTime = danceTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public AgeCategorie getAgeCategorie() {
        return ageCategorie;
    }

    public void setAgeCategorie(AgeCategorie ageCategorie) {
        this.ageCategorie = ageCategorie;
    }

    public DanceHall getDanceHall() {
        return danceHall;
    }

    public void setDanceHall(DanceHall danceHall) {
        this.danceHall = danceHall;
    }

    public List<DaysOfWeek> getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(List<DaysOfWeek> daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public String getDanceTime() {
        return danceTime;
    }

    public void setDanceTime(String danceTime) {
        this.danceTime = danceTime;
    }

    public List<Person> getGroup() {
        return group;
    }

    public void setGroup(List<Person> group) {
        this.group = group;
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
}
