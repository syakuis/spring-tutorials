package org.syaku.tutorials.spring.validation.constraints;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.UnsupportedEncodingException;

/**
 * 입력한 값이 정의한 값 사이에 존재하는 지 판단한다.
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 5. 15.
 */
public class SingleByteValidator implements ConstraintValidator<SingleByte, String> {
	private static final Logger logger = LoggerFactory.getLogger(SingleByteValidator.class);

	private String charset;

	@Override
	public void initialize(SingleByte parameters) {
		this.charset = parameters.charset();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null || value.length() == 0) {
			return true;
		}

		try {
			byte[] bytes = value.getBytes(this.charset);
			for (int i = 0; i < bytes.length; i++) {
				if (bytes[i] < 0) {
					return false;
				}
			}
		} catch (UnsupportedEncodingException uee) {
			logger.error(uee.getMessage(), uee);
			return false;
		}

		return true;
	}
}
