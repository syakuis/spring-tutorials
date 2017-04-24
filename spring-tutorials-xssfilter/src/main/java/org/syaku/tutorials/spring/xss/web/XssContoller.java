package org.syaku.tutorials.spring.xss.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.syaku.tutorials.spring.xss.domain.Foo;
import org.syaku.tutorials.spring.xss.support.XssClean;

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
	public Foo demo(@XssClean Foo foo, Model model,  @RequestParam(value = "text", required = false) @XssClean String text) {
		logger.debug("Controller text {}", text);
		logger.debug("Controller foo {}", foo.getEscape());
		return foo;
	}
}
