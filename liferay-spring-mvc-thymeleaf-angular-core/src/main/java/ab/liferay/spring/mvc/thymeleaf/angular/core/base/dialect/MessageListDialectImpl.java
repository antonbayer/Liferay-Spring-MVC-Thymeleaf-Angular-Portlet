package ab.liferay.spring.mvc.thymeleaf.angular.core.base.dialect;

import ab.liferay.spring.mvc.thymeleaf.angular.core.base.model.MessageList;
import ab.liferay.spring.mvc.thymeleaf.angular.core.base.model.MessageType;
import ab.liferay.spring.mvc.thymeleaf.angular.core.base.service.MessageService;
import ab.liferay.spring.mvc.thymeleaf.angular.core.portlet.Constants;
import org.thymeleaf.Arguments;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.NestableNode;
import org.thymeleaf.dom.Text;
import org.thymeleaf.processor.ElementNameProcessorMatcher;
import org.thymeleaf.processor.IElementNameProcessorMatcher;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.processor.element.AbstractElementProcessor;

import java.util.HashSet;
import java.util.Set;

public class MessageListDialectImpl extends AbstractDialect implements MessageListDialect {

    public static final String LAYOUT_NAMESPACE = "https://github.com/antonbayer/Liferay-Spring-MVC-Thymeleaf-Angular-Portlet";
    final String DIALECT_NAME = "messages";
    final String DIALECT_PREFIX = "ab";
    private final MessageService messageService;

    public MessageListDialectImpl(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public String getPrefix() {
        return DIALECT_PREFIX;
    }

    @Override
    public Set<IProcessor> getProcessors() {
        final Set<IProcessor> processors = new HashSet<IProcessor>();
        processors.add(new MessageListProcessor(new ElementNameProcessorMatcher(DIALECT_NAME, true)));
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
                group = Constants.MESSAGE_DEFAULT_GROUP;
            }
            String type = element.getAttributeValue("type");
            if (type == null) {
                type = Constants.MESSAGE_DEFAULT_MESSAGE_TYPE.getName();
            }
            MessageType messageType = MessageType.getMessageTypeByName(type);

            element.clearChildren();
            Element container = new Element("ul");
            element.addChild(container);
            MessageList messages = messageService.getMessages(messageType, group);
            if (messages != null) {
                for (String message : messages) {
                    Element entry = new Element("li");
                    entry.addChild(new Text(message));
                    container.addChild(entry);
                }
            }

            final NestableNode parent = element.getParent();
            parent.insertBefore(element, container);
            parent.removeChild(element);

            return ProcessorResult.OK;
        }

        @Override
        public int getPrecedence() {
            return 1000;
        }
    }
}