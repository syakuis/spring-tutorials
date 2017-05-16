package org.syaku.tutorials.spring.validation;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 5. 16.
 */
public interface ValidationMessage {
	/**
	 * 필드명
	 * @param field
	 * @return
	 */
	String getFieldName(String field);

	/**
	 * 데이터 바인딩시 실패한 경우 메세지
	 * @param message
	 * @return
	 */
	String getBindingFailure(String message);
}
