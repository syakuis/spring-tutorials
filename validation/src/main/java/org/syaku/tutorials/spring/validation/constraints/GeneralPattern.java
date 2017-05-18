package org.syaku.tutorials.spring.validation.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 5. 15.
 */
@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = GeneralPatternValidator.class)
@Documented
public @interface GeneralPattern {
	String message() default "";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	Regex pattern();

	enum Regex {
		UPPER_ALPHABET_NUMBER_UNDERSCORE,
		UPPER_ALPHABET_NUMBER,
		UPPER_ALPHABET,
		ALPHABET_NUMBER_UNDERSCORE,
		ALPHABET_NUMBER,
		ALPHABET,
		KOREAN_NUMBER,
		KOREAN,
		NUMBER,
		USER_ID,
		CRONTAB
		//EMAIL
	}
}
