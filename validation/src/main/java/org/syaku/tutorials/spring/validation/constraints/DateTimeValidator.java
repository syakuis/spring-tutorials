package org.syaku.tutorials.spring.validation.constraints;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 5. 15.
 */
public class DateTimeValidator implements ConstraintValidator<DateTime, String> {
	private static final Logger logger = LoggerFactory.getLogger(DateTimeValidator.class);

	private String message;
	private String format;

	@Override
	public void initialize(DateTime constraintAnnotation) {
		this.message = constraintAnnotation.message();
		this.format = constraintAnnotation.format();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null || "".equals(value)) {
			return true;
		}

		try {
			SimpleDateFormat formatter = new SimpleDateFormat(this.format);
			Date date = formatter.parse(value);
			String dateString = formatter.format(date);

			if (logger.isDebugEnabled()) {
				logger.debug(">< >< value: {}, result: {}", value, dateString);
			}

			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(message).addConstraintViolation();

			return value.equals(dateString);
		} catch (ParseException pe) {
			return false;
		}
	}
}
