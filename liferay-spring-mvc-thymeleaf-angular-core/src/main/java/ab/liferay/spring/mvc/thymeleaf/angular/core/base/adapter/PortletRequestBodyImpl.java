package ab.liferay.spring.mvc.thymeleaf.angular.core.base.adapter;

import ab.liferay.spring.mvc.thymeleaf.angular.core.base.annotation.PortletRequestBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

import javax.portlet.ResourceRequest;

/**
 * Created by abayer on 17.02.2015.
 */
public class PortletRequestBodyImpl implements WebArgumentResolver {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();

    @Override
    public Object resolveArgument(MethodParameter param, NativeWebRequest request) throws Exception {

        if (request.getNativeRequest() instanceof ResourceRequest && param.getParameterAnnotation(PortletRequestBody.class) != null) {
            return JSON_MAPPER.readValue(((ResourceRequest) request.getNativeRequest()).getReader(), param.getParameterType());
        }
        return WebArgumentResolver.UNRESOLVED;
    }
}