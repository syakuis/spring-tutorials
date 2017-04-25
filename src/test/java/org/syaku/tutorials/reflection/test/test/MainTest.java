package org.syaku.tutorials.reflection.test.test;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 15.
 */
public class MainTest {
	private static final Logger logger = LoggerFactory.getLogger(MainTest.class);

	private Foo foo;

	@Before
	public void setUp() {

		foo = new Foo();
		//foo.setLists(Arrays.asList("list-1","list-2","list-3","list-4"));
		/*foo.setMap(new HashMap<>());
		foo.putMap("map-key-1", "1");
		foo.putMap("map-key-2", "1");
		foo.putMap("map-key-3", "1");
		foo.putMap("map-key-4", "1");
		foo.setToos(Arrays.asList(
				new Too("list-too-1"),
				new Too("list-too-2"),
				new Too("list-too-3"),
				new Too("list-too-4")
		));
*/
		//logger.debug(foo.toString());
	}

	@Test
	public void collection() throws IllegalAccessException {
		Class clz = foo.getClass();
		logger.debug("instance {}", clz.isInstance(foo));
		Field[] fields = clz.getDeclaredFields();

		for (Field field : fields) {
			Class fieldClz = field.getClass();

			logger.debug("{} instance {}", field.getName(), field.getType());

			field.setAccessible(true);
			Object fieldObject = field.get(foo);

			field(foo, field);

			if (Map.class.isAssignableFrom(field.getType())) {
				logger.debug("(Map) {}", field.getName());
			}
		}
	}

	private void field(Object object, Field field) throws IllegalAccessException {

		if (Collection.class.isAssignableFrom(field.getType())) {
			Class clz = field.getClass();
			logger.debug("(Collection) {}", field.getName());
			logger.debug("{} ------------------> {}", field.getType(), (clz == field.getType()));
			field.setAccessible(true);
			Object value = field.get(foo);
			logger.debug("(value) {}", value);

		//	if (value == null) return;

			Collection collection = (Collection) value;
			/*for(Object fieldObject : collection.toArray()) {
				Class collectionClz = fieldObject.getClass();
				logger.debug(collectionClz.getName());

				Field[] fields = collectionClz.getDeclaredFields();

				for(Field field2 : fields) {
					logger.debug("(field2) {} : {}", field2.getName(), field2.getType());
					if (Collection.class.isAssignableFrom(field2.getType())) {
						field(object, field2);
					}
				}
			}*/
		}
	}

	//@Test
	public void test() throws IllegalAccessException{
		Class clz = foo.getClass();

		Field[] fields = clz.getDeclaredFields();

		for (Field field : fields) {
			//logger.debug("{} field name", field.getName());

			// field class
			field.getClass();

			Type genericType = field.getGenericType();


			// collection controller
			//if (Collection.class.isAssignableFrom(field.getType())) {


			// 제너릭인지 판단한다.
			if (genericType instanceof ParameterizedType) {
				ParameterizedType parameterizedType = (ParameterizedType) genericType;

				Type[] typeArguments = parameterizedType.getActualTypeArguments();

				for(Type typeArgument : typeArguments) {
					Class<?> typeClass = (Class<?>) typeArgument;
					logger.debug("field -> {} : type -> {}", field.getName(), typeClass.getName());
				}
			}

			// collection check
			//if (Collection.class.isAssignableFrom(field.getType())) {

				/*
				List<String>
				List<String, String>
				Map<String, List<String>>
				Foo<List<String>>

				 */

				/*
				logger.debug("-----------------> collection type");
				logger.debug("{} field type", field.getType().getName());

				// generic type
				Type type = field.getGenericType();
				logger.debug("{} generic type", type.getTypeName());

				// field get value
				field.setAccessible(true);
				logger.debug("field value --> {} ", field.get(foo));
				*/
			//}
		}
	}
}
