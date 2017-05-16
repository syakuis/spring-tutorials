package org.syaku.tutorials.spring.apps.validation.support;

import lombok.Getter;
import org.springframework.validation.BindingResult;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 5. 16.
 */
public class ValidationException extends RuntimeException {

	@Getter
	private final BindingResult bindingResult;

	public ValidationException(BindingResult bindingResult) {
		this.bindingResult = bindingResult;
	}

	public ValidationException(String message, BindingResult bindingResult) {
		super(message);
		this.bindingResult = bindingResult;
	}

	public ValidationException(String message, Throwable cause, BindingResult bindingResult) {
		super(message, cause);
		this.bindingResult = bindingResult;
	}
}
