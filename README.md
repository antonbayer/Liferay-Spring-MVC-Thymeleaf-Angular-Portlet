# Liferay - Spring MVC - Thymeleaf - Angular - Portlet

After playing around with several tutorials which have not fully filled my demands I decided to implement my Liferay Spring MVC Portlet from scratch with all the features I was used to have in a standalone Spring MVC application:

 * RestController for AngularJS.
 * ViewController with Thymeleaf and partial fragment ajax updates via jQuery.
 * @ResponseBody and @RequestBody which are not supported by spring for portlets out of the box.
 * Full support for integration tests with MockMVC.
 * 100% Java Config and no xml-file at all.
 * Custom Thymeleaf src-attribute for easy use in Portlets to get static resources: <img sc:src="images/image.jpg" />

The project consists of 3 Maven dependencies:

 * -parent: Does what a parent Maven dependency has to do.
 * -core: The basic/generic functionality and configuration you probably need in every portlet.
 * -portlet: A demo portlet that renders a list of persons with Thymeleaf on server-side and AngularJS via Rest on client-side.
 
I solved the support of @ResponseBody and @RequestBody for portlets by implementing my own @PortletResponseBody and @PortletRequestBody.

To be able to do integration tests with MockMVC I wrapped the portlet controller into a new controller and override the RequestMapping. That works like a charm.

### Build, run tests and install the Portlet

Run the maven command to install the portlet to your liferay installation.

```
mvn clean install liferay:deploy -P liferay
```

Do not forget to configure the liferay params in Maven. In my case I set the profile 'liferay' for executing the tasks which is defined in my user_home\\.m2\settings.xml:

```xml
<profile>
	<id>liferay</id>
	<properties>
		<liferay.version>6.2.10.6</liferay.version>
		<liferay.home>your_path\liferay-portal</liferay.home>
		<liferay.auto.deploy.dir>${liferay.home}\deploy</liferay.auto.deploy.dir>
		<liferay.app.server.deploy.dir>${liferay.home}\tomcat-7.0.42\webapps</liferay.app.server.deploy.dir>
		<liferay.app.server.lib.global.dir>${liferay.home}\tomcat-7.0.42\lib\ext</liferay.app.server.lib.global.dir>
		<liferay.app.server.portal.dir>${liferay.home}\tomcat-7.0.42\webapps\ROOT</liferay.app.server.portal.dir>
	</properties>
</profile>
```

Notes:

 * The portlet is tested with Java 1.7 and liferay-portal-6.2-ee-sp5.
 * Developed with IntellijIDEA Ultimate 14.
 * All used Maven dependencies are in the latest version (03/17/15).
 * The first time you add the portlet to a page you get a ADD_TO_PAGE NoSuchResourceActionException. All offered solutions in the web were not useful at all. Any idea?
 
### Improvements?

If you have any idea for improvments send me an [Email](mailto:wwa2007@nurfuerspam.de) or send me a merge request.

If you like it, feel free to give me a star and watch out for further updates. ;)

Cheers from Vienna

Toni :)

