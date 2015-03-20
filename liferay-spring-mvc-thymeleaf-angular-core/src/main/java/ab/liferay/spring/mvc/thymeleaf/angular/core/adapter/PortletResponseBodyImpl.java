package ab.liferay.spring.mvc.thymeleaf.angular.core.adapter;

import ab.liferay.spring.mvc.thymeleaf.angular.core.annotation.PortletResponseBody;
import ab.liferay.spring.mvc.thymeleaf.angular.core.util.Integration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.annotation.ModelAndViewResolver;

import javax.portlet.ResourceResponse;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by abayer on 17.02.2015.
 * Automatic JSON serialization doesn't work yet in Portlet MVC
 * See: https://jira.spring.io/browse/SPR-7344
 */
public class PortletResponseBodyImpl implements ModelAndViewResolver {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    @Override
    public ModelAndView resolveModelAndView(Method method, Class clazz, final Object returnValue, ExtendedModelMap modelMap, NativeWebRequest request) {

        if (request.getNativeResponse() instanceof ResourceResponse && method.getAnnotation(PortletResponseBody.class) != null) {
            return new ModelAndView() {
                @Override
                public View getView() {
                    return new View() {
                        @Override
                        public String getContentType() {
                            return Integration.JSON_UTF_8_CONTENT_TYPE.toString();
                        }

                        @Override
                        public void render(Map<String, ?> model, javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws Exception {
                            JSON_MAPPER.writeValue(response.getWriter(), returnValue);
                        }
                    };
                }
            };
        }
        return UNRESOLVED;
    }
}