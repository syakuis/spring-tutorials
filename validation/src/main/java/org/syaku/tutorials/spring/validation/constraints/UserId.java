package org.syaku.tutorials.spring.validation.constraints;

import org.hibernate.validator.constraints.NotEmpty;

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
@Constraint(validatedBy = UserIdValidator.class)
@Documented
public @interface UserId {
	/**
	 * default {text.valid.UserId.ALPHABET_NUMBER_UNDERSCORE}
	 * @return
	 */
	String message() default "";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	RegexType value() default RegexType.ALPHABET_NUMBER_UNDERSCORE;

	enum RegexType {
		ALPHABET_NUMBER_UNDERSCORE, ALPHABET_NUMBER, NUMBER, EMAIL
	}
}
