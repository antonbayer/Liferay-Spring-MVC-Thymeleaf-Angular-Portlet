package ab.liferay.spring.mvc.thymeleaf.angular.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

@Configuration
public class ThymeleafConfig {

    private final String SYSTEM_PROPERTY_THYMELEAF_CACHEABLE ="thymeleaf.cacheable";

    @Bean
    public ViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        return viewResolver;
    }

    @Bean
    public ServletContextTemplateResolver templateResolver() {
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setPrefix("/WEB-INF/thymeleaf/templates/");
        templateResolver.setSuffix(".xhtml");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCacheable(getThymeleafCacheable());
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        return templateEngine;
    }

    private boolean getThymeleafCacheable() {
        String thymeleafCacheable = System.getProperty(SYSTEM_PROPERTY_THYMELEAF_CACHEABLE);
        return thymeleafCacheable == null ? true : !thymeleafCacheable.equalsIgnoreCase(Boolean.FALSE.toString()); // always cacheable (true), expect the string 'false'
    }
}
