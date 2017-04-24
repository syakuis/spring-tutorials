package org.syaku.tutorials.spring.helloworld.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 13.
 */
@Controller
@RequestMapping("/helloworld")
public class HelloWorldController {

	@GetMapping
	public String helloworld() {
		return "helloworld/helloworld";
	}
}
