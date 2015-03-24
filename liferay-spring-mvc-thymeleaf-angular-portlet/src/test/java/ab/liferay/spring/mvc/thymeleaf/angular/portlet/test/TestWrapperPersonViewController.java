package ab.liferay.spring.mvc.thymeleaf.angular.portlet.test;

import ab.liferay.spring.mvc.thymeleaf.angular.core.base.service.MessageService;
import ab.liferay.spring.mvc.thymeleaf.angular.core.base.service.PortletService;
import ab.liferay.spring.mvc.thymeleaf.angular.portlet.controller.PersonViewController;
import ab.liferay.spring.mvc.thymeleaf.angular.portlet.service.PersonService;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.portlet.WindowStateException;

@RequestMapping("/test")
public class TestWrapperPersonViewController extends PersonViewController {

    public TestWrapperPersonViewController(PersonService personService, PortletService portletService, MessageService messageService) {
        super(personService, portletService, messageService);
    }

    @RequestMapping(value = "/view")
    @Override
    public String view(ModelMap model) throws WindowStateException {
        return super.view(model);
    }

    @RequestMapping(value = "/render")
    @Override
    public String render(ModelMap model) {
        return super.render(model);
    }

    @RequestMapping(value = "/action")
    @Override
    public void action() {
        super.action();
    }
}