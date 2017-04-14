package org.syaku.spring.tutorials.aspectj.xss.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.syaku.spring.tutorials.aspectj.xss.domain.Foo;
import org.syaku.spring.tutorials.aspectj.xss.support.XssValid;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 13.
 */
@Controller
@RequestMapping("/aspectj/xss")
public class XssContoller {
	private static final Logger logger = LoggerFactory.getLogger(XssContoller.class);

	@GetMapping(value = "")
	@ResponseBody
	public Foo demo(@XssValid Foo foo, Model model) {
		System.out.println(foo.toString());
		return foo;
	}
}
