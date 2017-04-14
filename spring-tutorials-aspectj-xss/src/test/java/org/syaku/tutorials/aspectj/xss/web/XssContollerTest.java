package org.syaku.tutorials.aspectj.xss.web;

import com.nhncorp.lucy.security.xss.XssPreventer;
import org.jmock.lib.concurrent.Blitzer;
import org.junit.After;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.syaku.spring.tutorials.aspectj.xss.domain.Foo;
import org.syaku.spring.tutorials.aspectj.xss.sevice.XssService;
import org.syaku.spring.tutorials.boot.Bootstrap;
import org.syaku.spring.tutorials.boot.servlet.ServletConfiguration;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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
	private AtomicInteger c;
	Blitzer blitzer = new Blitzer(200, 5);

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private XssService xssService;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		c = new AtomicInteger(0);
	}

	@After
	public void tearDown() throws InterruptedException {
		blitzer.shutdown();
	}

	int count = 0;

	private synchronized int counting() {
		return count++;
	}
	@Test
	public void demo() throws Exception {
		blitzer.blitz(new Runnable() {
			public void run() {
				c.getAndIncrement();
				try {
					int count = counting();
					String name = Thread.currentThread().getName();
					String filter = "\"><script>alert('xss_"+ count +"');</script>";

					logger.debug("run {},{}", name, count);
					MvcResult result = mockMvc.perform(
							get("/aspectj/xss?filter=" + filter + "&noFilter=" + filter + "&name=" + name + "&count=" + count))
							.andExpect(status().isOk())
							.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
							.andExpect(jsonPath("$.filter", is(XssPreventer.escape(filter).toString())))
							.andExpect(jsonPath("$.noFilter", is(filter)))
							.andExpect(jsonPath("$.name", is(name)))
							.andExpect(jsonPath("$.count", is(count)))
							.andReturn();

					logger.debug("{} ==> test response ===> {}", name, result.getResponse().getContentAsString());
				} catch (Exception e) {
					logger.debug(e.getMessage());
				}

			}
		});
	}
}
