package org.syaku.tutorials.java.reflection;

import java.lang.annotation.Annotation;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 20.
 */
public interface ObjectControlSupport {
	Object value(Object object, Annotation annotation);
	Class<? extends Annotation> getAnnotation();
}
