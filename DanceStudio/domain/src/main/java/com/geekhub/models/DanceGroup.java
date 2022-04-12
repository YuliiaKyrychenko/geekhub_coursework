package com.geekhub.models;

import com.geekhub.enums.AgeCategorie;
import com.geekhub.enums.DaysOfWeek;

import java.util.ArrayList;
import java.util.List;

public class DanceGroup {
    private int id;
    private String style;
    private Person teacher;
    private AgeCategorie ageCategorie;
    private String danceHall;
    private List<DaysOfWeek> dayOfWeek = new ArrayList<>();
    private String danceTime;
    private List<Person> group = new ArrayList<>();

    public DanceGroup(int id,
                      String style,
                      Person teacher,
                      AgeCategorie ageCategorie,
                      String danceHall,
                      List<DaysOfWeek> dayOfWeek,
                      String danceTime) {
        this.id = id;
        this.style = style;
        this.teacher = teacher;
        this.ageCategorie = ageCategorie;
        this.danceHall = danceHall;
        this.dayOfWeek = dayOfWeek;
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

    public Person getTeacher() {
        return teacher;
    }

    public void setTeacher(Person teacher) {
        this.teacher = teacher;
    }

    public AgeCategorie getAgeCategorie() {
        return ageCategorie;
    }

    public void setAgeCategorie(AgeCategorie ageCategorie) {
        this.ageCategorie = ageCategorie;
    }

    public String getDanceHall() {
        return danceHall;
    }

    public void setDanceHall(String danceHall) {
        this.danceHall = danceHall;
    }

    public List<DaysOfWeek> getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(List<DaysOfWeek> dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
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
}
