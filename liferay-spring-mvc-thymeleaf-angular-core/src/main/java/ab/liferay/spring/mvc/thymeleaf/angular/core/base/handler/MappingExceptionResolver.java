package ab.liferay.spring.mvc.thymeleaf.angular.core.base.handler;

import ab.liferay.spring.mvc.thymeleaf.angular.core.base.service.PortletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.handler.SimpleMappingExceptionResolver;

@Configuration
public class MappingExceptionResolver extends SimpleMappingExceptionResolver {

    private static final String EXCEPTION_HEADER_FLAG = "internal-server-exception";
    private final PortletService portletService;

    @Autowired
    public MappingExceptionResolver(PortletService portletService) {
        this.portletService = portletService;
    }

    protected ModelAndView getModelAndView(String viewName, Exception ex) {
        portletService.getPortletResponse().setProperty(EXCEPTION_HEADER_FLAG, Boolean.toString(true));
        return super.getModelAndView(viewName, ex);
    }
}