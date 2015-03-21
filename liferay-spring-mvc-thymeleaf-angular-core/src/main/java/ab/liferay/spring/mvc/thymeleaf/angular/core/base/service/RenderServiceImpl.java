package ab.liferay.spring.mvc.thymeleaf.angular.core.base.service;

import com.liferay.portal.util.PortalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.context.IContext;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.AjaxThymeleafView;
import org.thymeleaf.spring4.view.ThymeleafView;
import org.thymeleaf.standard.fragment.StandardDOMSelectorFragmentSpec;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class RenderServiceImpl implements RenderService {

    private static final java.lang.String FRAGMENT_SEPARATOR = "::";
    private final PortletService portletService;
    private final SpringTemplateEngine templateEngine;

    @Autowired
    public RenderServiceImpl(PortletService portletService, SpringTemplateEngine templateEngine) {
        this.portletService = portletService;
        this.templateEngine = templateEngine;
    }

    @Override
    public String renderTemplate(ModelAndView modelAndView) {

        ThymeleafView view = new AjaxThymeleafView();
        String[] views = modelAndView.getViewName().split(FRAGMENT_SEPARATOR);
        view.setTemplateName(views[0].trim());
        if (views.length == 2) {
            view.setFragmentSpec(new StandardDOMSelectorFragmentSpec(views[1].trim()));
        }

        HttpServletRequest httpServletRequest = PortalUtil.getHttpServletRequest(portletService.getPortletRequest());
        HttpServletResponse httpServletResponse = PortalUtil.getHttpServletResponse(portletService.getPortletResponse());

        IContext iContext = new WebContext(httpServletRequest, httpServletResponse, httpServletRequest.getServletContext(), portletService.getLocale(), modelAndView.getModel());
        return templateEngine.process(view.getTemplateName(), iContext, view.getFragmentSpec());
    }

    @Override
    public String renderTemplate(String viewName, ModelMap modelMap) {
        return renderTemplate(new ModelAndView(viewName, modelMap));
    }
}