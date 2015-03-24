package ab.liferay.spring.mvc.thymeleaf.angular.portlet.controller;

import ab.liferay.spring.mvc.thymeleaf.angular.core.base.annotation.ViewController;
import ab.liferay.spring.mvc.thymeleaf.angular.core.base.model.MessageType;
import ab.liferay.spring.mvc.thymeleaf.angular.core.base.service.MessageService;
import ab.liferay.spring.mvc.thymeleaf.angular.core.base.service.PortletService;
import ab.liferay.spring.mvc.thymeleaf.angular.portlet.model.Person;
import ab.liferay.spring.mvc.thymeleaf.angular.portlet.service.PersonService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceURL;
import javax.portlet.WindowStateException;
import java.util.List;

@ViewController
public class PersonViewController {

    private static Log _log = LogFactoryUtil.getLog(PersonViewController.class);

    private final PersonService personService;
    private final PortletService portletService;
    private final MessageService messageService;

    @Autowired
    public PersonViewController(PersonService personService, PortletService portletService, MessageService messageService) {
        this.personService = personService;
        this.portletService = portletService;
        this.messageService = messageService;
    }

    @RenderMapping
    @RequestMapping
    public String view(ModelMap model) throws WindowStateException {

        _log.debug("handle view");

        RenderResponse response = portletService.getRenderResponse();

        List<Person> persons = personService.getPersons();
        model.addAttribute("persons", persons);
        model.addAttribute("count", persons.size());

        ResourceURL resourceURL = response.createResourceURL();
        resourceURL.setResourceID(PersonRestController.REST_RESOURCE);
        model.addAttribute("resourceURL", resourceURL.toString());

        PortletURL renderUrl = response.createRenderURL();
        renderUrl.setParameter("personId", String.valueOf(personService.getPersons().get(0).getId()));
        model.addAttribute("renderUrl", renderUrl.toString());

        PortletURL actionUrl = response.createActionURL();
        actionUrl.setParameter("personId", String.valueOf(personService.getPersons().get(0).getId()));
        model.addAttribute("actionUrl", actionUrl.toString());

        PortletURL ajaxUrl = response.createRenderURL();
        ajaxUrl.setParameter("persons", "");
        ajaxUrl.setWindowState(portletService.getWindowStateExclusive());
        model.addAttribute("ajaxUrl", ajaxUrl.toString());

        ResourceURL jsonHtmlUrl = response.createResourceURL();
        jsonHtmlUrl.setResourceID(PersonRestController.HTML_JSON_RESOURCE);
        model.addAttribute("jsonHtmlUrl", jsonHtmlUrl.toString());

        messageService.addMessage("only.param");
        messageService.addMessage("arg.param", new Object[]{"Test"});
        messageService.addMessage("info.param", MessageType.INFO);
        messageService.addMessage("error.param", MessageType.ERROR);
        messageService.addMessage("error.special.param", MessageType.ERROR, "special");

        return "index/index";
    }

    @RenderMapping(params = {"persons"})
    public ModelAndView persons(Model model) {

        _log.debug("handle persons");

        model.addAttribute("persons", personService.getPersons());
        return new ModelAndView("index/index :: persons", model.asMap());
    }

    @RenderMapping(params = {"personId"})
    public String render(ModelMap model) {

        _log.debug("handle render");

        long personId = ParamUtil.getLong(portletService.getPortletRequest(), "personId");
        return "index/render";
    }

    @ActionMapping(params = {"personId"})
    public void action() {

        _log.debug("handle action");

        long personId = ParamUtil.getLong(portletService.getPortletRequest(), "personId");
        portletService.getActionResponse().setRenderParameter("personId", String.valueOf(personId));
    }
}