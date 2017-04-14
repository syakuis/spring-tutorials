package org.syaku.spring.tutorials.aspectj.xss.support;

import com.nhncorp.lucy.security.xss.XssPreventer;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
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
	//@Pointcut("@annotation(org.springframework.stereotype.Controller)")
	//public void controller() {

	//}

	// 어떤 위치의 파라메터라도 읽을 수 있게 포인트컷 설정.
	//@Before("@within(@XssFilter)")
	//public void test() {
///		System.out.println("good123");
	//}


	@Before("execution(public * *(.., @XssFilter (*))) || execution(* *(@XssFilter (*), ..)) || execution(* *(.., @XssFilter (*), ..))")
	//@Before("@within(org.syaku.spring.tutorials.aspectj.xss.support.XssFilter)")
	public void xssFilter(JoinPoint point) throws Exception {
		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();
		Annotation[][] methodAnnotations = method.getParameterAnnotations();
		Object[] objects = point.getArgs();
		//logger.debug(" ====================> aspect call");
		//logger.debug(" args = {} , annotations = {}", objects.length, methodAnnotations.length);
		//logger.debug(object[0].toString());

		if (objects.length != methodAnnotations.length) {
			throw new Exception("파라메터의 수가 일치하지 않아요.");
		}

		int i = 0;
		for (Annotation[] annotations : methodAnnotations) {
			for (Annotation annotation : annotations) {
				if (annotation instanceof XssFilter) {
					//logger.debug("finding XssFilter");
					Object object = objects[i];
					Class clz = object.getClass();

					//logger.debug("{}", object.toString());

					// private 필드를 읽기 위해 getDeclaredFields 메서드를 사용한다.
					for (Field field : clz.getDeclaredFields()) {
						if (field.getType() == String.class) {
							for (Annotation annotation2 : field.getDeclaredAnnotations()) {
								if (annotation2 instanceof XssFilter) {
									// private 접근자에 접근할 수 있게 설정한다.
									field.setAccessible(true);

									String name = field.getName();
									String value = (String) field.get(object);
									String escape = XssPreventer.escape(value);
									field.set(object, escape);

									//logger.debug("{} : {} --> {}", name, value, escape);
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
