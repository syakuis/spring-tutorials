package org.syaku.tutorials.spring.apps.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.syaku.tutorials.spring.apps.validation.support.policy.Edit;
import org.syaku.tutorials.spring.boot.Bootstrap;
import org.syaku.tutorials.spring.boot.servlet.ServletConfiguration;

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
		ServletConfiguration.class
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
		logger.debug(result.toString());
	}

	@Test
	public void dispFormSave() throws Exception {
		mockMvc.perform(get("/validation"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void prodFormSave() throws Exception {
		mockMvc.perform(post("/validation/save").param("age", ""))
				.andDo(print())
				.andExpect(status().isOk());
	}
}
