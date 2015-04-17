<%@ page import="ab.liferay.spring.mvc.thymeleaf.angular.core.base.service.I18nMessageConstants" %>
<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %>
<%@ page import="com.liferay.portal.kernel.util.StringPool" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ResourceBundle" %>
<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet" %>
<%@ taglib prefix="aui" uri="http://liferay.com/tld/aui" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="theme" %>
<%@ taglib prefix="liferay-ui" uri="http://liferay.com/tld/ui" %>
<theme:defineObjects/>
<portlet:defineObjects/>
<liferay-ui:panel-container extended="<%= true %>" id="infoButtonConfig" persistState="<%= true %>">
    <liferay-ui:panel collapsible="<%= true %>" extended="<%= true %>" id="infoButtonPanel" persistState="<%= true %>"
                      title="i18n-configuration-title">

        <%
            String template = "";
            ResourceBundle resourceBundle = ResourceBundle.getBundle("content/language_template");
            for (String key : resourceBundle.keySet()) {
                template += key + "=" + System.getProperty("line.separator");
            }
        %>
        <div>
            <div>Template</div>
            <div><textarea rows="10" style="width: 100%;" readonly="readonly"><%= template %></textarea></div>
        </div>
        <%
            for (Locale supportedLocale : LanguageUtil.getAvailableLocales()) {
                String key = I18nMessageConstants.CONFIGURATION_LANGUAGE_PREFIX + supportedLocale.toString();
                String preference = "preferences" + StringPool.DOUBLE_DASH + key + StringPool.DOUBLE_DASH;
                String language = portletPreferences.getValue(key, StringPool.BLANK);
        %>
        <div>
            <div><aui:input type="textarea" rows="10" style="width: 100%;" name="<%= preference %>"
                            value="<%= language %>"/>
            </div>
        </div>
        <%
            }
            String preference = "preferences" + StringPool.DOUBLE_DASH + I18nMessageConstants.CONFIGURATION_LANGUAGE_TO_UPDATE + StringPool.DOUBLE_DASH;
            String value = String.valueOf(true);
        %>
        <aui:input type="hidden" name="<%= preference %>" value="<%= value%>"/>

    </liferay-ui:panel>
</liferay-ui:panel-container>