package org.syaku.tutorials.java.reflection.method;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syaku.tutorials.java.reflection.model.Foo;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 21.
 */
public class MethodTest {
	private static final Logger logger = LoggerFactory.getLogger(MethodTest.class);

	private Foo foo;

	@Before
	public void setUp() {
		this.foo = new Foo();
	}


	@Test
	public void method() throws NoSuchMethodException {
		foo.setName("syaku");
		getMethodParameter(foo, "setName", String.class);
	}

	/**
	 * 클래스 내에 특정 메서드를 가져와 어노테이션을 이용해 파라메터 값을 대입하여 메서드를 실행한다.
	 *
	 * @param object the object
	 * @param name   the name
	 */
	private void getMethodParameter(Object object, String name, Class<?>... parameterTypes) throws NoSuchMethodException {
		Class clz = object.getClass();
		Method method = clz.getMethod(name, parameterTypes);

		Parameter[] parameters = method.getParameters();

		for (Parameter parameter : parameters) {
			logger.debug("{}", parameter.getName());
			logger.debug("{}", parameter.getType());
			logger.debug("{}", parameter.getAnnotatedType());
		}

	}
}
