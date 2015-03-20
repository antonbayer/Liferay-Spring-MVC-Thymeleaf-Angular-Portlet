package ab.liferay.spring.mvc.thymeleaf.angular.portlet.config;

import ab.liferay.spring.mvc.thymeleaf.angular.core.portlet.config.PortletCoreConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(PortletCoreConfig.class)
@ComponentScan(basePackages = {"ab.liferay.spring.mvc.thymeleaf.angular.portlet.service", "ab.liferay.spring.mvc.thymeleaf.angular.portlet.controller"})
public class PortletConfig { }