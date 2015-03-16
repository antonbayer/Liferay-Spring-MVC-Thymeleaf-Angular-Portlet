package ab.liferay.spring.mvc.thymeleaf.angular.portlet.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"ab.liferay.spring.mvc.thymeleaf.angular.portlet.service", "ab.liferay.spring.mvc.thymeleaf.angular.portlet.controller"})
public class PortletConfig { }