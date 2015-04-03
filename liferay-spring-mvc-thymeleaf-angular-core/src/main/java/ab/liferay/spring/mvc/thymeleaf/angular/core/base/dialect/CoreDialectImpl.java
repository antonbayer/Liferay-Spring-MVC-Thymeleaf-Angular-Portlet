package ab.liferay.spring.mvc.thymeleaf.angular.core.base.dialect;

import ab.liferay.spring.mvc.thymeleaf.angular.core.base.model.MessageList;
import ab.liferay.spring.mvc.thymeleaf.angular.core.base.model.MessageType;
import ab.liferay.spring.mvc.thymeleaf.angular.core.base.service.MessageService;
import ab.liferay.spring.mvc.thymeleaf.angular.core.base.service.PortletService;
import org.thymeleaf.Arguments;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.NestableNode;
import org.thymeleaf.dom.Text;
import org.thymeleaf.processor.*;
import org.thymeleaf.processor.element.AbstractElementProcessor;
import org.thymeleaf.standard.processor.attr.AbstractStandardSingleAttributeModifierAttrProcessor;

import java.util.HashSet;
import java.util.Set;

public class CoreDialectImpl extends AbstractDialect implements CoreDialect {

    public static final String LAYOUT_NAMESPACE = "http://core";
    final String DIALECT_PREFIX = "core";
    final String PROCESSOR_MESSAGES_NAME = "messages";
    final String PROCESSOR_SRC_NAME = "src";
    private final MessageService messageService;
    private final PortletService portletService;

    public CoreDialectImpl(PortletService portletService, MessageService messageService) {
        this.portletService = portletService;
        this.messageService = messageService;
    }

    @Override
    public String getPrefix() {
        return DIALECT_PREFIX;
    }

    @Override
    public Set<IProcessor> getProcessors() {
        final Set<IProcessor> processors = new HashSet<IProcessor>();
        processors.add(new MessageListProcessor(new ElementNameProcessorMatcher(PROCESSOR_MESSAGES_NAME, true)));
        processors.add(new StandardContentProcessor(new AttributeNameProcessorMatcher(PROCESSOR_SRC_NAME)));
        return processors;
    }

    private class MessageListProcessor extends AbstractElementProcessor {

        protected MessageListProcessor(IElementNameProcessorMatcher matcher) {
            super(matcher);
        }

        @Override
        protected ProcessorResult processElement(Arguments arguments, Element element) {

            String group = element.getAttributeValue("group");
            if (group == null) {
                group = MessageService.MESSAGE_DEFAULT_GROUP;
            }
            String type = element.getAttributeValue("type");
            if (type == null) {
                type = MessageService.MESSAGE_DEFAULT_MESSAGE_TYPE.getName();
            }
            MessageType messageType = MessageType.getMessageTypeByName(type);

            final NestableNode parent = element.getParent();
            MessageList messages = messageService.getRequestMessages(messageType, group);
            if (messages != null) {
                Element container = new Element("ul");
                element.addChild(container);
                for (String message : messages) {
                    Element entry = new Element("li");
                    entry.addChild(new Text(message));
                    container.addChild(entry);
                }
                parent.insertBefore(element, container);
            }
            parent.removeChild(element);

            return ProcessorResult.OK;
        }

        @Override
        public int getPrecedence() {
            return 1000;
        }
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
            return PROCESSOR_SRC_NAME;
        }

        @Override
        protected String getTargetAttributeValue(
                final Arguments arguments, final Element element, final String attributeName) {
            return portletService.getStaticContentUrl(element.getAttributeValue(attributeName));
        }
    }
}