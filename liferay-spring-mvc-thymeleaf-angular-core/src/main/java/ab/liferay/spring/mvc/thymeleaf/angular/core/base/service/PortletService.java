package ab.liferay.spring.mvc.thymeleaf.angular.core.base.service;

import com.liferay.portal.theme.ThemeDisplay;

import javax.portlet.*;
import java.util.Locale;
import java.util.Map;

public interface PortletService {

    String STATIC_CONTENT_RESOURCE_URL = "/static/";

    PortletConfig getPortletConfig();

    PortletRequest getPortletRequest();

    ThemeDisplay getThemeDisplay();

    PortletURL getPortletUrl(Map<String, String> params);

    PortletURL getPortletUrl(Map<String, String> params, String url, String portletId);

    String getPortletId(String portletName);

    RenderRequest getRenderRequest();

    ResourceRequest getResourceRequest();

    ActionRequest getActionRequest();

    EventRequest getEventRequest();

    PortletResponse getPortletResponse();

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

    String getStaticContentUrl(String path);

    PortletPreferences getPortletPreferences();

    PortletContext getPortletContext();
}
