package ab.liferay.spring.mvc.thymeleaf.angular.core.base.adapter;

import ab.liferay.spring.mvc.thymeleaf.angular.core.base.annotation.PortletRequestBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

import javax.portlet.ResourceRequest;

public class PortletRequestBodyImpl implements WebArgumentResolver {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    @Override
    public Object resolveArgument(MethodParameter param, NativeWebRequest request) throws Exception {

        PortletRequestBody portletRequestBody = param.getParameterAnnotation(PortletRequestBody.class);
        if (request.getNativeRequest() instanceof ResourceRequest && portletRequestBody != null) {
            String value = portletRequestBody.value();
            if (!PortletRequestBody.DEFAULT.equals(value)) {
                return JSON_MAPPER.readValue(request.getParameter(value), param.getParameterType());
            }
            return JSON_MAPPER.readValue(((ResourceRequest) request.getNativeRequest()).getReader(), param.getParameterType());
        }
        return WebArgumentResolver.UNRESOLVED;
    }
}