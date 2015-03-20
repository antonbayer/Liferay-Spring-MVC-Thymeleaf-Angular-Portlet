package ab.liferay.spring.mvc.thymeleaf.angular.portlet.test;

import ab.liferay.spring.mvc.thymeleaf.angular.core.base.service.PortletService;
import ab.liferay.spring.mvc.thymeleaf.angular.portlet.controller.PersonViewController;
import ab.liferay.spring.mvc.thymeleaf.angular.portlet.service.PersonService;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.portlet.WindowStateException;

@RequestMapping("/test")
public class TestPersonViewController extends PersonViewController {


    public TestPersonViewController(PersonService personService, PortletService portletService) {
        super(personService, portletService);
    }

    @RequestMapping(value = "/view")
    public String view(ModelMap model) throws WindowStateException {
        return super.view(model);
    }

    @RequestMapping(value = "/render")
    public String render(ModelMap model) {
        return super.render(model);
    }

    @RequestMapping(value = "/action")
    public void action() {
        super.action();
    }
}