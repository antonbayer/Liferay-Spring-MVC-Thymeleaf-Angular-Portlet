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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceURL;
import javax.portlet.WindowStateException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        PortletURL friendlyActionRedirectUrl = response.createActionURL();
        friendlyActionRedirectUrl.setParameter("action", "addRedirect");
        friendlyActionRedirectUrl.setParameter("id", "5");
        model.addAttribute("friendlyActionRedirectUrl", friendlyActionRedirectUrl.toString());

        PortletURL friendlyActionForwardUrl = response.createActionURL();
        friendlyActionForwardUrl.setParameter("action", "addForward");
        friendlyActionForwardUrl.setParameter("id", "5");
        model.addAttribute("friendlyActionForwardUrl", friendlyActionForwardUrl.toString());

        PortletURL friendlyRenderUrl = response.createRenderURL();
        friendlyRenderUrl.setParameter("render", "details");
        friendlyRenderUrl.setParameter("id", "6");
        model.addAttribute("friendlyRenderUrl", friendlyRenderUrl.toString());

        messageService.addRequestMessage("index.info", MessageType.INFO);

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
        messageService.addRequestMessage("render.info.person", new Object[]{personId}, MessageType.INFO);
        return "index/render";
    }

    @ActionMapping(params = {"personId"})
    public void action() {

        _log.debug("handle action");

        long personId = ParamUtil.getLong(portletService.getPortletRequest(), "personId");
        messageService.addRequestMessage("action.warning.person", new Object[]{personId}, MessageType.WARNING);
        portletService.getActionResponse().setRenderParameter("personId", String.valueOf(personId));
    }

    @ActionMapping(params = "action=addForward")
    public void friendlyActionForward(ModelMap model, @RequestParam("id") final String personId) throws IOException {

        _log.debug("handle friendly action forward");

        messageService.addRequestMessage("action.forward.warning.person", new Object[]{personId}, MessageType.INFO);

        portletService.getActionResponse().setRenderParameter("id", String.valueOf(personId));
        portletService.getActionResponse().setRenderParameter("render", "details");
    }

    @ActionMapping(params = "action=addRedirect")
    public void friendlyActionRedirect(ModelMap model, @RequestParam("id") final String personId) throws IOException {

        _log.debug("handle friendly action redirect");

        messageService.addFlashMessage("action.redirect.warning.person", new Object[]{personId}, MessageType.INFO);

        Map map = new HashMap<String, String>();
        map.put("render", "details");
        map.put("id", String.valueOf(personId));
        portletService.sendPortletRedirect(map);
    }

    @RenderMapping(params = "render=details")
    public String friendlyRender(ModelMap model, @RequestParam("id") final String personId) {

        _log.debug("handle friendly render");

        messageService.addRequestMessage("render.info.person", new Object[]{personId}, MessageType.INFO);
        return "index/render";
    }
}