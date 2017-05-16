package org.syaku.tutorials.spring.apps.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;
import org.springframework.web.context.WebApplicationContext;
import org.syaku.tutorials.spring.apps.validation.model.Form;
import org.syaku.tutorials.spring.boot.Bootstrap;
import org.syaku.tutorials.spring.boot.servlet.ValidationServlet;

import java.util.Collections;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 5. 10.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {
		Bootstrap.class,
		ValidationServlet.class
})
public class FormControllerTest {
	private static final Logger logger = LoggerFactory.getLogger(FormControllerTest.class);

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private Validator validator;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void original() {
		Form form = new Form();
		DataBinder binder = new DataBinder(form);
		binder.setValidator(validator);
		binder.validate();
		BindingResult result = binder.getBindingResult();

		logger.debug("{}", result);
	}

	@Test
	public void get_form() throws Exception {
		mockMvc.perform(get("/validation"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void post_form_valid_error() throws Exception {
		mockMvc.perform(post("/validation/save")
				.param("password", "13213213213123213213")
				.param("password2", "12")
				.param("age", "w")
				.param("name", "1")
				.param("userId", "Ywewqe221321321321321321321321321")
				.param("date", "20170431222222")
				.param("birthday", "1999-12-20")
				.param("sex", "X")
				.param("hobby", "영화", "게임", "게임", "게임")
		)
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void post_form_valid() throws Exception {
		mockMvc.perform(post("/validation/save")
				.param("password", "1234")
				.param("password2", "1234")
				.param("age", "20")
				.param("name", "1")
				.param("userId", "wqe2")
				.param("date", "20170430222222")
				.param("birthday", "1999-12-20")
				.param("sex", "S")
				.param("hobby", "영화", "게임")
		)
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void post_form_valid2() throws Exception {
		Form form = new Form();
		form.setAge(20);
		form.setBirthday(new Date());
		form.setDate("20170430222222");
		form.setEmail("ss");
		form.setHobby(new String[]{ "1", "2", "3" });
		form.setIdx("1");
		form.setName("good");
		form.setPassword("1234");
		form.setPassword2("1234");
		form.setPhone("12341234");
		form.setSex("S");
		form.setUserId("ewqewqe");

		form.setFormExts(Collections.EMPTY_LIST);

		ObjectMapper mapper = new ObjectMapper();


		mockMvc.perform(post("/validation/save2")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(form))
		)
				.andDo(print())
				.andExpect(status().isOk());
	}
}
