package org.syaku.tutorials.spring.apps.validation.support;

import lombok.Data;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 5. 11.
 */
@Data
public class ErrorResult {
	private final String field;
	private final Object value;
	private final String code;
	private final String message;
}
