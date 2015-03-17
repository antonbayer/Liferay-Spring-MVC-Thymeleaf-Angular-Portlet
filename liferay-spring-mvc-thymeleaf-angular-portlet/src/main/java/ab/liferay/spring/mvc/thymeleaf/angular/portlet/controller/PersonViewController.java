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
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import javax.portlet.PortletURL;
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

        _log.debug("handle view");

        RenderResponse response = portletService.getRenderResponse();

        model.addAttribute("persons", personService.getPersons());

        ResourceURL resourceURL = response.createResourceURL();
        resourceURL.setResourceID(PersonRestController.REST_RESOURCE);
        model.addAttribute("resourceURL", resourceURL.toString());

        PortletURL renderUrl = response.createRenderURL();
        renderUrl.setParameter("view", "render");
        renderUrl.setParameter("personId", String.valueOf(personService.getPersons().get(0).getId()));
        model.addAttribute("renderUrl", renderUrl.toString());

        PortletURL actionUrl = response.createActionURL();
        actionUrl.setParameter("view", "action");
        actionUrl.setParameter("personId", String.valueOf(personService.getPersons().get(0).getId()));
        model.addAttribute("actionUrl", actionUrl.toString());

        return "index/index";
    }

    @RenderMapping(params = {"personId"})
    @RequestMapping
    public String render(ModelMap model) {

        _log.debug("handle render");

        long personId = Long.valueOf(portletService.getRenderRequest().getParameter("personId"));

        return "index/render";
    }

    @ActionMapping(params = {"personId"})
    @RequestMapping
    public void action() {

        _log.debug("handle action");

        long personId = Long.valueOf(portletService.getActionRequest().getParameter("personId"));

        portletService.getActionResponse().setRenderParameter("personId", String.valueOf(personId));
    }
}