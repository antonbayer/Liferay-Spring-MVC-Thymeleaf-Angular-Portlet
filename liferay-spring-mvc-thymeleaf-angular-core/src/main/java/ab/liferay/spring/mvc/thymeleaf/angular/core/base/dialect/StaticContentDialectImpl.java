package ab.liferay.spring.mvc.thymeleaf.angular.core.base.dialect;

import ab.liferay.spring.mvc.thymeleaf.angular.core.base.service.PortletService;
import ab.liferay.spring.mvc.thymeleaf.angular.core.portlet.util.StaticUtil;
import org.thymeleaf.Arguments;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.AttributeNameProcessorMatcher;
import org.thymeleaf.processor.IAttributeNameProcessorMatcher;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.processor.attr.AbstractStandardSingleAttributeModifierAttrProcessor;

import java.util.HashSet;
import java.util.Set;

public class StaticContentDialectImpl extends AbstractDialect implements StaticContentDialect {

    public static final String LAYOUT_NAMESPACE = "https://github.com/antonbayer/Liferay-Spring-MVC-Thymeleaf-Angular-Portlet";
    final String DIALECT_NAME = "src";
    final String DIALECT_PREFIX = "sc";
    private final PortletService portletService;

    public StaticContentDialectImpl(PortletService portletService) {
        this.portletService = portletService;
    }

    @Override
    public String getPrefix() {
        return DIALECT_PREFIX;
    }

    @Override
    public Set<IProcessor> getProcessors() {
        final Set<IProcessor> processors = new HashSet<IProcessor>();
        processors.add(new StandardContentProcessor(new AttributeNameProcessorMatcher(DIALECT_NAME)));
        return processors;
    }

    private class StandardContentProcessor extends AbstractStandardSingleAttributeModifierAttrProcessor {

        protected StandardContentProcessor(IAttributeNameProcessorMatcher matcher) {
            super(matcher);
        }

        @Override
        public int getPrecedence() {
            return 1000;
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
            return DIALECT_NAME;
        }

        @Override
        protected String getTargetAttributeValue(
                final Arguments arguments, final Element element, final String attributeName) {
            return StaticUtil.getStaticContentUrl(portletService.getRenderResponse(), element.getAttributeValue(attributeName));
        }
    }
}