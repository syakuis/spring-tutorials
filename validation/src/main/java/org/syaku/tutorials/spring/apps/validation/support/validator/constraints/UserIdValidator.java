package org.syaku.tutorials.spring.apps.validation.support.validator.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.syaku.tutorials.spring.apps.validation.support.validator.constraints.UserId.RegexType;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 5. 15.
 */
public class UserIdValidator implements ConstraintValidator<UserId, String> {
	private RegexType regexType;
	private String message;

	@Override
	public void initialize(UserId constraintAnnotation) {
		this.regexType = constraintAnnotation.value();
		this.message = constraintAnnotation.message();
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null || "".equals(value)) {
			return true;
		}

		String expression;
		String message;

		if (regexType.equals(RegexType.ALPHABET_NUMBER_UNDERSCORE)) {
			expression = "(^[a-z][a-z0-9_]+)";
			message = "{text.valid.UserId.ALPHABET_NUMBER_UNDERSCORE}";
		} else if (regexType.equals(RegexType.ALPHABET_NUMBER)) {
			expression = "(^[a-z][a-z0-9]+)";
			message = "{text.valid.UserId.ALPHABET_NUMBER}";
		} else if (regexType.equals(RegexType.NUMBER)) {
			expression = "([0-9]+)";
			message = "{text.valid.UserId.NUMBER}";
		} else {
			expression = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
			message = "{text.valid.UserId.EMAIL}";
		}

		if (this.message != null && !"".equals(this.message)) {
			message = this.message;
		}

		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate(message).addConstraintViolation();

		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(value);

		return matcher.find();
	}
}
