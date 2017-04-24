package org.syaku.spring.tutorials.aspectj.xss.support;

import com.nhncorp.lucy.security.xss.XssFilter;
import com.nhncorp.lucy.security.xss.XssPreventer;
import com.nhncorp.lucy.security.xss.XssSaxFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syaku.spring.tutorials.aspectj.xss.support.reflection.ObjectRefConverter;

import java.lang.annotation.Annotation;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 24.
 */
public class XssFilterConverter implements ObjectRefConverter {
	private static final Logger logger = LoggerFactory.getLogger(XssFilterConverter.class);

	private XssFilter xssFilter;
	private XssSaxFilter xssSaxFilter;

	public XssFilterConverter(XssFilter xssFilter, XssSaxFilter xssSaxFilter) {
		this.xssFilter = xssFilter;
		this.xssSaxFilter = xssSaxFilter;
	}

	@Override
	public Object value(Object object, Annotation annotation) {
		if (annotation == null || object == null || object.getClass() != String.class) {
			logger.debug("변경할 사항이 없습니다.");
			return object;
		}

		XssType xssType = ((XssClean) annotation).value();

		String value = (String) object;

		if (xssType.equals(XssType.SAX)) {
			return xssSaxFilter.doFilter(value);
		} else if (xssType.equals(XssType.DOM)) {
			return xssFilter.doFilter(value);
		} else {
			return XssPreventer.escape(value);
		}
	}

	@Override
	public Class<? extends Annotation> getAnnotation() {
		return XssClean.class;
	}
}
