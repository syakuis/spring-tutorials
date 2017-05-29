package org.syaku.tutorials.spring.apps.validation.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.syaku.tutorials.spring.apps.validation.model.Form;
import org.syaku.tutorials.spring.apps.validation.support.AppValidationMessage;
import org.syaku.tutorials.spring.handlers.SuccessHandler;
import org.syaku.tutorials.spring.validation.ValidBindingResult;
import org.syaku.tutorials.spring.validation.Validation;
import org.syaku.tutorials.spring.validation.ValidationException;
import org.syaku.tutorials.spring.validation.ValidationResult;

import java.util.List;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 5. 10.
 */
@Controller
@RequestMapping("/validation")
public class FormController {
	private static final Logger logger = LoggerFactory.getLogger(FormController.class);

	@Autowired private MessageSourceAccessor messageSource;

	@GetMapping
	public String dispFromSave() {
		return "validation/form";
	}

	@PostMapping(value = "/save", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String procFormSave(Model model, @Validated Form form, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("errors",
					new ValidationResult(
							bindingResult,
							new AppValidationMessage(messageSource)
					).getFieldErrors());
			return "validation/form";
		}

		return "validation/done";
	}

	@PutMapping(value = "/save")
	@ResponseBody
	public SuccessHandler procFormEdit(@Validated @RequestBody Form form, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			throw new ValidationException(bindingResult);
		}
		return new SuccessHandler("success");
	}

	@PutMapping(value = "/save/forms")
	@ResponseBody
	public SuccessHandler procFormsEdit(@Validation @RequestBody List<Form> forms) {
		return new SuccessHandler("success");
	}

	@PutMapping(value = "/save/forms/test")
	@ResponseBody
	public SuccessHandler procFormsEdit(@Validation @RequestBody List<Form> forms, @ValidBindingResult BindingResult bindingResult) {
		return new SuccessHandler("success");
	}

	@PostMapping(value = "/save")
	@ResponseBody
	public SuccessHandler procFormWrite(@Validated @RequestBody Form form, @ValidBindingResult BindingResult bindingResult) {
		return new SuccessHandler("success");
	}
}
