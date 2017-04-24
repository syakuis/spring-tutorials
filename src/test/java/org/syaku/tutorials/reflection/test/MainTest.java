package org.syaku.tutorials.reflection.test;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 14.
 */
public class MainTest {
	private static final Logger logger = LoggerFactory.getLogger(MainTest.class);

	private Foo foo;

	@Before
	public void before() {
		foo = new Foo();
		foo.setName("foot");
		foo.labelsArray2List("1","2","3","4");
		foo.childArray2List(
				new Child("child1", "type1"),
				new Child("child3", "type3"),
				new Child("child3", "type3")
		);
	}

	@Test
	public void test() throws Exception {
		// foo object
		logger.debug(foo.toString());

		// get class
		Class clz = foo.getClass();

		// private 접근제어도 가져오기 위함.
		Field[] fields = clz.getDeclaredFields();

		for(Field field : fields) {
			// 항목 접근을 허용해준다.
			field.setAccessible(true);
			logger.debug("{} : {}", field.getName(), field.get(foo));

			if (field.getType() == ArrayList.class) {

			}
		}

		/*
		Method[] methods = clz.getMethods();

		for(Method method : methods) {
			logger.debug("{} method", method.getName());
		}
		*/
	}

}
