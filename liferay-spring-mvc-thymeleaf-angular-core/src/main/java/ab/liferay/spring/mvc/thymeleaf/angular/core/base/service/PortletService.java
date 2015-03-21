package ab.liferay.spring.mvc.thymeleaf.angular.core.base.service;

import javax.portlet.*;
import java.util.Locale;

public interface PortletService {

    PortletConfig getPortletConfig();

    RenderRequest getRenderRequest();

    ResourceRequest getResourceRequest();

    ActionRequest getActionRequest();

    EventRequest getEventRequest();

    RenderResponse getRenderResponse();

    ResourceResponse getResourceResponse();

    ActionResponse getActionResponse();

    EventResponse getEventResponse();

    PortletSession getPortletSession();
    
    Locale getLocale();

    WindowState getWindowStateExclusive();

    WindowState getWindowStatePopup();

    WindowState getWindowStateMaximized();

    WindowState getWindowStateMinimized();

    WindowState getWindowStateNormal();
}
