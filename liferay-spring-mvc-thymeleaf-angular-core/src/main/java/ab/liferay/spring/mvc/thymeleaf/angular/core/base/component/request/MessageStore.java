package ab.liferay.spring.mvc.thymeleaf.angular.core.base.component.request;

import ab.liferay.spring.mvc.thymeleaf.angular.core.base.model.MessageList;
import ab.liferay.spring.mvc.thymeleaf.angular.core.base.model.MessageType;
import ab.liferay.spring.mvc.thymeleaf.angular.core.base.model.MessageTypeList;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.List;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MessageStore extends HashMap<String, MessageTypeList> {

    public void addMessage(String message, MessageType messageType, String group) {

        MessageTypeList groupList = get(group);
        if (groupList == null) {
            put(group, new MessageTypeList());
            groupList = get(group);
        }

        List<String> messageTypeList = groupList.get(messageType);
        if (messageTypeList == null) {
            groupList.put(messageType, new MessageList());
            messageTypeList = groupList.get(messageType);
        }
        messageTypeList.add(message);
    }

    public MessageList getMessages(MessageType messageType, String group) {
        MessageTypeList groupList = get(group);
        if (groupList == null) {
            return null;
        }
        return groupList.get(messageType);
    }
}