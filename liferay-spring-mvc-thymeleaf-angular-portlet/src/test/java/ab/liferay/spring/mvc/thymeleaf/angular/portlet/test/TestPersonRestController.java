package ab.liferay.spring.mvc.thymeleaf.angular.portlet.test;

import ab.liferay.spring.mvc.thymeleaf.angular.portlet.controller.PersonRestController;
import ab.liferay.spring.mvc.thymeleaf.angular.portlet.model.Person;
import ab.liferay.spring.mvc.thymeleaf.angular.portlet.service.PersonService;
import ab.liferay.spring.mvc.thymeleaf.angular.core.service.PortletService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/test")
public class TestPersonRestController extends PersonRestController {


    public TestPersonRestController(PersonService personService, PortletService portletService) {
        super(personService, portletService);
    }

    @RequestMapping(value = "/" + PersonRestController.REST_RESOURCE)
    @ResponseBody
    public List<Person> rest() {
        return super.rest();
    }

    @RequestMapping(value = "/" + PersonRestController.REST_RESOURCE, method = RequestMethod.POST)
    @ResponseBody
    public void rest(@RequestBody Person person) {
        super.rest(person);
    }
}