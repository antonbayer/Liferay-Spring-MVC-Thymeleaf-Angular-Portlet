package ab.liferay.spring.mvc.thymeleaf.angular.core.annotation;

import java.lang.annotation.*;

/**
 * Created by abayer on 17.02.2015.
 */
    @Target(ElementType.PARAMETER)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface PortletRequestBody {
}
