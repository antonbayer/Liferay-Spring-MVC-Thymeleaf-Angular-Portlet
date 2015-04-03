package ab.liferay.spring.mvc.thymeleaf.angular.core.base.service;

import ab.liferay.spring.mvc.thymeleaf.angular.core.base.model.MessageList;
import ab.liferay.spring.mvc.thymeleaf.angular.core.base.model.MessageType;

import java.util.Locale;

public interface MessageService {

    String MESSAGE_DEFAULT_GROUP = "global";
    MessageType MESSAGE_DEFAULT_MESSAGE_TYPE = MessageType.ERROR;

    void addRequestMessage(String message);

    void addRequestMessage(String message, String group);

    void addRequestMessage(String message, MessageType messageType);

    void addRequestMessage(String message, MessageType messageType, String group);

    void addRequestMessage(String message, Object[] arg);

    void addRequestMessage(String message, Object[] arg, String group);

    void addRequestMessage(String message, Object[] arg, MessageType messageType);

    void addRequestMessage(String message, Object[] arg, MessageType messageType, String group);

    MessageList getRequestMessages(MessageType messageType, String group);

    String getMessage(String message);

    String getMessage(String message, Object[] arg);

    String getMessage(String message, Object[] arg, Locale locale);
}
