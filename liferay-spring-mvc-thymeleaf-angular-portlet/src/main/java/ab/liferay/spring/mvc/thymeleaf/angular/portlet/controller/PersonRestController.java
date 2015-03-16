package ab.liferay.spring.mvc.thymeleaf.angular.portlet.controller;

import ab.liferay.spring.mvc.thymeleaf.angular.core.annotation.PortletRequestBody;
import ab.liferay.spring.mvc.thymeleaf.angular.core.annotation.PortletResponseBody;
import ab.liferay.spring.mvc.thymeleaf.angular.core.controller.ViewController;
import ab.liferay.spring.mvc.thymeleaf.angular.portlet.model.Person;
import ab.liferay.spring.mvc.thymeleaf.angular.portlet.service.PersonService;
import ab.liferay.spring.mvc.thymeleaf.angular.core.service.PortletService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import java.util.List;

@Controller
public class PersonRestController extends ViewController {

    private static Log _log = LogFactoryUtil.getLog(PersonRestController.class);

    public static final String REST_RESOURCE = "rest";

    private final PersonService personService;
    private final PortletService portletService;

    @Autowired
    public PersonRestController(PersonService personService, PortletService portletService) {
        this.personService = personService;
        this.portletService = portletService;
    }

    @ResourceMapping(value = REST_RESOURCE)
    @RequestMapping(method = RequestMethod.GET)
    @PortletResponseBody
    public List<Person> rest() {

        return personService.getPersons();
    }

    @ResourceMapping(REST_RESOURCE)
    @RequestMapping(method = RequestMethod.POST)
    @PortletResponseBody
    public void rest(@PortletRequestBody Person person) {

        personService.addPerson(person);
    }
}
