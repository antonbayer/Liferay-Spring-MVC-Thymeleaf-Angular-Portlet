package ab.liferay.spring.mvc.thymeleaf.angular.portlet.service;

import ab.liferay.spring.mvc.thymeleaf.angular.portlet.model.Person;

import java.util.List;

public interface PersonService {

    List<Person> getPersons();

    void addPerson(Person person);
}