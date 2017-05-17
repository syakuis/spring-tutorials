package org.syaku.tutorials.spring.validation;

import org.springframework.validation.FieldError;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 5. 16.
 */
public interface ValidationMessage {
	/**
	 * 필드명
	 * @param error
	 * @return
	 */
	String getFieldName(FieldError error);

	/**
	 * 데이터 바인딩시 실패한 경우 메세지
	 * @param error
	 * @return
	 */
	String getBindingFailure(FieldError error);
}
