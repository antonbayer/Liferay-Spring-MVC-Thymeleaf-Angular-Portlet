package ab.liferay.spring.mvc.thymeleaf.angular.core.base.service;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

public interface RenderService {

    String renderTemplate(ModelAndView modelAndView);

    String renderTemplate(String viewName, ModelMap modelMap);

    String renderTemplate(String s);
}