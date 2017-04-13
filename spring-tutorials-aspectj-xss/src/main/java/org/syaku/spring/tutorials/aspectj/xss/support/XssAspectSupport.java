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
import java.lang.reflect.Method;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 13.
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
	@Before("execution(* *(@XssFilter (*)))")
	public void xssFilter(JoinPoint point) {
		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();
		Annotation[][] methodAnnotations = method.getParameterAnnotations();
		Object[] object = point.getArgs();
		logger.debug(" ====================> aspect call");
		logger.debug(object[0].toString());

		object[0] = null;
	}
}
