package org.syaku.spring.tutorials.aspectj.xss.support.reflection;

import java.lang.annotation.Annotation;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 24.
 */
public interface ObjectRefConverter {
	Object value(Object object, Annotation annotation);
	Class<? extends Annotation> getAnnotation();
}