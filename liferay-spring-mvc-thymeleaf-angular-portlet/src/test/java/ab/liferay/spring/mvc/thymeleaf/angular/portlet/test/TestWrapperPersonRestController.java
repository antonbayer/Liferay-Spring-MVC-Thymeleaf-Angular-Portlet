package ab.liferay.spring.mvc.thymeleaf.angular.portlet.test;

import ab.liferay.spring.mvc.thymeleaf.angular.core.base.service.RenderService;
import ab.liferay.spring.mvc.thymeleaf.angular.portlet.controller.PersonRestController;
import ab.liferay.spring.mvc.thymeleaf.angular.portlet.model.Person;
import ab.liferay.spring.mvc.thymeleaf.angular.portlet.service.PersonService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/test")
public class TestWrapperPersonRestController extends PersonRestController {


    public TestWrapperPersonRestController(PersonService personService, RenderService renderService) {
        super(personService, renderService);
    }

    @RequestMapping(value = "/" + PersonRestController.REST_RESOURCE)
    @ResponseBody
    @Override
    public List<Person> rest() {
        return super.rest();
    }

    @RequestMapping(value = "/" + PersonRestController.REST_RESOURCE, method = RequestMethod.POST)
    @ResponseBody
    @Override
    public void rest(@RequestBody Person person) {
        super.rest(person);
    }
}