package api.console.controllers;

import com.geekhub.models.Person;
import com.geekhub.services.PersonService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@ComponentScan(basePackages = {"com.geekhub.config"})
@RestController
@RequestMapping("/people")
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @RequestMapping(
            path = {"", "/"},
            method = RequestMethod.GET,
            produces = MediaType.TEXT_HTML_VALUE,
            consumes = MediaType.ALL_VALUE
    )

    @GetMapping
    public String showPeople() {
        return personService.showPeople();
    }

    @GetMapping("/{id}")
    public String findPersonById(@PathVariable Integer id) {
        Person person = personService.getPersonById(id);

        return person.getId() + " " + person.getRole() + " " + person.getFirstName() + " "
                + person.getLastName() + " " + person.getContacts() + " " + person.getBirthday();
    }

    @DeleteMapping("/{id}")
    public String deletePersonById(@PathVariable Integer id) {
        Person person = personService.getPersonById(id);
        int personId = person.getId();
        personService.deletePerson(id);
        return "You have deleted: " + personId  + "templates/person";
    }

    @PutMapping("/add")
    public String savePerson(@RequestParam(name = "firstName") String firstName,
                             @RequestParam (name = "lastName") String lastName,
                             @RequestParam (name = "contacts") String contacts,
                             @RequestParam (name = "birthday") String birthday,
                             @RequestParam (name = "role") String role){
        System.out.println("Enter something");
        Person person = personService.addNewPerson(firstName, lastName, contacts, birthday, role);
        return "You have added " + person.getFirstName() + " " + person.getLastName() + " with id " + person.getId();
    }
}
