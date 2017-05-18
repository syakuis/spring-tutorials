package org.syaku.tutorials.spring.validation.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

/**
 * 입력한 값이 정의한 값 사이에 존재하는 지 판단한다.
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 5. 15.
 */
public class BetweenValidator implements ConstraintValidator<Between, CharSequence> {
	private String[] values;
	private Between.ValueSet valueSet;

	@Override
	public void initialize(Between parameters) {
		this.values = parameters.values();
		this.valueSet = parameters.valueSet();
	}

	@Override
	public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
		if (value == null || value.length() == 0) {
			return true;
		}

		if (this.values.length == 0) {
			String message;
			if (Between.ValueSet.YN.equals(this.valueSet)) {
				this.values = new String[]{ "Y", "N" };
				message = "{text.valid.Between.YN}";
			} else if (Between.ValueSet.ZERO_ONE.equals(this.valueSet)) {
				this.values = new String[]{ "0", "1" };
				message = "{text.valid.Between.ZERO_ONE}";
		 	} else {
				throw new IllegalArgumentException();
			}

			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
		}

		return Arrays.asList(this.values).indexOf(value) > -1;
	}
}
