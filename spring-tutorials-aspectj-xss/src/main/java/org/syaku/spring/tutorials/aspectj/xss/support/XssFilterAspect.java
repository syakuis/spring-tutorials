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
import org.syaku.spring.tutorials.aspectj.xss.support.reflection.ObjectRef;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

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

	@Before("execution(public * *(.., @XssClean (*))) || execution(* *(@XssClean (*), ..)) || execution(* *(.., @XssClean (*), ..))")
	public void xssFilter(JoinPoint point) throws InstantiationException, IllegalAccessException {
		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();
		Object[] args =  point.getArgs();
		logger.debug("aspect {}, {}", args[2]);
		XssFilterConverter xssFilterConverter = new XssFilterConverter(xssFilter, xssSaxFilter);
		ObjectRef objectRef = new ObjectRef(xssFilterConverter);
		objectRef.getMethodParameter(method, args);
		logger.debug("aspect {}", args[2]);
	}
}
