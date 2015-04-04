package ab.liferay.spring.mvc.thymeleaf.angular.core.base.adapter;

import ab.liferay.spring.mvc.thymeleaf.angular.core.base.annotation.PortletRequestBody;
import ab.liferay.spring.mvc.thymeleaf.angular.core.base.component.request.RequestBodyCache;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

import javax.portlet.ClientDataRequest;
import java.io.IOException;
import java.text.MessageFormat;

public class PortletRequestBodyImpl implements PortletRequestBodyWebArgumentResolver {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
    private final RequestBodyCache requestBodyCache;

    @Autowired
    public PortletRequestBodyImpl(RequestBodyCache requestBodyCache) {
        this.requestBodyCache = requestBodyCache;
    }

    @Override
    public Object resolveArgument(MethodParameter param, NativeWebRequest request) throws Exception {

        if (request.getNativeRequest() instanceof ClientDataRequest && param.hasParameterAnnotation(PortletRequestBody.class)) {
            String value = param.getParameterAnnotation(PortletRequestBody.class).value();
            ClientDataRequest clientDataRequest = request.getNativeRequest(ClientDataRequest.class);
            if (!PortletRequestBody.DEFAULT.equals(value)) {
                if (isMethod(clientDataRequest, RequestMethod.POST)) {
                    String json = JSON_MAPPER.readTree(getRequestBody(clientDataRequest)).get(value).toString();
                    return JSON_MAPPER.readValue(json, param.getParameterType());
                } else if (isMethod(clientDataRequest, RequestMethod.GET)) {
                    return JSON_MAPPER.readValue(request.getParameter(value), param.getParameterType());
                }
                throw new RuntimeException(MessageFormat.format("REST Method {0} for values not supported.", clientDataRequest.getMethod()));
            }
            if (isMethod(clientDataRequest, RequestMethod.POST)) {
                return JSON_MAPPER.readValue(clientDataRequest.getReader(), param.getParameterType());
            }
            throw new RuntimeException(MessageFormat.format("REST Method {0} for body not supported.", clientDataRequest.getMethod()));
        }
        return WebArgumentResolver.UNRESOLVED;
    }

    private boolean isMethod(ClientDataRequest clientDataRequest, RequestMethod requestMethod) {
        return requestMethod.name().equalsIgnoreCase(clientDataRequest.getMethod());
    }

    private String getRequestBody(ClientDataRequest clientDataRequest) {
        if (requestBodyCache.getBody() == null) {
            StringBuilder builder = new StringBuilder();
            String aux = "";
            try {
                while ((aux = clientDataRequest.getReader().readLine()) != null) {
                    builder.append(aux);
                }
                requestBodyCache.setBody(builder.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return requestBodyCache.getBody();

    }
}