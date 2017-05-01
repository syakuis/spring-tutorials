package org.syaku.tutorials.spring.xss.support;

import com.nhncorp.lucy.security.xss.XssFilter;
import com.nhncorp.lucy.security.xss.XssSaxFilter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.syaku.tutorials.spring.xss.support.reflection.ObjectRef;

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

	@Pointcut("within(org.syaku.tutorials.spring.xss.web.*) && @target(org.springframework.stereotype.Controller)")
	public void porintTarget() {
		logger.debug(">< >< invoke aspectj");
	}

	@Before("porintTarget() && (execution(public * *(.., @org.syaku.tutorials.spring.xss.support.Defence (*))) || execution(public * *(@org.syaku.tutorials.spring.xss.support.Defence (*), ..)) || execution(public * *(.., @org.syaku.tutorials.spring.xss.support.Defence (*), ..)))")
	public void xssFilter(JoinPoint point) throws InstantiationException, IllegalAccessException {
		logger.debug(">< >< invoke aspectj");
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
