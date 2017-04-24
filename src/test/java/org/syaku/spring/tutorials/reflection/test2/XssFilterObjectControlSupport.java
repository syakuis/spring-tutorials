package org.syaku.spring.tutorials.reflection.test2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syaku.tutorials.java.reflection.ObjectControlSupport;
import org.syaku.tutorials.spring.xss.support.XssClean;
import org.syaku.tutorials.spring.xss.support.XssType;

import java.lang.annotation.Annotation;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 20.
 */
public class XssFilterObjectControlSupport implements ObjectControlSupport {
	private static final Logger logger = LoggerFactory.getLogger(XssFilterObjectControlSupport.class);

	private Class<? extends Annotation> annotation = XssClean.class;

	@Override
	public Class<? extends Annotation> getAnnotation() {
		return annotation;
	}

	@Override
	public Object value(Object object, Annotation annotation) {
		if (object == null) return object;

		if (object.getClass() == String.class) {
			if (this.annotation.equals(annotation)) {
				XssType xssType = ((XssClean) annotation).value();
				logger.debug("찾음!! {} {}", annotation.toString(), xssType);
			}
			return ((String) object).replaceAll("good", "GOOD");
		}

		return object;
	}
}
