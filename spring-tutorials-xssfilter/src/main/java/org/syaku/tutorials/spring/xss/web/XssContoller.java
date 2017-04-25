package org.syaku.tutorials.spring.xss.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.syaku.tutorials.spring.xss.domain.Foo;
import org.syaku.tutorials.spring.xss.support.Defence;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 13.
 */
@Controller
@RequestMapping("/aspectj/xss")
public class XssContoller {
	private static final Logger logger = LoggerFactory.getLogger(XssContoller.class);

	@GetMapping(value = "/{module_id}")
	@ResponseBody
	public Foo demo(
			//@PathVariable("module_id") String module_id,
			@Defence Foo foo, Model model,
			@RequestParam(value = "text", required = false)
			@Defence String text) {
		logger.debug("Controller text {}", text);
		logger.debug("Controller foo {}", foo.getEscape());
		return foo;
	}
}
