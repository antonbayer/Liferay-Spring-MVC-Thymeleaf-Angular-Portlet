package ab.liferay.spring.mvc.thymeleaf.angular.portlet.controller;

import ab.liferay.spring.mvc.thymeleaf.angular.core.base.annotation.PortletRequestBody;
import ab.liferay.spring.mvc.thymeleaf.angular.core.base.annotation.PortletResponseBody;
import ab.liferay.spring.mvc.thymeleaf.angular.core.base.annotation.ViewController;
import ab.liferay.spring.mvc.thymeleaf.angular.core.base.service.RenderService;
import ab.liferay.spring.mvc.thymeleaf.angular.portlet.model.Person;
import ab.liferay.spring.mvc.thymeleaf.angular.portlet.service.PersonService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ViewController
public class PersonRestController {

    public static final String REST_RESOURCE = "rest";
    public static final String HTML_JSON_RESOURCE = "htmljson";
    private static Log _log = LogFactoryUtil.getLog(PersonRestController.class);
    private final RenderService renderService;
    private final PersonService personService;

    @Autowired
    public PersonRestController(PersonService personService, RenderService renderService) {
        this.personService = personService;
        this.renderService = renderService;
    }

    @ResourceMapping(value = REST_RESOURCE)
    @RequestMapping(method = RequestMethod.GET)
    @PortletResponseBody
    public List<Person> rest() {

        return personService.getPersons();
    }

    @ResourceMapping(REST_RESOURCE)
    @RequestMapping(method = RequestMethod.POST)
    public void rest(@PortletRequestBody Person person) {

        personService.addPerson(person);
    }

    @ResourceMapping(HTML_JSON_RESOURCE)
    @RequestMapping(method = RequestMethod.GET)
    @PortletResponseBody
    public Map<String, String> html() {

        Map<String, String> jsonMap = new HashMap<String, String>();
        List<Person> persons = personService.getPersons();

        ModelMap modelMap = new ModelMap();
        modelMap.put("persons", persons);
        String html = renderService.renderTemplate("index/index :: persons", modelMap);
        jsonMap.put("persons", html);

        modelMap = new ModelMap();
        modelMap.put("count", persons.size());
        html = renderService.renderTemplate("index/index :: count", modelMap);
        jsonMap.put("count", html);

        return jsonMap;
    }

    //    GET with querystring param: ...&person1={"firstname": "test-1-1"}&person2={"firstname": "test-1-2"} -> Working
//    @ResourceMapping("get-json-1")
//    @RequestMapping(method = RequestMethod.GET)
//    public void test1(@PortletRequestBody("person1") Person p1, @PortletRequestBody("person2") Person p2) {
//        System.out.println(p1.getFirstname() + " " + p2.getFirstname());
//        return;
//    }

    //    POST with body: {"person1": {"firstname": "test-2-1"}, "person2": {"firstname": "test-2-2"}} -> Working
//    @ResourceMapping("post-json-2")
//    @RequestMapping(method = RequestMethod.POST)
//    public void test2(@PortletRequestBody("person1") Person p1, @PortletRequestBody("person2") Person p2) {
//        System.out.println(p1.getFirstname() + " " + p2.getFirstname());
//        return;
//    }

    //     POST with body: {"firstname": "test3"} -> Working
//    @ResourceMapping("post-json-3")
//    @RequestMapping(method = RequestMethod.POST)
//    public void test3(@PortletRequestBody Person p) {
//        System.out.println(p.getFirstname());
//        return;
//    }
}
