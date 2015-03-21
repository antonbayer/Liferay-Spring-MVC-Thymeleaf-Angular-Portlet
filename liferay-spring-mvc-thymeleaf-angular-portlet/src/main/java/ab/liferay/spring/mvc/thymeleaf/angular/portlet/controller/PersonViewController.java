package ab.liferay.spring.mvc.thymeleaf.angular.portlet.controller;

import ab.liferay.spring.mvc.thymeleaf.angular.core.base.annotation.ViewController;
import ab.liferay.spring.mvc.thymeleaf.angular.core.base.service.PortletService;
import ab.liferay.spring.mvc.thymeleaf.angular.core.portlet.util.StaticUtil;
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

import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceURL;
import javax.portlet.WindowStateException;

@ViewController
public class PersonViewController {

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
    public String view(ModelMap model) throws WindowStateException {

        _log.debug("handle view");

        RenderResponse response = portletService.getRenderResponse();

        model.addAttribute("persons", personService.getPersons());

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
        ajaxUrl.setParameter("personsFragment", "");
        ajaxUrl.setWindowState(portletService.getWindowStateExclusive());
        model.addAttribute("ajaxUrl", ajaxUrl.toString());

        model.addAttribute("fileUrl", StaticUtil.getStaticContentUrl(portletService.getRenderResponse(), "image.jpg"));

        return "index/index";
    }

    @RenderMapping(params = {"personsFragment"})
    public String personsFragment(Model model) {

        _log.debug("handle personsFragment");

        model.addAttribute("persons", personService.getPersons());
        return "/index/index :: personsFragment";
    }

    @RenderMapping(params = {"personId"})
    public String render(ModelMap model) {

        _log.debug("handle render");

        long personId = ParamUtil.getLong(portletService.getRenderRequest(), "personId");
        return "index/render";
    }

    @ActionMapping(params = {"personId"})
    public void action() {

        _log.debug("handle action");

        long personId = ParamUtil.getLong(portletService.getActionRequest(), "personId");
        portletService.getActionResponse().setRenderParameter("personId", String.valueOf(personId));
    }
}