package ab.liferay.spring.mvc.thymeleaf.angular.core.base.service;

import ab.liferay.spring.mvc.thymeleaf.angular.core.base.component.request.MessageStore;
import ab.liferay.spring.mvc.thymeleaf.angular.core.base.model.MessageList;
import ab.liferay.spring.mvc.thymeleaf.angular.core.base.model.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class MessageServiceImpl implements MessageService {

    private final PortletService portletService;
    private final MessageSource messageSource;
    private MessageStore messageStore;

    @Autowired
    public MessageServiceImpl(PortletService portletService, MessageSource messageSource, MessageStore messageStore) {
        this.portletService = portletService;
        this.messageSource = messageSource;
        this.messageStore = messageStore;
    }

    @Override
    public void addRequestMessage(String message) {
        addRequestMessage(message, null, MESSAGE_DEFAULT_MESSAGE_TYPE, MESSAGE_DEFAULT_GROUP);
    }

    @Override
    public void addRequestMessage(String message, String group) {
        addRequestMessage(message, null, MESSAGE_DEFAULT_MESSAGE_TYPE, group);
    }

    @Override
    public void addRequestMessage(String message, MessageType messageType) {
        addRequestMessage(message, null, messageType, MESSAGE_DEFAULT_GROUP);
    }

    @Override
    public void addRequestMessage(String message, MessageType messageType, String group) {
        addRequestMessage(message, null, messageType, group);
    }

    @Override
    public void addRequestMessage(String message, Object[] arg) {
        addRequestMessage(message, arg, MESSAGE_DEFAULT_MESSAGE_TYPE, MESSAGE_DEFAULT_GROUP);
    }

    @Override
    public void addRequestMessage(String message, Object[] arg, String group) {
        addRequestMessage(message, arg, MESSAGE_DEFAULT_MESSAGE_TYPE, group);
    }

    @Override
    public void addRequestMessage(String message, Object[] arg, MessageType messageType) {
        addRequestMessage(message, arg, messageType, MESSAGE_DEFAULT_GROUP);
    }

    @Override
    public void addRequestMessage(String message, Object[] arg, MessageType messageType, String group) {
        String fullMessage = messageSource.getMessage(message, arg, portletService.getLocale());
        messageStore.addMessage(fullMessage, messageType, group);
    }

    @Override
    public MessageList getRequestMessages(MessageType messageType, String group) {
        return messageStore.getMessages(messageType, group);
    }

    @Override
    public String getMessage(String message) {
        return getMessage(message, null);
    }

    @Override
    public String getMessage(String message, Object[] arg) {
        return getMessage(message, arg, portletService.getLocale());
    }

    @Override
    public String getMessage(String message, Object[] arg, Locale locale) {
        return messageSource.getMessage(message, arg, locale);
    }
}