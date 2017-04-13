package org.syaku.tutorials.aspectj.xss.web;

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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.syaku.spring.tutorials.aspectj.xss.domain.Foo;
import org.syaku.spring.tutorials.boot.Bootstrap;
import org.syaku.spring.tutorials.boot.servlet.ServletConfiguration;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {
		Bootstrap.class,
		ServletConfiguration.class
})
public class XssContollerTest {
	private static final Logger logger = LoggerFactory.getLogger(XssContollerTest.class);

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}


	@Test
	public void demo() throws Exception {
		logger.debug("demo controller test");
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/aspectj/xss?name=good")).andReturn();

		Foo foo = new Foo();
		foo.setName("good");

		logger.debug("test response ===> {}", result.getResponse().getContentAsString());
	}
}
