package org.syaku.tutorials.spring.validation.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 한 글자에 대한 바이크 크기 검사.
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 5. 15.
 */
@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = SingleByteValidator.class)
@Documented
public @interface SingleByte {
	String message() default "{text.valid.SingleByte}";

	String charset() default "utf-8";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
