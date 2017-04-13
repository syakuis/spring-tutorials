package org.syaku.spring.tutorials.aspectj.xss.support;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.ReflectiveMethodInvocation;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

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
public class XssAspectSupport {
	private static final Logger logger = LoggerFactory.getLogger(XssAspectSupport.class);
/*
	@AfterReturning(pointcut = "execution(* *(@XssFilter (*)))", returning = "result")
	public void xssFilter2(JoinPoint point, Object result) {
		logger.debug(" ====================> aspect call");
		logger.debug(result.toString());
	}
*/
	// @After("@annotation(org.syaku.spring.tutorials.aspectj.xss.support.XssFilter)")
	// @Before("execution(public * *(..)) && @args(org.syaku.spring.tutorials.aspectj.xss.support.XssFilter,..)")

	// 어떤 위치의 파라메터라도 읽을 수 있게 포인트컷 설정.
	@Before("execution(* *(.., @XssFilter (*))) || execution(* *(@XssFilter (*), ..)) || execution(* *(.., @XssFilter (*), ..))")
	public void xssFilter(JoinPoint point) throws Exception {
		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();
		Annotation[][] methodAnnotations = method.getParameterAnnotations();
		Object[] objects = point.getArgs();
		logger.debug(" ====================> aspect call");
		logger.debug(" args = {} , annotations = {}", objects.length, methodAnnotations.length);
		//logger.debug(object[0].toString());

		if (objects.length != methodAnnotations.length) {
			throw new Exception("파라메터의 수가 일치하지 않아요.");
		}

		int i = 0;
		for (Annotation[] annotations : methodAnnotations) {
			for (Annotation annotation : annotations) {
				if (annotation instanceof XssFilter) {
					logger.debug("finding XssFilter");
					Object object = objects[i];
					Class clz = object.getClass();

					// private 필드를 읽기 위해 getDeclaredFields 메서드를 사용한다.
					for (Field field : clz.getDeclaredFields()) {
						for (Annotation annotation2 : field.getDeclaredAnnotations()) {
							if (annotation instanceof XssFilter) {
								// private 접근자에 접근할 수 있게 설정한다.
								field.setAccessible(true);
								logger.debug("{} : {}", field.getName(), field.get(object));
								field.set(object, "2");
							}
						}
					}
/*
					for (Method method1 : clz.getMethods()) {
						logger.debug(method1.getName());
						if ("getName".equals(method1.getName())) {
							Object value = method1.invoke(object);
							logger.debug(value.toString());
						}

						if ("setName".equals(method1.getName())) {
							method1.invoke(object, "2");
						}
					}*/
				}
			}
			i++;

		}
/*
		for (Object object : point.getArgs()) {
			Class clz = object.getClass();

			logger.debug(clz.getName());
		}

		for (Object object : point.getArgs()) {
			Class clz = object.getClass();

			for (Field field : clz.getDeclaredFields()) {
				logger.debug(field.getName());

				logger.debug(field.getAnnotatedType().toString());
			}

			for (Method method1 : clz.getMethods()) {
				logger.debug(method1.getName());
				if ("getName".equals(method1.getName())) {
					Object value = method1.invoke(object);
					logger.debug(value.toString());
				}

				if ("setName".equals(method1.getName())) {
					method1.invoke(object, "2");
				}
			}
		}*/
/*
		for (Parameter parameter : method.getParameters()) {

			Class clz = parameter.getType();
			logger.debug(clz.getCanonicalName());
			for (Field field : clz.getFields()) {

				logger.debug(field.getName());
				logger.debug(field.get(object[0]).toString());
			}
			for (Method method1 : clz.getMethods()) {
				if ("getName".equals(method1.getName())) {
					Object value = method1.invoke(object[0]);
					logger.debug(value.toString());
				}
			}
		}*/
	}
}
