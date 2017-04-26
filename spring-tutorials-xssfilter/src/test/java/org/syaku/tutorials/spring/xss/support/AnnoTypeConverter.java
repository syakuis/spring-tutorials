package org.syaku.tutorials.spring.xss.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syaku.tutorials.spring.xss.support.reflection.ObjectRefConverter;

import java.lang.annotation.Annotation;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 26.
 */
public class AnnoTypeConverter implements ObjectRefConverter {
	private static final Logger logger = LoggerFactory.getLogger(AnnoTypeConverter.class);

	@Override
	public Object value(Object object, Annotation annotation) {
		if (object instanceof String) {
			logger.debug(object.getClass().getName());
			return ((String) object).toUpperCase();
		}
		return object;
	}

	@Override
	public Class<? extends Annotation> getAnnotation() {
		return Anno.class;
	}
}
