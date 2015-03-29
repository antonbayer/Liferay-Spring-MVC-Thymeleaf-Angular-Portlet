package ab.liferay.spring.mvc.thymeleaf.angular.core.base.annotation;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PortletRequestBody {
    String DEFAULT = "";

    String value() default DEFAULT;
}
