package ab.liferay.spring.mvc.thymeleaf.angular.portlet.test;

import ab.liferay.spring.mvc.thymeleaf.angular.core.base.service.PortletService;
import ab.liferay.spring.mvc.thymeleaf.angular.portlet.controller.PersonViewController;
import ab.liferay.spring.mvc.thymeleaf.angular.portlet.model.GlobalErrorBox;
import ab.liferay.spring.mvc.thymeleaf.angular.portlet.service.PersonService;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.portlet.WindowStateException;

@RequestMapping("/test")
public class TestPersonViewController extends PersonViewController {

    public TestPersonViewController(PersonService personService, PortletService portletService) {
        super(personService, portletService);
    }

    @RequestMapping(value = "/view")
    @Override
    public String view(GlobalErrorBox globalErrorBox, Errors globalErrors, ModelMap model) throws WindowStateException {
        return super.view(globalErrorBox, globalErrors, model);
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