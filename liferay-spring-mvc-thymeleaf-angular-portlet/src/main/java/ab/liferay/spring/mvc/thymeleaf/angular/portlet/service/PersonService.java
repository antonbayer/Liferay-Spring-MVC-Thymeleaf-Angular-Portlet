package ab.liferay.spring.mvc.thymeleaf.angular.portlet.service;

import ab.liferay.spring.mvc.thymeleaf.angular.portlet.model.Person;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {

    List<Person> persons;

    public PersonService() {
    }

    public List<Person> getPersons() {

        if (persons == null) {
            persons = new ArrayList<Person>();

            addPerson(new Person(1, "DemoFirstname1", "DemoFirstname1"));
            addPerson(new Person(2, "DemoFirstname3", "DemoFirstname2"));
            addPerson(new Person(3, "DemoFirstname3", "DemoFirstname3"));
        }

        return persons;
    }

    public void addPerson(Person person) {
        getPersons().add(person);

    }
}