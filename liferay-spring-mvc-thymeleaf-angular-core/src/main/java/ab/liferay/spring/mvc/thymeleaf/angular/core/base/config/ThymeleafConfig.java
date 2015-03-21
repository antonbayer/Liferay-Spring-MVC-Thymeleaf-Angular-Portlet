package ab.liferay.spring.mvc.thymeleaf.angular.core.base.config;

import ab.liferay.spring.mvc.thymeleaf.angular.core.base.service.PortletService;
import ab.liferay.spring.mvc.thymeleaf.angular.core.portlet.util.StaticUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.Arguments;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.standard.processor.attr.AbstractStandardSingleAttributeModifierAttrProcessor;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class ThymeleafConfig {

    private final String SYSTEM_PROPERTY_THYMELEAF_CACHEABLE ="thymeleaf.cacheable";

    @Bean
    public ViewResolver viewResolver(final PortletService portletService) {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine(portletService));
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
    public SpringTemplateEngine templateEngine(final PortletService portletService) {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());

        Set<IDialect> dialects = new HashSet<IDialect>();
        dialects.add(staticContentDialect(portletService));
        templateEngine.setAdditionalDialects(dialects);

        return templateEngine;
    }

    @Bean
    public AbstractDialect staticContentDialect(final PortletService portletService) {
        final String ATTR_NAME = "src";
        final String TAG_NAME = "sc";

        return new AbstractDialect() {
            @Override
            public String getPrefix() {
                return TAG_NAME;
            }

            @Override
            public Set<IProcessor> getProcessors() {
                final Set<IProcessor> processors = new HashSet<IProcessor>();
                processors.add(new AbstractStandardSingleAttributeModifierAttrProcessor(ATTR_NAME) {
                    @Override
                    public int getPrecedence() {
                        return 0;
                    }

                    @Override
                    protected ModificationType getModificationType(Arguments arguments, Element element, String attributeName, String newAttributeName) {
                        return ModificationType.SUBSTITUTION;
                    }

                    @Override
                    protected boolean removeAttributeIfEmpty(Arguments arguments, Element element, String attributeName, String newAttributeName) {
                        return false;
                    }

                    @Override
                    protected String getTargetAttributeName(Arguments arguments, Element element, String attributeName) {
                        return ATTR_NAME;
                    }

                    @Override
                    protected String getTargetAttributeValue(
                            final Arguments arguments, final Element element, final String attributeName) {
                        return StaticUtil.getStaticContentUrl(portletService.getRenderResponse(), element.getAttributeValue(attributeName));
                    }
                });
                return processors;
            }
        };
    }

    private boolean getThymeleafCacheable() {
        String thymeleafCacheable = System.getProperty(SYSTEM_PROPERTY_THYMELEAF_CACHEABLE);
        return thymeleafCacheable == null ? true : !thymeleafCacheable.equalsIgnoreCase(Boolean.FALSE.toString()); // always cacheable (true), expect the string 'false'
    }
}