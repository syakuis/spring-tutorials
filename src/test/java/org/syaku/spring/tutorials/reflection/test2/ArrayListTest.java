package org.syaku.spring.tutorials.reflection.test2;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 18.
 */
public class ArrayListTest {
	private static final Logger logger = LoggerFactory.getLogger(ArrayListTest.class);

	@Test
	public void fooTest() throws InstantiationException, IllegalAccessException {
		Foo foo = new Foo();

		logger.debug("{}", foo.getClass().getTypeName());

		Map mapToo = new HashMap();
		mapToo.put("111", new Too());
		mapToo.put("112",  new Too("Map Too good - 1", Arrays.asList("Map Too List - 1","Map Too List - 1","Map Too List - 1","Map Too List - 1","Map Too List - 1")));
		mapToo.put("113",  new Too());
		foo.setMapToo(mapToo);

		logger.debug("{} {}", foo.getClass().getTypeName(), foo.toString());
	}

	public void listTest() throws InstantiationException, IllegalAccessException {
		List<String> strings = new LinkedList<>();
		strings.add("list - 1");
		strings.add("list - 2");
		strings.add("list - 3");
		strings.add("list - 4");

		Set<List<String>> set = new HashSet<>();
		set.add(strings);

		List<Set<List<String>>> list = new ArrayList<>();
		list.add(set);

		Object object = getType(list);



		logger.debug("{}", object);
		logger.debug("{}", object.getClass().getTypeName());

	}

	public void test() throws InstantiationException, IllegalAccessException {
		List<List<String>> result = Arrays.asList(
				Arrays.asList("good1", "good2", "good3", "good4"),
				Arrays.asList("good1", "good2", "good3", "good4")
		);
		logger.debug("result @{} ({}) {}", result.hashCode(), result.getClass(), result);
		List<String> result2 = (List) getType(result);
		logger.debug(" + result @{} ({}) {} // {}", result2.hashCode(), result2.getClass(), result2);


		Foo foo = new Foo();

		foo.setName("String - syaku");
		foo.setCount(1);
		foo.setListString(Arrays.asList("ArrayList - good1", "ArrayList - good2"));
		foo.setListInter(Arrays.asList(111, 112, 113));

		foo.setListToo(Arrays.asList(Arrays.asList(
				new Too("ArrayList  good Too - 1", Arrays.asList("ArrayList  good Too - 1","ArrayList Too - 1","ArrayList Too - 1","ArrayList Too - 1","ArrayList Too - 1")),
				new Too("ArrayList Too - 2", null),
				new Too("ArrayList Too - 3", null),
				new Too("ArrayList Too - 4", null)
		),Arrays.asList(
				new Too(),
				new Too("ArrayList Too - 2", null),
				new Too("ArrayList Too - 3", null),
				new Too("ArrayList Too - 4", null)
		)));

		foo.setToo(new Too("Too good - 11", Arrays.asList("Too ArrayList - 111", "Too ArrayList - 112")));

		Set setToo = new HashSet();
		setToo.add(new Too("set1", Arrays.asList("Set Too good - set1","Set Too - set1","Set Too - set1","Set Too - 1","Set Too - 1")));
		foo.setSetToo(setToo);

		Map map = new HashMap();
		map.put("111", 2211);
		map.put("112", 2211);
		map.put("113", 2211);
		foo.setMap(map);

		Map mapToo = new HashMap();
		mapToo.put("111", new Too());
		mapToo.put("112",  new Too("Map Too good - 1", Arrays.asList("Map Too List - 1","Map Too List - 1","Map Too List - 1","Map Too List - 1","Map Too List - 1")));
		mapToo.put("113",  new Too());
		foo.setMapToo(mapToo);

		foo.setToos(new Too[]{
				new Too(),
				new Too("Array Too good - 2", null),
				new Too("Array Too - 3", null),
				new Too("Array Too - 4", null)
		});

		foo.setStrs(new String[]{
				"Array String good - 1",
				"Array String - 2",
				"Array String - 3",
				"Array String - 4"
		});

		Foo foo2 = (Foo) getType(foo);
		logger.debug(" + result @{} ({}) {} // {}", foo2.hashCode(), foo2.getClass(), foo2);
	}

