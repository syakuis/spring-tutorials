package org.syaku.spring.tutorials.aspectj.xss.support;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 13.
 */
@Target({
		ElementType.FIELD,
		ElementType.PARAMETER
})
@Retention(RetentionPolicy.RUNTIME)
public @interface XssClean {
	XssType value() default XssType.SAX;
}
