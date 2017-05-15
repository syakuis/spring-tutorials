package org.syaku.tutorials.spring.apps.validation.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.*;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 5. 10.
 */
public final class ValidationBindingResult {
	private static final Logger logger = LoggerFactory.getLogger(ValidationBindingResult.class);

	private List<ErrorResult> fieldErrors;
	private Map<String, ErrorResult> fieldErrorMap;

	public ValidationBindingResult(BindingResult bindingResult, MessageSource messageSource) {
		Assert.isTrue(bindingResult.hasErrors(), "validation check is not error.");

		List<ErrorResult> fieldErrors = new ArrayList<>();
		Map<String, ErrorResult> fieldErrorMap = new HashMap<>();

		for (FieldError error : bindingResult.getFieldErrors()) {
			String field = error.getField();

			// 폼에서 전송된 데이터 타입이 바인딩과정에 일치하지 않을 경우 오류 메세지가 기록된다. 이를 알기쉽게 변경하였다.
			String message = error.isBindingFailure() ? messageSource.getMessage("text.valid.Invalid", null, Locale.getDefault()) : error.getDefaultMessage();
			ErrorResult result = new ErrorResult(
					field,
					error.getRejectedValue(),
					error.getCode(),
					message
			);

			fieldErrors.add(result);

			if (field != null) {
				fieldErrorMap.put(field, result);
			}

			logger.error(">< >< field : {}, value: {}, code: {}, codes: {}, bindingFailure: {}, message: {}",
					error.getField(),
					error.getRejectedValue(),
					error.getCode(),
					error.getCodes(),
					error.isBindingFailure(),
					error.getDefaultMessage()
			);
		}

		this.fieldErrors = fieldErrors;
		this.fieldErrorMap = fieldErrorMap;

	}

	public List<ErrorResult> getFieldErrors() {
		return fieldErrors;
	}

	public Map<String, ErrorResult> getFieldErrorMap() {
		return fieldErrorMap;
	}
}
