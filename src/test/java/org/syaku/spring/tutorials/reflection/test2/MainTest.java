package org.syaku.spring.tutorials.reflection.test2;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 17.
 */
public class MainTest {
	private static final Logger logger = LoggerFactory.getLogger(MainTest.class);

	private Foo foo;
	private int depth = 0;

	@Before
	public void setUp() {
		foo = new Foo();

		foo.setName("good");
		foo.setCount(1);
		foo.setListString(Arrays.asList("good1", "good2"));
		foo.setListInter(Arrays.asList(1, 2, 3));

		foo.setListToo(Arrays.asList(Arrays.asList(
				new Too("1", Arrays.asList("1","1","1","1","1")),
				new Too("2", null),
				new Too("3", null),
				new Too("4", null)
		),Arrays.asList(
				new Too(),
				new Too("2", null),
				new Too("3", null),
				new Too("4", null)
		)));

		foo.setToo(new Too("11", Arrays.asList("111", "112")));

		Set setToo = new HashSet();
		setToo.add(new Too("set1", Arrays.asList("set1","set1","set1","1","1")));
		foo.setSetToo(setToo);

		Map map = new HashMap();
		map.put("111", 11);
		map.put("112", 11);
		map.put("113", 11);
		foo.setMap(map);

		Map mapToo = new HashMap();
		map.put("111", new Too());
		map.put("112",  new Too("1", Arrays.asList("1","1","1","1","1")));
		map.put("113",  new Too());
	}

	@Test
	public void test() throws IllegalArgumentException, IllegalAccessException {
		getField(foo);
	}

	private static boolean isWrapperType(Class<?> clazz) {
		return clazz.equals(Boolean.class) ||
				clazz.equals(Integer.class) ||
				clazz.equals(Character.class) ||
				clazz.equals(Byte.class) ||
				clazz.equals(Short.class) ||
				clazz.equals(Double.class) ||
				clazz.equals(Long.class) ||
				clazz.equals(Float.class);
	}

	private void getFields() {

	}

	private void getField(Object object) throws IllegalArgumentException, IllegalAccessException {
		Class clz = object.getClass();
		Field[] fields = clz.getDeclaredFields();

		for(Field field : fields) {
			Type genericType = field.getGenericType();
			Type type = field.getType();
			String name = field.getName();
			field.setAccessible(true);
			Object value = field.get(object);

			//logger.debug("----> {}<{}> {} = {}", type, genericType, name, value);

			if (value != null) {
				//logger.debug("----> {}<{}> {} = {}", type, genericType, name, value);

				Class valueClz = value.getClass();
				//System.out.println(valueClz.isArray());

				if (isWrapperType(valueClz) || valueClz == String.class) {
					logger.debug("reference....... {} = {}", name, value);
				} else if (valueClz.isArray()) {
					for (Object o2 : (Object[]) value){
						if (isWrapperType(o2.getClass()) || o2.getClass() == String.class) {
							logger.debug("collection {} = {}", name, o2);
						} else {
							getField(o2);
						}
					}
				} else if (Collection.class.isAssignableFrom(field.getType())) {
					//logger.debug("collection....... {} = {}", name, value);
					Collection collection = (Collection) value;
					for (Object o2 : collection) {
						if (isWrapperType(o2.getClass()) || o2.getClass() == String.class) {
							logger.debug("collection {} = {}", name, o2);
						} else {
							getField(o2);
						}
					}
				} else if (Map.class.isAssignableFrom(field.getType())) {
					//logger.debug("map....... {} = {}", name, value);
					Map map = (Map) value;
					Collection collection = map.values();
					for (Object o2 : collection) {
						if (isWrapperType(o2.getClass()) || o2.getClass() == String.class) {
							logger.debug("map {} = {}", name, o2);
						} else {
							getField(o2);
						}
					}
				} else {
					getField(value);
				}
			}

		}
	}
}
