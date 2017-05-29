package org.syaku.tutorials.spring.validation;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.Valid;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 5. 29.
 */
@Data
@AllArgsConstructor
public class ValidationWrapper {
	@Valid
	private Object object;

	public ValidationWrapper() {
	}
}
