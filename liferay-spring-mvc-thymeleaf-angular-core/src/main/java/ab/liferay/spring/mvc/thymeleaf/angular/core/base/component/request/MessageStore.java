package ab.liferay.spring.mvc.thymeleaf.angular.core.base.component.request;

import ab.liferay.spring.mvc.thymeleaf.angular.core.base.model.MessageList;
import ab.liferay.spring.mvc.thymeleaf.angular.core.base.model.MessageType;
import ab.liferay.spring.mvc.thymeleaf.angular.core.base.model.MessageTypeList;
import ab.liferay.spring.mvc.thymeleaf.angular.core.base.service.PortletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import javax.portlet.PortletSession;
import java.util.HashMap;
import java.util.List;

@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MessageStore extends HashMap<String, MessageTypeList> {

    private final PortletService portletService;
    private String FLASH_SESSION_MESSAGE_STORE = "FLASH_SESSION_MESSAGE_STORE";

    @Autowired
    public MessageStore(PortletService portletService) {
        this.portletService = portletService;
    }

    @PostConstruct
    public void postContruct() {
        Object obj = this.portletService.getPortletSession().getAttribute(FLASH_SESSION_MESSAGE_STORE, PortletSession.PORTLET_SCOPE);
        if (obj != null && obj instanceof MessageStore) {
            MessageStore flashMessageStore = (MessageStore) obj;
            for (String key : flashMessageStore.keySet()) {
                put(key, flashMessageStore.get(key));
            }
        }
        this.portletService.getPortletSession().removeAttribute(FLASH_SESSION_MESSAGE_STORE, PortletSession.PORTLET_SCOPE);
    }

    public void addRequestMessage(String message, MessageType messageType, String group) {
        addMessage(this, message, messageType, group);
    }

    public void addFlashMessage(String message, MessageType messageType, String group) {
        addMessage(getFlashMessageStore(), message, messageType, group);
    }

    public MessageList getMessages(MessageType messageType, String group) {
        MessageTypeList groupList = get(group);
        if (groupList == null) {
            return null;
        }
        return groupList.get(messageType);
    }

    private void addMessage(MessageStore messageStore, String message, MessageType messageType, String group) {

        MessageTypeList groupList = messageStore.get(group);
        if (groupList == null) {
            messageStore.put(group, new MessageTypeList());
            groupList = messageStore.get(group);
        }

        List<String> messageTypeList = groupList.get(messageType);
        if (messageTypeList == null) {
            groupList.put(messageType, new MessageList());
            messageTypeList = groupList.get(messageType);
        }
        messageTypeList.add(message);
    }

    private MessageStore getFlashMessageStore() {
        Object obj = this.portletService.getPortletSession().getAttribute(FLASH_SESSION_MESSAGE_STORE, PortletSession.PORTLET_SCOPE);
        if (obj != null && obj instanceof MessageStore) {
            return (MessageStore) obj;
        }
        this.portletService.getPortletSession().setAttribute(FLASH_SESSION_MESSAGE_STORE, new MessageStore(portletService), PortletSession.PORTLET_SCOPE);
        return getFlashMessageStore();
    }
}