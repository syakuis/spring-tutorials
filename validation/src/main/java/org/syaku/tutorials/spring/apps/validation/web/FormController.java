package org.syaku.tutorials.spring.apps.validation.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.syaku.tutorials.spring.apps.validation.model.Form;
import org.syaku.tutorials.spring.apps.validation.support.ValidationBindingResult;
import org.syaku.tutorials.spring.apps.validation.support.policy.Add;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 5. 10.
 */
@Controller
@RequestMapping("/validation")
public class FormController {
	private static final Logger logger = LoggerFactory.getLogger(FormController.class);

	@GetMapping
	public String dispFromSave() {
		return "validation/form";
	}

	@PostMapping(value = "/save", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String procFormSave(Model model, @ModelAttribute("form")  Form form, BindingResult bindingResult) {

		logger.debug(form.toString());

		if (bindingResult.hasErrors()) {
			model.addAttribute("errors", new ValidationBindingResult(bindingResult).getFieldErrors());
			return "validation/form";
		}

		return "validation/done";
	}
}
