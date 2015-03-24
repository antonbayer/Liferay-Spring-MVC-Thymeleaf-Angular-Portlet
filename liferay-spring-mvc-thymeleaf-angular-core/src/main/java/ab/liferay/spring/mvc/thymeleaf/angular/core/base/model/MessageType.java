package ab.liferay.spring.mvc.thymeleaf.angular.core.base.model;

import java.text.MessageFormat;

public enum MessageType {

    ERROR("error"), INFO("info"), WARNING("warning");

    private final String name;

    MessageType(String name) {
        this.name = name;
    }

    public static MessageType getMessageTypeByName(String name) {
        for (MessageType messageType : MessageType.values()) {
            if (messageType.getName().equals(name)) {
                return messageType;
            }
        }
        throw new RuntimeException(MessageFormat.format("MessageType {0} not found.", name));
    }

    public String getName() {
        return name;
    }
}