package org.syaku.tutorials.spring.validation.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 문자열의 날짜형식을 {@link Date} 형으로 변형하여 올바른 날짜 형식과 포맷이지 판단한다.
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 5. 15.
 */
public class DateTimeValidator implements ConstraintValidator<DateTime, CharSequence> {
	private String format;

	@Override
	public void initialize(DateTime constraintAnnotation) {
		this.format = constraintAnnotation.format();
	}

	@Override
	public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
		if (value == null || value.length() == 0) {
			return true;
		}

		try {
			SimpleDateFormat formatter = new SimpleDateFormat(this.format);
			Date date = formatter.parse(value.toString());
			String dateString = formatter.format(date);

			return value.equals(dateString);
		} catch (ParseException pe) {
			return false;
		}
	}
}
