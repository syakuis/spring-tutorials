package org.syaku.spring.tutorials.aspectj.xss.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.syaku.spring.tutorials.aspectj.xss.domain.Foo;
import org.syaku.spring.tutorials.aspectj.xss.support.XssFilter;

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
	public String demo(Model model, @XssFilter Foo foo) {
		logger.debug("controller foo ====> {}", foo.toString());
		logger.debug(model.toString());
		return "/aspectj/xss/demo.view";
	}
}
