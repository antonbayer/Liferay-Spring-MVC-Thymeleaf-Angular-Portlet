package ab.liferay.spring.mvc.thymeleaf.angular.core.base.config;

import ab.liferay.spring.mvc.thymeleaf.angular.core.base.adapter.PortletRequestBodyImpl;
import ab.liferay.spring.mvc.thymeleaf.angular.core.base.adapter.PortletRequestBodyWebArgumentResolver;
import ab.liferay.spring.mvc.thymeleaf.angular.core.base.adapter.PortletResponseBodyImpl;
import ab.liferay.spring.mvc.thymeleaf.angular.core.base.component.request.RequestBodyCache;
import ab.liferay.spring.mvc.thymeleaf.angular.core.base.handler.MappingExceptionResolver;
import ab.liferay.spring.mvc.thymeleaf.angular.core.base.service.PortletService;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.web.portlet.mvc.annotation.AnnotationMethodHandlerAdapter;

import javax.portlet.PortletConfig;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

@Configuration
@Import(ThymeleafConfig.class)
@ComponentScan(basePackages = {"ab.liferay.spring.mvc.thymeleaf.angular.core.base.component", "ab.liferay.spring.mvc.thymeleaf.angular.core.base.service", "ab.liferay.spring.mvc.thymeleaf.angular.core.controller"})
public class CoreConfig {

    public static final String MISSING_PROPERTY_INDICATOR = "??##**!!__";
    private static final String ERROR_VIEW = "error/error";

    @Bean
    public MappingExceptionResolver mappingExceptionResolver(PortletService portletService) {

        MappingExceptionResolver mappingExceptionResolver = new MappingExceptionResolver(portletService);
        mappingExceptionResolver.setDefaultErrorView(ERROR_VIEW);
        return mappingExceptionResolver;
    }

    @Bean
    public AnnotationMethodHandlerAdapter annotationMethodHandlerAdapter(PortletRequestBodyWebArgumentResolver portletRequestBody) {

        AnnotationMethodHandlerAdapter annotationMethodHandlerAdapter = new AnnotationMethodHandlerAdapter();
        annotationMethodHandlerAdapter.setCustomArgumentResolver(portletRequestBody);
        annotationMethodHandlerAdapter.setCustomModelAndViewResolver(new PortletResponseBodyImpl());
        annotationMethodHandlerAdapter.setOrder(0);

        return annotationMethodHandlerAdapter;
    }

    @Bean
    public PortletRequestBodyWebArgumentResolver portletRequestBody(RequestBodyCache requestBodyCache) {
        return new PortletRequestBodyImpl(requestBodyCache);
    }

    @Bean
    public MessageSource messageSource(final PortletService portletService) {

        return new AbstractMessageSource() {
            @Override
            protected MessageFormat resolveCode(String code, Locale locale) {

                PortletConfig portletConfig = portletService.getPortletConfig();
                ResourceBundle resourceBundle = portletConfig.getResourceBundle(locale);

                String text;
                try {
                    text = resourceBundle.getString(code);
                } catch (MissingResourceException e) {
                    text = MISSING_PROPERTY_INDICATOR + code + "_" + locale;
                }

                return new MessageFormat(text);
            }
        };
    }
}