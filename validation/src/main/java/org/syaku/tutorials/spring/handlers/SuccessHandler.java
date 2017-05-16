package org.syaku.tutorials.spring.handlers;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Controller 처리가 완료되었을 때 응답하기 위한 클래스
 *
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2016. 12. 16.
 */
@AllArgsConstructor
public class SuccessHandler<T> {
	@Getter
	private final String message;
	@Getter
	private final boolean error;
	@Getter
	private final StatusCode statusCode;
	@Getter
	private T content;

	public SuccessHandler(String message) {
		this(message, false, StatusCode.OK);
	}

	public SuccessHandler(boolean error) {
		this("", error, StatusCode.OK);
	}

	public SuccessHandler(String message, boolean error) {
		this(message, error, StatusCode.OK);
	}

	public SuccessHandler(String message, boolean error, StatusCode statusCode) {
		this.message = message;
		this.error = error;
		this.statusCode = statusCode;
	}

	public int getCode() {
		return statusCode.getCode();
	}
}
