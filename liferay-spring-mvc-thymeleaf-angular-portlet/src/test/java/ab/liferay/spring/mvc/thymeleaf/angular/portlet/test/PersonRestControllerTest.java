package ab.liferay.spring.mvc.thymeleaf.angular.portlet.test;

import ab.liferay.spring.mvc.thymeleaf.angular.core.service.PortletService;
import ab.liferay.spring.mvc.thymeleaf.angular.core.util.Integration;
import ab.liferay.spring.mvc.thymeleaf.angular.portlet.controller.PersonRestController;
import ab.liferay.spring.mvc.thymeleaf.angular.portlet.model.Person;
import ab.liferay.spring.mvc.thymeleaf.angular.portlet.service.PersonService;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mock.web.portlet.MockRenderResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@Category(Integration.class)
@WebAppConfiguration
@ContextConfiguration(classes = PersonRestControllerTest.Config.class)
public class PersonRestControllerTest {

    @Autowired
    private PersonService personService;
    @Autowired
    protected PortletService portletService;

    @Autowired
    private WebApplicationContext ctx;

    private MockMvc mockMvc;

    private List<Person> persons;

    @Before
    public void setUp() {
        mockMvc = webAppContextSetup(ctx).build();
        Locale.setDefault(Config.LOCALE);

        persons = new ArrayList<Person>();
        persons.add(new Person(1, "TestFirstname1", "TestFirstname1"));
        persons.add(new Person(2, "TestFirstname3", "TestFirstname2"));
        persons.add(new Person(3, "TestFirstname3", "TestFirstname3"));
        when(personService.getPersons()).thenReturn(persons);
    }

    @Test
    public void restGet() throws Exception {

        ResultActions result = mockMvc.perform(get("/test/" + PersonRestController.REST_RESOURCE));
        result.andExpect(status().isOk())
                .andExpect(content().contentType(Integration.JSON_UTF_8_CONTENT_TYPE))
                .andExpect(jsonPath("$", hasSize(personService.getPersons().size())));

        assertThat(result.andReturn().getResponse().getContentAsString()).isNotEmpty();
    }

    @Test
    public void restPost() throws Exception {

        final Person person = new Person(4, "TestFirstname4", "TestFirstname4");

        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                persons.add((Person) invocation.getArguments()[0]);
                return null;
            }
        }).when(personService).addPerson((Person) anyObject());


        ResultActions result = mockMvc.perform(post("/test/" + PersonRestController.REST_RESOURCE)
                .contentType(Integration.JSON_UTF_8_CONTENT_TYPE)
                .content(Integration.convertObjectToJsonBytes(person)));
        result.andExpect(status().isOk())
                .andExpect(status().is(200));

        restGet();
    }

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
        public TestPersonRestController testPersonRestController(PersonService personService, PortletService portletService) {
            return new TestPersonRestController(personService, portletService);
        }
    }
}