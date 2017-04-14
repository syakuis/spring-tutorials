package org.syaku.spring.tutorials.aspectj.xss.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.syaku.spring.tutorials.aspectj.xss.domain.Foo;
import org.syaku.spring.tutorials.aspectj.xss.sevice.XssService;
import org.syaku.spring.tutorials.aspectj.xss.support.XssFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 13.
 */
@Controller
@RequestMapping("/aspectj/xss")
public class XssContoller {
	private static final Logger logger = LoggerFactory.getLogger(XssContoller.class);

	@Autowired
	private XssService xssService;

	@GetMapping(value = "")
	public String demo(@XssFilter Foo foo, HttpServletRequest httpServletRequest, HttpServletResponse response, Model model) {
		logger.debug("controller foo ====> {}", foo.toString());
		logger.debug(model.toString());
		xssService.test();

		model.addAttribute("name", foo.getName());
		return "/aspectj/xss/demo.view";
	}
}
