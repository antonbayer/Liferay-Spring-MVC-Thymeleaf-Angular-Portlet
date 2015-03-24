package ab.liferay.spring.mvc.thymeleaf.angular.core.base.config;

import ab.liferay.spring.mvc.thymeleaf.angular.core.base.dialect.MessageListDialect;
import ab.liferay.spring.mvc.thymeleaf.angular.core.base.dialect.MessageListDialectImpl;
import ab.liferay.spring.mvc.thymeleaf.angular.core.base.dialect.StaticContentDialect;
import ab.liferay.spring.mvc.thymeleaf.angular.core.base.dialect.StaticContentDialectImpl;
import ab.liferay.spring.mvc.thymeleaf.angular.core.base.service.MessageService;
import ab.liferay.spring.mvc.thymeleaf.angular.core.base.service.PortletService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class ThymeleafConfig {

    private final String SYSTEM_PROPERTY_THYMELEAF_CACHEABLE ="thymeleaf.cacheable";

    @Bean
    public ViewResolver viewResolver(SpringTemplateEngine templateEngine) {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine);
        return viewResolver;
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setPrefix("/WEB-INF/thymeleaf/templates/");
        templateResolver.setSuffix(".xhtml");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCacheable(getThymeleafCacheable());
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine(SpringResourceTemplateResolver templateResolver, StaticContentDialect staticContentDialect, MessageListDialect messageListDialect) {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Set<IDialect> dialects = new HashSet<IDialect>();
        dialects.add(messageListDialect);
        dialects.add(staticContentDialect);
        templateEngine.setAdditionalDialects(dialects);

        return templateEngine;
    }

    @Bean
    public StaticContentDialect staticContentDialect(final PortletService portletService) {
        return new StaticContentDialectImpl(portletService);
    }

    @Bean
    public MessageListDialect messageListDialect(final MessageService messageService) {
        return new MessageListDialectImpl(messageService);
    }

    private boolean getThymeleafCacheable() {
        String thymeleafCacheable = System.getProperty(SYSTEM_PROPERTY_THYMELEAF_CACHEABLE);
        return thymeleafCacheable == null ? true : !thymeleafCacheable.equalsIgnoreCase(Boolean.FALSE.toString()); // always cacheable (true), expect the string 'false'
    }
}