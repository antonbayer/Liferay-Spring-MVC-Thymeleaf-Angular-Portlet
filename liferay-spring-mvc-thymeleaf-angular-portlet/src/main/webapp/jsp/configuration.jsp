<!--
Implemented according to liferay manual: https://dev.liferay.com/develop/tutorials/-/knowledge_base/6-2/using-configurable-portlet-preferences
-->
<%@ taglib prefix="liferay-portlet" uri="http://liferay.com/tld/portlet" %>
<%@ taglib prefix="aui" uri="http://liferay.com/tld/aui" %>
<%@ page import="com.liferay.portal.kernel.util.Constants" %>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL"/>

<aui:form action="<%= configurationURL %>" method="post" name="fm">
    <aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>"/>

    <!-- Preference control goes here -->
    <aui:fieldset column="true">

        <!-- import from liferay-spring-mvc-thymeleaf-angular-core copied by maven-war-plugin -->
        <%@include file="portlet_i18n_configuration.jsp" %>

        <aui:button-row>
            <aui:button type="submit"/>
        </aui:button-row>
    </aui:fieldset>
</aui:form>