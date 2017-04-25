package org.syaku.tutorials.spring.xss.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syaku.tutorials.spring.xss.support.reflection.ObjectRefConverter;

import java.lang.annotation.Annotation;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 25.
 */
public class ReferenceTypeConverter implements ObjectRefConverter {
	private static final Logger logger = LoggerFactory.getLogger(ReferenceTypeConverter.class);

	@Override
	public Object value(Object object, Annotation annotation) {
		if (object instanceof String) {
			return ((String) object).toUpperCase();
		} else if (object instanceof Boolean) {
			return !(boolean) object;
		} else if (object instanceof Float) {
			return (float) object * 1000;
		} else if (object instanceof Long) {
			return (long) object * 1000;
		} else if (object instanceof Integer) {
			return (int) object * 1000;
		} else {
			return object;
		}
	}

	@Override
	public Class<? extends Annotation> getAnnotation() {
		return null;
	}
}
