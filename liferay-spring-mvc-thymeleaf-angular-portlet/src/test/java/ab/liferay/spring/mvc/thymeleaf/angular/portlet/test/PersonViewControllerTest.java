package ab.liferay.spring.mvc.thymeleaf.angular.portlet.test;

import ab.liferay.spring.mvc.thymeleaf.angular.core.config.ThymeleafConfig;
import ab.liferay.spring.mvc.thymeleaf.angular.core.service.PortletService;
import ab.liferay.spring.mvc.thymeleaf.angular.core.util.Integration;
import ab.liferay.spring.mvc.thymeleaf.angular.portlet.model.Person;
import ab.liferay.spring.mvc.thymeleaf.angular.portlet.service.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.mock.web.portlet.MockRenderResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@Category(Integration.class)
@WebAppConfiguration
@ContextConfiguration(classes = PersonViewControllerTest.Config.class)
public class PersonViewControllerTest {

    @Autowired
    private PersonService personService;
    ;
    @Autowired
    private PortletService portletService;

    @Autowired
    private WebApplicationContext ctx;

    private MockMvc mockMvc;

    private List<Person> persons;

    @Before
    public void setUp() {
        mockMvc = webAppContextSetup(ctx).build();
        Locale.setDefault(Config.LOCALE);

        persons = new ArrayList<Person>();
        persons.add(new Person(1, "TestFirstname1", "TestLastname1"));
        persons.add(new Person(2, "TestFirstname2", "TestLastname2"));
        persons.add(new Person(3, "TestFirstname3", "TestLastname3"));
        when(personService.getPersons()).thenReturn(persons);
    }

    @Test
    public void viewIndex() throws Exception {

//        ResultActions result = mockMvc.perform(get("/test/view").locale(Locale.getDefault()));
//        result.andExpect(status().isOk())
//                .andExpect(view().name("index/index"))
//                .andExpect(model().attributeExists("persons"))
//                .andExpect(model().attributeExists("resourceURL"));
//
//        String contentAsString = result.andReturn().getResponse().getContentAsString();
//        assertThat(contentAsString).isNotEmpty();
//        assertFalse(contentAsString.contains("??"));
//
//        Document parse = Jsoup.parse(contentAsString);
//        Elements elements = parse.select("table#persons > tbody > tr");
//        List<Person> servicePersons = personService.getPersons();
//        assertEquals(elements.size(), servicePersons.size());
//        for (int i = 0; i < elements.size() || i< servicePersons.size(); i++) {
//            Element tr = elements.get(i);
//            Person person = servicePersons.get(i);
//            assertEquals(tr.children().get(0).text(), String.valueOf(person.getId()));
//            assertEquals(tr.children().get(1).text(), person.getFirstname());
//            assertEquals(tr.children().get(2).text(), person.getLastname());
//        }
    }

    @Import(ThymeleafConfig.class)
    @EnableWebMvc
    @Configuration
    public static class Config {

        public static Locale LOCALE = Locale.GERMAN;

        @Bean
        public PersonService personService() {
            PersonService mock = Mockito.mock(PersonService.class);
            return mock;
        }

        @Bean
        public PortletService portletService() {
            PortletService mock = Mockito.mock(PortletService.class);
            when(mock.getRenderResponse()).thenReturn(new MockRenderResponse());
            when(mock.getLocale()).thenReturn(LOCALE);
            return mock;
        }

        @Bean
        public MessageSource messageSource(final PortletService portletService) {

            ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
            messageSource.setBasename("content/language");
            messageSource.setDefaultEncoding(StandardCharsets.UTF_8.toString());
            return messageSource;
        }

        @Bean
        public TestPersonViewController testPersonViewController(PersonService personService, PortletService portletService) {
            return new TestPersonViewController(personService, portletService);
        }
    }
}