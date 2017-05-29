package org.syaku.tutorials.spring.apps.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
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
import org.syaku.tutorials.spring.apps.validation.model.FormExt;
import org.syaku.tutorials.spring.apps.validation.support.AppValidationMessage;
import org.syaku.tutorials.spring.boot.Bootstrap;
import org.syaku.tutorials.spring.boot.servlet.ValidationServlet;
import org.syaku.tutorials.spring.handlers.StatusCode;
import org.syaku.tutorials.spring.handlers.SuccessHandler;
import org.syaku.tutorials.spring.validation.ValidationException;
import org.syaku.tutorials.spring.validation.ValidationResult;
import org.syaku.tutorials.spring.validation.ValidationWrapper;
import org.syaku.tutorials.spring.validation.group.Edit;

import javax.validation.Valid;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

	@Autowired
	private MessageSourceAccessor messageSource;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	class Foo {
		@Valid
		private Object object;

		public Foo(Object object) {
			this.object = object;
		}

		public Object getObject() {
			return object;
		}

		public void setObject(Object object) {
			this.object = object;
		}
	}

	@Test
	public void original() {
		Form form = new Form();
		List<FormExt> formExts = new ArrayList<>();
		formExts.add(new FormExt("", "good"));
		form.setFormExts(formExts);
		List<Form> forms = new ArrayList<>();
		forms.add(form);

		ValidationWrapper wrapper = new ValidationWrapper(forms);

		DataBinder binder = new DataBinder(wrapper);
		binder.setValidator(validator);
		binder.validate();
		BindingResult result = binder.getBindingResult();

		if (result.hasErrors()) {
			SuccessHandler successHandler = new SuccessHandler(
					"유효성 검사 오류...",
					true, StatusCode.FormValidation,
					new ValidationResult(
							result,
							new AppValidationMessage(messageSource)
					).getFieldErrors());

			logger.debug("{}", successHandler);
		}
	}

	@Test
	public void async_post_form_write_valid() throws Exception {
		Form form = new Form();
		form.setAge(20);
		form.setBirthday(new Date());
		form.setDate("20170430222222");
		form.setEmail("ss");
		form.setHobby(new String[]{ "1", "2", "3" });
		form.setIdx("AS000000000000000001");
		form.setName("good");
		form.setPassword("1234");
		form.setPassword2("1234");
		form.setPhone("12341234");
		form.setSex("S");
		form.setUserId("ewqewqe");

		form.setFormExts(Collections.EMPTY_LIST);

		ObjectMapper mapper = new ObjectMapper();


		mockMvc.perform(post("/validation/save")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(form))
		)
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void async_post_form_edit_valid() throws Exception {
		Form form = new Form();
		form.setAge(20);
		form.setBirthday(new Date());
		form.setDate("20170430222222");
		form.setEmail("ss");
		form.setHobby(new String[]{ "1", "2", "3" });
		form.setIdx("1");
		form.setName("가나다라마");
		form.setPassword("가나다2321wdqeq");
		form.setPassword2("1ewqewq1231");
		form.setPhone("12341234");
		form.setSex("S");
		form.setUserId("ewqewqe");

		form.setFormExts(Collections.EMPTY_LIST);

		ObjectMapper mapper = new ObjectMapper();


		mockMvc.perform(put("/validation/save")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(form))
		)
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void async_post_forms_edit_valid() throws Exception {
		Form form = new Form();
		form.setAge(20);
		form.setBirthday(new Date());
		form.setDate("20170430222222");
		form.setEmail("ss");
		form.setHobby(new String[]{ "1", "2", "3" });
		form.setIdx("1");
		form.setName("가나다라마");
		form.setPassword("가나다2321wdqeq");
		form.setPassword2("1ewqewq1231");
		form.setPhone("12341234");
		form.setSex("S");
		form.setUserId("ewqewqe");

		form.setFormExts(Collections.EMPTY_LIST);

		List<Form> forms = new ArrayList<>();

		forms.add(form);

		ObjectMapper mapper = new ObjectMapper();


		mockMvc.perform(put("/validation/save/forms")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(forms))
		)
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void async_post_forms2_edit_valid() throws Exception {
		Form form = new Form();
		form.setAge(20);
		form.setBirthday(new Date());
		form.setDate("20170430222222");
		form.setEmail("ss");
		form.setHobby(new String[]{ "1", "2", "3" });
		form.setIdx("1");
		form.setName("가나다라마");
		form.setPassword("가나다2321wdqeq");
		form.setPassword2("1ewqewq1231");
		form.setPhone("12341234");
		form.setSex("S");
		form.setUserId("ewqewqe");

		form.setFormExts(Collections.EMPTY_LIST);

		List<Form> forms = new ArrayList<>();

		forms.add(form);

		ObjectMapper mapper = new ObjectMapper();


		mockMvc.perform(put("/validation/save/forms/test")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(forms))
		)
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void string_byte() throws Exception {
		String text = "a가 나 다!@#$%^&*()_+213";
		byte[] bytes = text.getBytes("utf-8");
		for (int i = 0; i < bytes.length; i++) {
			logger.debug("{}, {}", bytes[i], bytes[i] < 0);
		}
	}

	@Test
	public void regex() {
		String text = "asdsa";
		Pattern pattern = Pattern.compile("([a-z]+)");
		System.out.println(pattern.matcher(text).matches());
	}
}
