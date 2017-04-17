package org.syaku.spring.tutorials.aspectj.xss.support;

import com.nhncorp.lucy.security.xss.XssFilter;
import com.nhncorp.lucy.security.xss.XssPreventer;
import com.nhncorp.lucy.security.xss.XssSaxFilter;
import org.apache.commons.lang3.StringUtils;
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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 13.
 *
 * method get return : http://stackoverflow.com/questions/15697217/how-to-get-return-value-of-invoked-method
 * method set :
 *
 * 찾기단계
 * @XssClean
 * Type ? (1) String.class : (2) Object
 * (1) 처리단계로 이동
 * (2) (찾기단계 재귀) 클래스 내부에 @XssClean 찾기 그리고 Type ? (1) String.class : (2) Object 처리
 *
 * 처리단계
 * String.class (필터처리단계)
 * GenericType 체크 String.class (필터처리단계)
 * 깊은 타입인 경우 처리단계 재귀)
 *
 * 필터처리단계
 *
 */
@Aspect
@Component
public class XssFilterAspect {
	private static final Logger logger = LoggerFactory.getLogger(XssFilterAspect.class);

	@Autowired
	private XssSaxFilter xssSaxFilter;

	@Autowired
	private XssFilter xssFilter;

	private void clzFinding(Object object) throws IllegalAccessException {
		Class clz = object.getClass();

		// private 필드를 읽기 위해 getDeclaredFields 메서드를 사용한다.
		for (Field field : clz.getDeclaredFields()) {
			if (field.getType() == String.class) {
				for (Annotation annotation : field.getDeclaredAnnotations()) {
					if (annotation instanceof XssClean) {
						// private 접근자에 접근할 수 있게 설정한다.
						XssType xssType = ((XssClean) annotation).value();

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

	@Before("execution(public * *(.., @XssClean (*))) || execution(* *(@XssClean (*), ..)) || execution(* *(.., @XssClean (*), ..))")
	public void xssFilter(JoinPoint point) throws IllegalAccessException, InvocationTargetException, NoSuchFieldException {
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
				Object object = objects[i];
				Class clz = object.getClass();
				if (!object.getClass().isPrimitive()) {
					if (annotation instanceof XssClean) {
						if (clz == String.class) {
							XssType xssType = ((XssClean) annotation).value();

							Field field = clz.getDeclaredField("value");

							String value = (String) object;
							logger.debug("{} : {}", field.getName(), value);
							String filter;

							if (xssType.equals(XssType.SAX)) {
								filter = xssSaxFilter.doFilter(value);
							} else if (xssType.equals(XssType.DOM)) {
								filter = xssFilter.doFilter(value);
							} else {
								filter = XssPreventer.escape(value);
							}

							field.setAccessible(true);
							field.set(object, filter.toCharArray());
						} else {
							clzFinding(object);
						}
					}
				}
			}
			i++;
		}

		//method.invoke(point.getThis(), objects);
	}
}
