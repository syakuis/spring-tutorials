package org.syaku.tutorials.spring.validation.constraints;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * 값이 사용자 계정 패턴에 맞게 입력했는 지 판단한다.
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 5. 15.
 */
public class GeneralPatternValidator implements ConstraintValidator<GeneralPattern, CharSequence> {
	private static final Logger logger = LoggerFactory.getLogger(GeneralPatternValidator.class);
	private GeneralPattern.Regex pattern;
	private String message;

	@Override
	public void initialize(GeneralPattern constraintAnnotation) {
		this.pattern = constraintAnnotation.pattern();
		this.message = constraintAnnotation.message();
	}

	@Override
	public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
		if (value == null || value.length() == 0) {
			return true;
		}

		String expression;
		String message;

		switch(pattern) {
			case ALPHABET_NUMBER_UNDERSCORE:
				expression = "([a-zA-Z0-9_]+)";
				message = "{text.valid.GeneralPattern.ALPHABET_NUMBER_UNDERSCORE}";
				break;
			case ALPHABET_NUMBER:
				expression = "([a-zA-Z0-9]+)";
				message = "{text.valid.GeneralPattern.ALPHABET_NUMBER}";
				break;
			case ALPHABET:
				expression = "([a-zA-Z]+)";
				message = "{text.valid.GeneralPattern.ALPHABET}";
				break;
			case UPPER_ALPHABET_NUMBER_UNDERSCORE:
				expression = "([A-Z0-9_]+)";
				message = "{text.valid.GeneralPattern.UPPER_ALPHABET_NUMBER_UNDERSCORE}";
				break;
			case UPPER_ALPHABET_NUMBER:
				expression = "([A-Z0-9]+)";
				message = "{text.valid.GeneralPattern.UPPER_ALPHABET_NUMBER}";
				break;
			case UPPER_ALPHABET:
				expression = "([A-Z]+)";
				message = "{text.valid.GeneralPattern.UPPER_ALPHABET}";
				break;
			case KOREAN_NUMBER:
				expression = "([가-핳0-9]+)";
				message = "{text.valid.GeneralPattern.KOREAN_NUMBER}";
				break;
			case KOREAN:
				expression = "([가-핳]+)";
				message = "{text.valid.GeneralPattern.KOREAN}";
				break;
			case NUMBER:
				expression = "([0-9]+)";
				message = "{text.valid.GeneralPattern.NUMBER}";
				break;
			case USER_ID:
				expression = "(^[a-z][a-z0-9_]+)";
				message = "{text.valid.GeneralPattern.USER_ID}";
				break;
			case CRONTAB:
				expression = "^\\s*($|#|\\w+\\s*=|(\\?|\\*|(?:[0-5]?\\d)(?:(?:-|\\/|\\,)(?:[0-5]?\\d))?(?:,(?:[0-5]?\\d)(?:(?:-|\\/|\\,)(?:[0-5]?\\d))?)*)\\s+(\\?|\\*|(?:[0-5]?\\d)(?:(?:-|\\/|\\,)(?:[0-5]?\\d))?(?:,(?:[0-5]?\\d)(?:(?:-|\\/|\\,)(?:[0-5]?\\d))?)*)\\s+(\\?|\\*|(?:[01]?\\d|2[0-3])(?:(?:-|\\/|\\,)(?:[01]?\\d|2[0-3]))?(?:,(?:[01]?\\d|2[0-3])(?:(?:-|\\/|\\,)(?:[01]?\\d|2[0-3]))?)*)\\s+(\\?|\\*|(?:0?[1-9]|[12]\\d|3[01])(?:(?:-|\\/|\\,)(?:0?[1-9]|[12]\\d|3[01]))?(?:,(?:0?[1-9]|[12]\\d|3[01])(?:(?:-|\\/|\\,)(?:0?[1-9]|[12]\\d|3[01]))?)*)\\s+(\\?|\\*|(?:[1-9]|1[012])(?:(?:-|\\/|\\,)(?:[1-9]|1[012]))?(?:L|W)?(?:,(?:[1-9]|1[012])(?:(?:-|\\/|\\,)(?:[1-9]|1[012]))?(?:L|W)?)*|\\?|\\*|(?:JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)(?:(?:-)(?:JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC))?(?:,(?:JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)(?:(?:-)(?:JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC))?)*)\\s+(\\?|\\*|(?:[0-6])(?:(?:-|\\/|\\,|#)(?:[0-6]))?(?:L)?(?:,(?:[0-6])(?:(?:-|\\/|\\,|#)(?:[0-6]))?(?:L)?)*|\\?|\\*|(?:MON|TUE|WED|THU|FRI|SAT|SUN)(?:(?:-)(?:MON|TUE|WED|THU|FRI|SAT|SUN))?(?:,(?:MON|TUE|WED|THU|FRI|SAT|SUN)(?:(?:-)(?:MON|TUE|WED|THU|FRI|SAT|SUN))?)*)(|\\s)+(\\?|\\*|(?:|\\d{4})(?:(?:-|\\/|\\,)(?:|\\d{4}))?(?:,(?:|\\d{4})(?:(?:-|\\/|\\,)(?:|\\d{4}))?)*))$";
				message = "{text.valid.GeneralPattern.CRONTAB}";
				break;
				/*
			case EMAIL:
				expression = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
				message = "{text.valid.GeneralPattern.EMAIL}";
				break;*/
			default:
				throw new IllegalArgumentException();
		}

		if (logger.isDebugEnabled()) {
			logger.debug(">< >< expression: {}, message: {}", expression, message);
		}

		if ("".equals(this.message)) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
		}

		Pattern pattern = Pattern.compile(expression);
		return pattern.matcher(value).matches();
	}
}
