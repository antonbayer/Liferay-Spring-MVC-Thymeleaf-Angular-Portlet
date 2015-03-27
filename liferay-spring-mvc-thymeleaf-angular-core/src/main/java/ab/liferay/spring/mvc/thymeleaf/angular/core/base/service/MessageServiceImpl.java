package ab.liferay.spring.mvc.thymeleaf.angular.core.base.service;

import ab.liferay.spring.mvc.thymeleaf.angular.core.base.model.MessageList;
import ab.liferay.spring.mvc.thymeleaf.angular.core.base.model.MessageStore;
import ab.liferay.spring.mvc.thymeleaf.angular.core.base.model.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    private static final String PORTLET_MESSAGE_STORE = "portlet.message.store";
    private final PortletService portletService;
    private final MessageSource messageSource;

    @Autowired
    public MessageServiceImpl(PortletService portletService, MessageSource messageSource) {
        this.portletService = portletService;
        this.messageSource = messageSource;
    }

    @Override
    public void addMessage(String message) {
        addMessage(message, null, MESSAGE_DEFAULT_MESSAGE_TYPE, MESSAGE_DEFAULT_GROUP);
    }

    @Override
    public void addMessage(String message, String group) {
        addMessage(message, null, MESSAGE_DEFAULT_MESSAGE_TYPE, group);
    }

    @Override
    public void addMessage(String message, MessageType messageType) {
        addMessage(message, null, messageType, MESSAGE_DEFAULT_GROUP);
    }

    @Override
    public void addMessage(String message, MessageType messageType, String group) {
        addMessage(message, null, messageType, group);
    }

    @Override
    public void addMessage(String message, Object[] arg) {
        addMessage(message, arg, MESSAGE_DEFAULT_MESSAGE_TYPE, MESSAGE_DEFAULT_GROUP);
    }

    @Override
    public void addMessage(String message, Object[] arg, String group) {
        addMessage(message, arg, MESSAGE_DEFAULT_MESSAGE_TYPE, group);
    }

    @Override
    public void addMessage(String message, Object[] arg, MessageType messageType) {
        addMessage(message, arg, messageType, MESSAGE_DEFAULT_GROUP);
    }

    @Override
    public void addMessage(String message, Object[] arg, MessageType messageType, String group) {
        MessageStore messageStore = getMessageStore();
        String fullMessage = messageSource.getMessage(message, arg, portletService.getLocale());
        messageStore.addMessage(fullMessage, messageType, group);
    }

    @Override
    public MessageList getMessages(MessageType messageType, String group) {
        return getMessageStore().getMessages(messageType, group);
    }

    private MessageStore getMessageStore() {
        MessageStore messageStore;
        Object o = portletService.getPortletRequest().getAttribute(PORTLET_MESSAGE_STORE);
        if (o == null) {
            messageStore = new MessageStore();
            portletService.getPortletRequest().setAttribute(PORTLET_MESSAGE_STORE, messageStore);
        } else {
            if (o instanceof MessageStore) {
                messageStore = (MessageStore) o;
            } else {
                throw new RuntimeException("no MessageStore.");
            }
        }
        return messageStore;
    }
}
