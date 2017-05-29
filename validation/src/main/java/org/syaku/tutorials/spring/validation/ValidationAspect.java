package org.syaku.tutorials.spring.validation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Method;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 5. 16.
 */
@Aspect
public class ValidationAspect implements Ordered {
	private static final Logger logger = LoggerFactory.getLogger(ValidationAspect.class);
	private Validator validator;

	@Override
	public int getOrder() {
		return 1;
	}

	@Autowired
	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	@Pointcut("@target(org.springframework.stereotype.Controller) || @target(org.springframework.web.bind.annotation.RestController)")
	public void pointTarget() {
		logger.debug(">< >< invoke aspectj");
	}

	@Before("pointTarget() && (execution(public * *(.., @org.syaku.tutorials.spring.validation.Validation (*))) || execution(public * *(@org.syaku.tutorials.spring.validation.Validation (*), ..)) || execution(public * *(.., @org.syaku.tutorials.spring.validation.Validation (*), ..)))")
	public void validation(JoinPoint point) {
		logger.debug(">< >< Validation Arguments Aspect");
		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();
		Annotation[][] argAnnotations = method.getParameterAnnotations();
		Object[] args = point.getArgs();

		ValidationWrapper wrapper = new ValidationWrapper();
		BindingResult bindingResult = null;
		boolean isBindingResult = false;


		for (int i = 0; i < args.length; i++) {
			Object arg = args[i];
			Annotation annotation = getAnnotation(argAnnotations[i], Validation.class);

			if (annotation != null) {
				wrapper.setObject(arg);
				DataBinder binder = new DataBinder(wrapper);
				binder.setValidator(validator);
				binder.validate();
				bindingResult = binder.getBindingResult();
			}

			if (bindingResult != null && arg instanceof BindingResult) {
				Array.set(args, i, bindingResult);
				isBindingResult = true;
			}
		}

		if (!isBindingResult && bindingResult.hasErrors()) {
			throw new ValidationException(bindingResult);
		}
	}

	private Annotation getAnnotation(Annotation[] annotations, Class<?> clazz) {
		for (Annotation annotation : annotations) {
			if (clazz.isInstance(annotation)) {
				return annotation;
			}
		}

		return null;
	}
}