	private Object replaceStr(Object object) {
		logger.debug("{}", object);
		if (object != null && object.getClass() == String.class) {
			return ((String) object).replaceAll("good", "GOOD");
		}

		return object;
	}

	private boolean isWrapperType(Class<?> clazz) {
		return clazz.equals(Boolean.class) ||
				clazz.equals(Integer.class) ||
				clazz.equals(Character.class) ||
				clazz.equals(Byte.class) ||
				clazz.equals(Short.class) ||
				clazz.equals(Double.class) ||
				clazz.equals(Long.class) ||
				clazz.equals(Float.class);
	}

	private Object getType(Object value) throws IllegalAccessException, InstantiationException {
		if (value == null) return null;
		Class clz = value.getClass();

		if (isWrapperType(clz) || clz == String.class) {
			return replaceStr(value);
		} else if (clz.isArray()) {
			return getArray(value);
		} else if (Collection.class.isAssignableFrom(clz)) {
			return getCollection(value);
		} else if (Map.class.isAssignableFrom(clz)) {
			return getMap(value);
		} else {
			return getObject(value);
		}
	}

	private Object getObject(Object object) throws IllegalAccessException, InstantiationException {
		Class clz = object.getClass();
		Field[] fields = clz.getDeclaredFields();

		for(Field field : fields) {
			field.setAccessible(true);
			Object value = field.get(object);
			if (value != null) {
				field.set(object, getType(value));
			}
		}

		return object;
	}

	private Object[] getArray(Object object) throws IllegalAccessException, InstantiationException {
		Object[] objects = (Object[]) object;
		int count = objects.length;

		Object[] result = (Object[]) Array.newInstance(object.getClass().getComponentType(), count);

		logger.debug("{}", count);

		for (int i = 0; i < count; i++) {
			Array.set(result, i, getType(objects[i]));
		}

		return result;
	}

	private Map getMap(Object object) throws IllegalAccessException, InstantiationException {
		Map result = (Map) object.getClass().newInstance();
		Map map = (Map) object;

		Iterator keys = map.keySet().iterator();
		while(keys.hasNext()) {
			Object key = keys.next();
			result.put(key, getType(map.get(key)));
		}

		return result;
	}

	// 원래 클래스 타입으로 새로운 객체를 생성해야 한다.
	private List getList(Object object) throws InstantiationException, IllegalAccessException {
		Class clz = object.getClass();
		List result = Collections.emptyList();

		try {
			// Arrays.asTo(...) ArrayList 와 다른 타입이므로 변경한다.
			Class<?> arraysType = Class.forName("java.util.Arrays$ArrayList");
			if (arraysType.isAssignableFrom(clz)) {
				result = new ArrayList();
			} else {
				result = (List) object.getClass().newInstance();
			}

			for(Object value : (List) object) {
				result.add(getType(value));
			}
		} catch (ClassNotFoundException e) {
			logger.debug(e.getMessage());
		}

		return result;
	}

	private Set getSet(Object object) throws InstantiationException, IllegalAccessException {
		Set result = (Set) object.getClass().newInstance();

		for(Object value : (Set) object) {
			result.add(getType(value));
		}

		return result;
	}

	// Collection type 으로 합치기

	private Collection getCollection(Object object) throws InstantiationException, IllegalAccessException {
		Class clz = object.getClass();
		Collection result = Collections.emptyList();

		try {
			// Arrays.asTo(...) ArrayList 와 다른 타입이므로 변경한다.
			Class<?> arraysType = Class.forName("java.util.Arrays$ArrayList");
			if (arraysType.isAssignableFrom(clz)) {
				result = new ArrayList();
			} else {
				result = (Collection) object.getClass().newInstance();
			}

			for(Object value : (Collection) object) {
				result.add(getType(value));
			}
		} catch (ClassNotFoundException e) {
			logger.debug(e.getMessage());
		}

		return result;
	}
}
