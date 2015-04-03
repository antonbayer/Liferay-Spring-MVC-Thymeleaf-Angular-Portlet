package ab.liferay.spring.mvc.thymeleaf.angular.core.base.adapter;

import ab.liferay.spring.mvc.thymeleaf.angular.core.base.annotation.PortletRequestBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

import javax.portlet.ClientDataRequest;
import java.io.IOException;
import java.text.MessageFormat;

public class PortletRequestBodyImpl


        implements WebArgumentResolver {

    private static final ObjectMapper JSON_MAPPER = new ObjectMapper();
    private static final String JSON_REQUESTBODY_CACHE_ATTRIBUTE = "JSON_REQUESTBODY_CACHE";

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
                return JSON_MAPPER.readValue(getRequestBody(clientDataRequest), param.getParameterType());
            }
            throw new RuntimeException(MessageFormat.format("REST Method {0} for body not supported.", clientDataRequest.getMethod()));
        }
        return WebArgumentResolver.UNRESOLVED;
    }

    private boolean isMethod(ClientDataRequest clientDataRequest, RequestMethod requestMethod) {
        return requestMethod.name().equalsIgnoreCase(clientDataRequest.getMethod());
    }

    private String getRequestBody(ClientDataRequest clientDataRequest) {
        Object obj = clientDataRequest.getAttribute(JSON_REQUESTBODY_CACHE_ATTRIBUTE);
        if (obj == null) {
            StringBuilder builder = new StringBuilder();
            String aux = "";
            try {
                while ((aux = clientDataRequest.getReader().readLine()) != null) {
                    builder.append(aux);
                }
                String json = builder.toString();

                clientDataRequest.setAttribute(JSON_REQUESTBODY_CACHE_ATTRIBUTE, json);
                return json;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return obj.toString();

    }
}