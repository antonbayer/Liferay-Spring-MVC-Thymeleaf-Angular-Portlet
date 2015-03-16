package ab.liferay.spring.mvc.thymeleaf.angular.portlet.test;

import ab.liferay.spring.mvc.thymeleaf.angular.portlet.controller.PersonViewController;
import ab.liferay.spring.mvc.thymeleaf.angular.portlet.service.PersonService;
import ab.liferay.spring.mvc.thymeleaf.angular.core.service.PortletService;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/test")
public class TestPersonViewController extends PersonViewController {


    public TestPersonViewController(PersonService personService, PortletService portletService) {
        super(personService, portletService);
    }

    @RequestMapping(value = "/view")
    public String view(ModelMap model) {
        return super.view(model);
    }
}