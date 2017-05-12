package org.syaku.tutorials.java.reflection.object;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 21.
 */
public class ObjectTest {
	private static final Logger logger = LoggerFactory.getLogger(ObjectTest.class);

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
				logger.debug("{}", value.getClass().getName());
				Class<?> clz = value.getClass();

				// 자료형인 경우에만
				if (isWrapperType(clz)) {
					Field field = clz.getDeclaredField("value");
					field.setAccessible(true);
/*
					Field modifiers = field.getClass().getDeclaredField("modifiers");
					modifiers.setAccessible(true);

					modifiers.set(field, field.getModifiers() & ~Modifier.FINAL);
					field.set(null, "1");
*/
					logger.debug("{} = {}", field.getName(), value);
				}

			}
		}catch (NoSuchFieldException e) {
			logger.error(e.getMessage(), e);
		}


	}
}