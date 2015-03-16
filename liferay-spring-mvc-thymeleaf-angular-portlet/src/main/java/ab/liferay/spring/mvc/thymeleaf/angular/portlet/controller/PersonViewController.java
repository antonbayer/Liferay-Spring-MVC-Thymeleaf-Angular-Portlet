package ab.liferay.spring.mvc.thymeleaf.angular.portlet.controller;

import ab.liferay.spring.mvc.thymeleaf.angular.core.controller.ViewController;
import ab.liferay.spring.mvc.thymeleaf.angular.core.service.PortletService;
import ab.liferay.spring.mvc.thymeleaf.angular.portlet.service.PersonService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import javax.portlet.RenderResponse;
import javax.portlet.ResourceURL;

@Controller
public class PersonViewController extends ViewController {

    private static Log _log = LogFactoryUtil.getLog(PersonViewController.class);

    private final PersonService personService;
    private final PortletService portletService;

    @Autowired
    public PersonViewController(PersonService personService, PortletService portletService) {
        this.personService = personService;
        this.portletService = portletService;
    }

    @RenderMapping
    @RequestMapping
    public String view(ModelMap model) {

        _log.debug("handle zulassen start view");

        RenderResponse response = portletService.getRenderResponse();

        ResourceURL restUrl = response.createResourceURL();
        restUrl.setResourceID(PersonRestController.REST_RESOURCE);
        model.addAttribute("restUrl", restUrl.toString());

        model.addAttribute("persons", personService.getPersons());

        return "index/index";
    }
}
