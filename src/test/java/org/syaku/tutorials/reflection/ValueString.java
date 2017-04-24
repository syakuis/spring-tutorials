package org.syaku.tutorials.reflection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 17.
 */
@Target({
		ElementType.FIELD
})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValueString {
}