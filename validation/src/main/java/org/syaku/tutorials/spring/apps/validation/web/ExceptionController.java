package org.syaku.tutorials.spring.apps.validation.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.syaku.tutorials.spring.apps.validation.support.*;

import java.util.List;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 5. 16.
 */
@ControllerAdvice
public class ExceptionController {
	@Autowired private MessageSourceAccessor messageSource;

	@ExceptionHandler(ValidationException.class)
	@ResponseBody
	public SuccessHandler<List<ErrorResult>> validationException(ValidationException e) {
		return new SuccessHandler("유효성 검사 오류...", true, StatusCode.FormValidation, new ValidationBindingResult(e.getBindingResult(), messageSource).getFieldErrors());
	}
}
