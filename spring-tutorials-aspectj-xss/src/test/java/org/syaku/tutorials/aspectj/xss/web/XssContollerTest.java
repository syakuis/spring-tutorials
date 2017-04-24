package org.syaku.tutorials.aspectj.xss.web;

import com.nhncorp.lucy.security.xss.XssFilter;
import com.nhncorp.lucy.security.xss.XssPreventer;
import com.nhncorp.lucy.security.xss.XssSaxFilter;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.syaku.spring.tutorials.boot.Bootstrap;
import org.syaku.spring.tutorials.boot.servlet.ServletConfiguration;

import java.util.concurrent.atomic.AtomicInteger;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
	Blitzer blitzer = new Blitzer(1, 5);

	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private XssSaxFilter xssSaxFilter;

	@Autowired
	private XssFilter xssFilter;

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
					String escape = "\"><script>alert('xss_"+ count +"');</script>";
					String filter = "<img src=\"<img src=1\\ onerror=alert(1234)>\" onerror=\"alert('XSS')\">";
					String saxFilter = "<TABLE class=\"NHN_Layout_Main\" style=\"TABLE-LAYOUT: fixed\" cellSpacing=\"0\" cellPadding=\"0\" width=\"743\">" + "</TABLE>" + "<SPAN style=\"COLOR: #66cc99\"></SPAN>";

					MvcResult result = mockMvc.perform(
							get("/aspectj/xss?text=" + escape + "&filter=" + filter + "&escape=" + escape + "&name=" + name + "&count=" + count + "&saxFilter=" + saxFilter))
							.andExpect(status().isOk())
							.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
							//.andExpect(jsonPath("$.filter", is(XssPreventer.escape(filter).toString())))
							//.andExpect(jsonPath("$.noFilter", is(filter)))
							//.andExpect(jsonPath("$.name", is(name)))
							//.andExpect(jsonPath("$.count", is(count)))
							.andReturn();

					logger.debug("{} ==> test response {} ===> {}", name, result.getResponse().getStatus(), result.getResponse().getContentAsString());

					logger.debug("name {}", name);
					logger.debug("escape {}", XssPreventer.escape(escape));
					logger.debug("filter {}", xssFilter.doFilter(filter));
					logger.debug("saxFilter {}", xssSaxFilter.doFilter(saxFilter));
				} catch (Exception e) {
					logger.debug(e.getMessage(), e);
				}

			}
		});
	}
}
