package org.syaku.tutorials.java.reflection;

import org.syaku.tutorials.spring.xss.support.FilterXss;
import org.syaku.tutorials.spring.xss.support.reflection.ObjectRefConverter;

import java.lang.annotation.Annotation;
import java.util.Locale;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 20.
 */
public class XssFilterObjectControlSupport implements ObjectRefConverter {
	@Override
	public Object value(Object object, Annotation annotation) {
		if (object != null && object.getClass() == String.class) {
			return ((String) object).toUpperCase(Locale.getDefault());
		}
		return object;
	}

	@Override
	public Class<? extends Annotation> getAnnotation() {
		return FilterXss.class;
	}
}
