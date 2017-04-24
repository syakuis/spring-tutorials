package org.syaku.tutorials.reflection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syaku.tutorials.spring.xss.support.FilterXss;
import org.syaku.tutorials.spring.xss.support.XssType;
import org.syaku.tutorials.spring.xss.support.reflection.ObjectRefConverter;

import java.lang.annotation.Annotation;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 20.
 */
public class XssFilterConverter implements ObjectRefConverter {
	private static final Logger logger = LoggerFactory.getLogger(XssFilterConverter.class);

	private Class<? extends Annotation> annotation = FilterXss.class;

	@Override
	public Class<? extends Annotation> getAnnotation() {
		return annotation;
	}

	@Override
	public Object value(Object object, Annotation annotation) {
		if (object == null) return object;

		if (object.getClass() == String.class) {
			if (this.annotation.equals(annotation)) {
				XssType xssType = ((FilterXss) annotation).value();
				logger.debug("찾음!! {} {}", annotation.toString(), xssType);
			}
			return ((String) object).replaceAll("good", "GOOD");
		}

		return object;
	}
}
