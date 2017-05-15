package org.syaku.tutorials.spring.apps.validation.support.validator.constraints;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
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
@Constraint(validatedBy = DateTimeValidator.class)
@NotEmpty
@Documented
public @interface DateTime {
	/**
	 * default {text.valid.UserId.ALPHABET_NUMBER_UNDERSCORE}
	 * @return
	 */
	String message() default "{text.valid.DateTime}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	/**
	 * dateTime yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	String format() default "yyyy-MM-dd";
}
