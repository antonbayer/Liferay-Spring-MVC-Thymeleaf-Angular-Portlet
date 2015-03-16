# Liferay - Spring MVC - Thymeleaf - Angular - Portlet

After fighting with several tutorials which have not fully filled my demands I decided to implement my Liferay Spring MVC Portlet from scatch with all the feature i was used to have with a standalone Spring MVC application:

 * RestController for AngularJS
 * ViewController with Thymeleaf
 * @ResponseBody and @RequestBody which are not supported by spring for portlets
 * full support for integration tests with MockMVC

The project consists of 3 Maven dependencies:

 * -parent: Does what a parent Maven dependency has to do
 * -core: The basic/generic functionality and configuration you probably need in every portlet
 * -portlet: A Demo portlet that renders a list of persons with Thymeleaf on server-side and AngularJS via Rest on client-side.
 
I solved the support of @ResponseBody and @RequestBody for portlets by implementing my own @PortletResponseBody and @PortletRequestBody.

### Build, run tests and install the Portlet

Run the maven command to install the portlet to your liferay installation.

```
mvn clean install liferay:deploy -P liferay
```

Do not forget to configure the params in Maven.  In my case I set the profile 'liferay' for executing the tasks which is defined in <user_home>/.m2/settings.xml:

```xml
<profile>
	<id>liferay</id>
	<properties>
		<liferay.version>6.2.10.6</liferay.version>
		<liferay.home>C:\<your_path>\liferay-portal-6.2-ee-sp5-clean</liferay.home>
		<liferay.auto.deploy.dir>${liferay.home}\deploy</liferay.auto.deploy.dir>
		<liferay.app.server.deploy.dir>${liferay.home}\tomcat-7.0.42\webapps</liferay.app.server.deploy.dir>
		<liferay.app.server.lib.global.dir>${liferay.home}\tomcat-7.0.42\lib\ext</liferay.app.server.lib.global.dir>
		<liferay.app.server.portal.dir>${liferay.home}\tomcat-7.0.42\webapps\ROOT</liferay.app.server.portal.dir>
	</properties>
</profile>
```

Notes:

 * The porlet is tested with Java 1.7.
 * The first time you want to add the portet to a page you get a ADD_TO_PAGE NoSuchResourceActionException.
 
### Improvements?

Send me an [Email](mailto:wwa2007@nurfuerspam.de) or send me a merge request. :)

Cheers from Vienna, Austria

