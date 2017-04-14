package org.syaku.spring.tutorials.aspectj.xss.support;

import com.nhncorp.lucy.security.xss.XssFilter;
import com.nhncorp.lucy.security.xss.XssPreventer;
import com.nhncorp.lucy.security.xss.XssSaxFilter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 13.
 *
 * method get return : http://stackoverflow.com/questions/15697217/how-to-get-return-value-of-invoked-method
 * method set :
 */
@Aspect
@Component
public class XssFilterAspect {
	private static final Logger logger = LoggerFactory.getLogger(XssFilterAspect.class);

	@Autowired
	private XssSaxFilter xssSaxFilter;

	@Autowired
	private XssFilter xssFilter;

	@Before("execution(public * *(.., @XssValid (*))) || execution(* *(@XssValid (*), ..)) || execution(* *(.., @XssValid (*), ..))")
	public void xssFilter(JoinPoint point) throws IllegalAccessException {
		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();
		Annotation[][] methodAnnotations = method.getParameterAnnotations();
		Object[] objects = point.getArgs();

		if (objects.length != methodAnnotations.length) {
			throw new IndexOutOfBoundsException("파라메터의 수가 일치하지 않아요.");
		}

		int i = 0;
		for (Annotation[] annotations : methodAnnotations) {
			for (Annotation annotation : annotations) {
				if (annotation instanceof XssValid) {
					Object object = objects[i];
					Class clz = object.getClass();

					// private 필드를 읽기 위해 getDeclaredFields 메서드를 사용한다.
					for (Field field : clz.getDeclaredFields()) {
						if (field.getType() == String.class) {
							for (Annotation annotation2 : field.getDeclaredAnnotations()) {
								if (annotation2 instanceof XssClean) {
									// private 접근자에 접근할 수 있게 설정한다.
									XssType xssType = ((XssClean) annotation2).value();

									field.setAccessible(true);

									String value = (String) field.get(object);
									logger.debug("{} : {}", field.getName(), object.toString());
									String filter;

									if (xssType.equals(XssType.SAX)) {
										filter = xssSaxFilter.doFilter(value);
										field.set(object, filter);
									} else if (xssType.equals(XssType.DOM)) {
										filter = xssFilter.doFilter(value);
										field.set(object, filter);
									} else {
										filter = XssPreventer.escape(value);
										field.set(object, filter);
									}

									if (logger.isDebugEnabled()) {
										logger.debug("{}::{} = {} -->\n<-- {}", xssType.name(), field.getName(), value, filter);
									}
								}
							}
						}
					}
				}
			}
			i++;
		}
	}
}
