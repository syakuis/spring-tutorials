package org.syaku.tutorials.reflection;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 18.
 */
public class ReflectionListTest {
	private static final Logger logger = LoggerFactory.getLogger(ReflectionListTest.class);

	private Foo foo;
	private int depth = 0;

	@Before
	public void setUp() {
		foo = new Foo();

		foo.setName("String - syaku");
		foo.setCount(1);
		foo.setListString(Arrays.asList("ArrayList - good1", "ArrayList - good2"));
		foo.setListInter(Arrays.asList(111, 112, 113));

		foo.setListToo(Arrays.asList(Arrays.asList(
				new Too("ArrayList Too - 1", Arrays.asList("ArrayList Too - 1","ArrayList Too - 1","ArrayList Too - 1","ArrayList Too - 1","ArrayList Too - 1")),
				new Too("ArrayList Too - 2", null),
				new Too("ArrayList Too - 3", null),
				new Too("ArrayList Too - 4", null)
		),Arrays.asList(
				new Too(),
				new Too("ArrayList Too - 2", null),
				new Too("ArrayList Too - 3", null),
				new Too("ArrayList Too - 4", null)
		)));

		foo.setToo(new Too("Too - 11", Arrays.asList("Too ArrayList - 111", "Too ArrayList - 112")));

		Set setToo = new HashSet();
		setToo.add(new Too("set1", Arrays.asList("Set Too - set1","Set Too - set1","Set Too - set1","Set Too - 1","Set Too - 1")));
		foo.setSetToo(setToo);

		Map map = new HashMap();
		map.put("111", 2211);
		map.put("112", 2211);
		map.put("113", 2211);
		foo.setMap(map);

		Map mapToo = new HashMap();
		mapToo.put("111", new Too());
		mapToo.put("112",  new Too("Map Too - 1", Arrays.asList("Map Too List - 1","Map Too List - 1","Map Too List - 1","Map Too List - 1","Map Too List - 1")));
		mapToo.put("113",  new Too());
		foo.setMapToo(mapToo);

		foo.setToos(new Too[]{
				new Too(),
				new Too("Array Too - 2", null),
				new Too("Array Too - 3", null),
				new Too("Array Too - 4", null)
		});

		foo.setStrs(new String[]{
				"Array String - 1",
				"Array String - 2",
				"Array String - 3",
				"Array String - 4"
		});
	}

	@Test
	public void test() throws IllegalAccessException, ClassNotFoundException {
		List<List<Too>> result = Arrays.asList(Arrays.asList(
				new Too("1", Arrays.asList("1","1","1","1","1")),
				new Too("2", null),
				new Too("3", null),
				new Too("4", null)
		),Arrays.asList(
				new Too(),
				new Too("2", null),
				new Too("3", null),
				new Too("4", null)
		));

		getType(foo, null);

		logger.debug("result {}", foo.toString());
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

	private void getType(Object value, Object object) throws IllegalAccessException, ClassNotFoundException {
		if (value == null) return;
		Class clz = value.getClass();

		if (isWrapperType(clz) || clz == String.class) {
			getValue(value, object);
		} else if (clz.isArray()) {
			getArray(value);
		} else if (Collection.class.isAssignableFrom(clz)) {
			getCollection(value);
		} else if (Map.class.isAssignableFrom(clz)) {
			getMap(value);
		} else {
			getObject(value);
		}
	}

	private <T> T cast(Class<T> type, Object value) {
		if (type.isInstance(value)){
			return type.cast(value);
		} else {
			return null;
		}
	}

	private void getValue(Object value, Object object) throws ClassNotFoundException {

	//	logger.debug("Value - {} {}", value.getClass().getTypeName(), cast(value.getClass(), value));
	}

	private void getObject(Object object) throws IllegalAccessException, ClassNotFoundException {
		Class clz = object.getClass();
		Field[] fields = clz.getDeclaredFields();

		for(Field field : fields) {
			field.setAccessible(true);
			getType(field.get(object), object);
		}
	}

	private void getCollection(Object object) throws IllegalAccessException, ClassNotFoundException {
		for(Object value : (Collection) object) {
			getType(value, object);
		}
	}

	private void getArray(Object object) throws IllegalAccessException, ClassNotFoundException {
		for(Object value : (Object[]) object) {
			getType(value, object);
		}
	}

	private void getMap(Object object) throws IllegalAccessException, ClassNotFoundException {
		Map map = new HashMap();
		for(Object value : ((Map) object).values()) {
			getType(value, object);
			map.put(value, value);
		}

		System.out.println(map.toString());
	}
}
