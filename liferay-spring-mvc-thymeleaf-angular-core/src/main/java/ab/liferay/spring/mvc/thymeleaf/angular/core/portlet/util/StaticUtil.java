package ab.liferay.spring.mvc.thymeleaf.angular.core.portlet.util;

import ab.liferay.spring.mvc.thymeleaf.angular.core.portlet.Constants;

import javax.portlet.MimeResponse;
import javax.portlet.ResourceURL;

public class StaticUtil {

    public static String getStaticContentUrl(MimeResponse response, String path) {
        ResourceURL fileURL = response.createResourceURL();
        fileURL.setResourceID(Constants.STATIC_CONTENT_RESOURCE_URL);
        fileURL.setParameter(Constants.STATIC_CONTENT_FILE_PARAM, path);
        return fileURL.toString();
    }
}