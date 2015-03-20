package ab.liferay.spring.mvc.thymeleaf.angular.core.base.annotation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Controller
@RequestMapping("view")
public @interface ViewController {
    String value() default "";
}
