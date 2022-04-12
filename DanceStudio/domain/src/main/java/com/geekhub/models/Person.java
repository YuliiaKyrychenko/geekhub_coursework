package com.geekhub.models;

import com.geekhub.enums.Role;

public class Person {
    private int id;
    private String firstName;
    private String lastName;
    private String contacts;
    private String birthday;
    private Role role;
    private static final long serialVersionUID = 1L;

    public Person(int id, String firstName, String lastName, String contacts, String birthday, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contacts = contacts;
        this.birthday = birthday;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
