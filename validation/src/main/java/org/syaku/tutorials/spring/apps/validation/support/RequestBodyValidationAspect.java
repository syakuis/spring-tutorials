package org.syaku.tutorials.spring.apps.validation.support;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 5. 16.
 */
@Aspect
@Component
public class RequestBodyValidationAspect {
	private static final Logger logger = LoggerFactory.getLogger(RequestBodyValidationAspect.class);

	@Pointcut("@target(org.springframework.stereotype.Controller) || @target(org.springframework.web.bind.annotation.RestController)")
	public void pointTarget() {
		logger.debug(">< >< invoke aspectj");
	}

	@Before("pointTarget() && (execution(public * *(.., @org.syaku.tutorials.spring.apps.validation.support.ValidResult (*))) || execution(public * *(@org.syaku.tutorials.spring.apps.validation.support.ValidResult (*), ..)) || execution(public * *(.., @org.syaku.tutorials.spring.apps.validation.support.ValidResult (*), ..)))")
	public void validResponse(JoinPoint point) {
		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();
		Annotation[][] argAnnotations = method.getParameterAnnotations();
		Object[] args =  point.getArgs();

		for(int i = 0; i < args.length; i++) {
			Object arg = args[i];
			if (isAnnotation(argAnnotations[i]) && arg instanceof BindingResult) {
				BindingResult bindingResult = (BindingResult) arg;

				if (bindingResult.hasErrors()) {
					 throw new ValidationException(bindingResult);
				}
			}
		}
	}

	private boolean isAnnotation(Annotation[] annotations) {
		for (Annotation annotation : annotations) {
			if (ValidResult.class.isInstance(annotation)) {
				return true;
			}
		}

		return false;
	}
}
