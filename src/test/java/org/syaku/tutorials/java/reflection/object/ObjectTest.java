package org.syaku.tutorials.java.reflection.object;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syaku.tutorials.java.reflection.method.MethodTest;
import org.syaku.tutorials.java.reflection.model.Foo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 21.
 */
public class ObjectTest {
	private static final Logger logger = LoggerFactory.getLogger(MethodTest.class);

	@Test
	public void list() throws NoSuchMethodException {
		List<String> list = new ArrayList<>();
		list.add("a");
		list.add("a");
		list.add("a");
		list.add("a");
		list.add("a");
		getList(list);

		List<Integer> list2 = new ArrayList<>();
		list2.add(1);
		list2.add(1);
		list2.add(1);
		list2.add(1);
		list2.add(1);
		getList(list2);
	}


	private boolean isWrapperType(Class<?> clazz) {
		return clazz.equals(String.class) ||
				clazz.equals(Boolean.class) ||
				clazz.equals(Integer.class) ||
				clazz.equals(Character.class) ||
				clazz.equals(Byte.class) ||
				clazz.equals(Short.class) ||
				clazz.equals(Double.class) ||
				clazz.equals(Long.class) ||
				clazz.equals(Float.class);
	}

	private <T> void getList(Object object) {
		if (object.getClass() != ArrayList.class) {
			return;
		}

		List<T> list = (List<T>) object;

		logger.debug("ArrayList {}");

		try {
			for (T value : list) {
				logger.debug("{}", value.getClass().getTypeName());
				Class<?> clz = value.getClass();

				// 자료형인 경우에만
				if (isWrapperType(clz)) {

					Field[] fields = clz.getDeclaredFields();
					for (Field field : fields) {
						field.setAccessible(true);
						if ("value".equals(field.getName())) {
							field.set(object, "1");
							logger.debug("{} = {}", field.getName(), value);
						}
					}

				}

			}
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		}


	}
}