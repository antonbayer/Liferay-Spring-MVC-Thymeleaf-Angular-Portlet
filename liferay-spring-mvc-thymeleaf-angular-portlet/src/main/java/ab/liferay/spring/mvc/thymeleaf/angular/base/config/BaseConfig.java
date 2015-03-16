package ab.liferay.spring.mvc.thymeleaf.angular.base.config;

import ab.liferay.spring.mvc.thymeleaf.angular.core.config.CoreConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(CoreConfig.class)
public class BaseConfig { }