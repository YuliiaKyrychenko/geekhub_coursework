package com.geekhub.services;

import com.geekhub.enums.Role;
import com.geekhub.exceptions.ValidationException;
import com.geekhub.models.Person;
import com.geekhub.sources.PersonSource;

public class PersonService {
    private final PersonSource personSource;

    public PersonService(PersonSource personSource) {
        this.personSource = personSource;
    }

    public Person addNewPerson(String firstName, String lastName, String contacts, String birthday, Role role) {
        if(firstName.isBlank()|| lastName.isBlank() || contacts.isBlank() || birthday.isBlank() ||
                String.valueOf(role).isBlank()) {
            throw new ValidationException("Validation has failed cause of empty fields");
        }
        int id = (int) (Math.random()*(600+1)) - 200;
        Person newPerson = new Person(id, firstName, lastName, contacts, birthday, role);
        personSource.add(newPerson);
        return newPerson;
    }

    public Person getPersonById(int id) {
        return personSource.get(id);
    }

    public void deletePerson(int id) {
        personSource.delete(id);
    }

    public String showPeople() {
        String people = "";
        for (int i = 0; i < personSource.showPeople().size(); i++) {
            people += personSource.get(i).getFirstName() + " " + personSource.get(i).getLastName() +"\n";
        }
        return people;
    }
}
