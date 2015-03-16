package ab.liferay.spring.mvc.thymeleaf.angular.core.config;

import ab.liferay.spring.mvc.thymeleaf.angular.core.annotation.PortletRequestBodyImpl;
import ab.liferay.spring.mvc.thymeleaf.angular.core.annotation.PortletResponseBodyImpl;
import ab.liferay.spring.mvc.thymeleaf.angular.core.service.PortletService;
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
@ComponentScan(basePackages = { "ab.liferay.spring.mvc.thymeleaf.angular.core.service" })
public class CoreConfig {

    @Bean
    public AnnotationMethodHandlerAdapter annotationMethodHandlerAdapter() {

        AnnotationMethodHandlerAdapter annotationMethodHandlerAdapter = new AnnotationMethodHandlerAdapter();
        annotationMethodHandlerAdapter.setCustomArgumentResolver(new PortletRequestBodyImpl());
        annotationMethodHandlerAdapter.setCustomModelAndViewResolver(new PortletResponseBodyImpl());
        annotationMethodHandlerAdapter.setOrder(0);

        return annotationMethodHandlerAdapter;
    }

    @Bean
    public MessageSource messageSource(final PortletService portletService) {

        MessageSource messageSource = new AbstractMessageSource() {
            @Override
            protected MessageFormat resolveCode(String code, Locale locale) {

                PortletConfig portletConfig = portletService.getPortletConfig();
                ResourceBundle resourceBundle = portletConfig.getResourceBundle(locale);

                    String text;
                    try {
                        text = resourceBundle.getString(code);
                    }catch (MissingResourceException e) {
                        text = "??" + code + "_" + locale + "??";
                    }

                    return new MessageFormat(text);
             }
        };
        return messageSource;
    }
}