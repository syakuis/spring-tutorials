package org.syaku.tutorials.spring.validation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.validation.BindingResult;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 5. 16.
 */
@Aspect
public class ValidationResponseAspect implements Ordered {
	private static final Logger logger = LoggerFactory.getLogger(ValidationResponseAspect.class);

	@Override
	public int getOrder() {
		return 2;
	}

	@Pointcut("@target(org.springframework.stereotype.Controller) || @target(org.springframework.web.bind.annotation.RestController)")
	public void pointTarget() {
		logger.debug(">< >< invoke aspectj");
	}

	@Before("pointTarget() && (execution(public * *(.., @org.syaku.tutorials.spring.validation.ValidBindingResult (*))) || execution(public * *(@org.syaku.tutorials.spring.validation.ValidBindingResult (*), ..)) || execution(public * *(.., @org.syaku.tutorials.spring.validation.ValidBindingResult (*), ..)))")
	public void validResponse(JoinPoint point) {
		logger.debug(">< >< Validation Response Aspect");
		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();
		Annotation[][] argAnnotations = method.getParameterAnnotations();
		Object[] args = point.getArgs();

		for (int i = 0; i < args.length; i++) {
			Object arg = args[i];
			if (isAnnotation(argAnnotations[i], ValidBindingResult.class) && arg instanceof BindingResult) {
				BindingResult bindingResult = (BindingResult) arg;

				if (bindingResult.hasErrors()) {
					throw new ValidationException(bindingResult);
				}
			}
		}
	}

	private boolean isAnnotation(Annotation[] annotations, Class<?> clazz) {
		for (Annotation annotation : annotations) {
			if (clazz.isInstance(annotation)) {
				return true;
			}
		}

		return false;
	}
}
