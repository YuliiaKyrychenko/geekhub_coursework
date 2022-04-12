package com.geekhub.sources;

import com.geekhub.models.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PersonSource {
    private List<Person> people = new ArrayList<>();

    public List<Person> showPeople() {
        return people;
    }

    public Person get(int index) {
        return people.get(index);
    }

    public void add(Person newPerson) {
        people.add(newPerson);
    }

    public void delete(int personIndex) {
        if(!Objects.isNull(people.get(personIndex))) {
            people.remove(personIndex);
            return;
        }
    }

}
