package ab.liferay.spring.mvc.thymeleaf.angular.core.base.service;

import ab.liferay.spring.mvc.thymeleaf.angular.core.base.model.MessageList;
import ab.liferay.spring.mvc.thymeleaf.angular.core.base.model.MessageType;

public interface MessageService {

    String MESSAGE_DEFAULT_GROUP = "global";
    MessageType MESSAGE_DEFAULT_MESSAGE_TYPE = MessageType.ERROR;

    void addMessage(String message);

    void addMessage(String message, String group);

    void addMessage(String message, MessageType messageType);

    void addMessage(String message, MessageType messageType, String group);

    void addMessage(String message, Object[] arg);

    void addMessage(String message, Object[] arg, String group);

    void addMessage(String message, Object[] arg, MessageType messageType);

    void addMessage(String message, Object[] arg, MessageType messageType, String group);

    MessageList getMessages(MessageType messageType, String group);
}
