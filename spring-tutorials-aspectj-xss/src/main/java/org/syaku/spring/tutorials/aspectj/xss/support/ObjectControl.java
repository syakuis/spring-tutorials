package org.syaku.spring.tutorials.aspectj.xss.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

/**
 * 1. 리플랙션을 이용하여 데이터 출력
 * 2. 깊숙한 데이터까지 어떻게 출력할까? 데이터 타입도 생각해야함.
 * 3. 깊숙한 곳까지 데이터를 어떻게 변경할까? 데이터 타입도 생각해야함.
 * 4. 깊숙한 곳까지 데이터를 변경은 했는 데.. 모든 데이터가 아닌 어노테이션에 해당하는 것들만 변경하기.
 * 깊숙한 곳까지 어노테이션에 맞는 데이터를 변경할때 어노에티션의 값을 이용하여 처리하기.
 * 5. 안정성 및 기존 데이터 타입을 유지했는 지
 *
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 20.
 */
public class ObjectControl {
	private static final Logger logger = LoggerFactory.getLogger(ObjectControl.class);

	private final ObjectControlSupport objectControlSupport;
	private Class<? extends Annotation> annotation;

	public ObjectControl(ObjectControlSupport objectControlSupport) {
		this.objectControlSupport = objectControlSupport;
		this.annotation = objectControlSupport.getAnnotation();
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

	public Object getType(Object value) throws IllegalAccessException, InstantiationException {
		return getType(value, null);
	}

	public Object getType(Object value, Annotation annotation) throws IllegalAccessException, InstantiationException {
		if (value == null) return null;
		Class clz = value.getClass();

		if (isWrapperType(clz) || clz == String.class) {
			return objectControlSupport.value(value, annotation);
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
			Annotation annotation = field.getAnnotation(this.annotation);
			boolean isAnnoType = true;

			if (annotation != null && this.annotation.equals(annotation.annotationType())) {
				isAnnoType = this.annotation.equals(annotation.annotationType());

				if (logger.isDebugEnabled()) {
					logger.debug("Field Annotation {} :: {} = {} equals {}",
							field.getName(),
							isAnnoType,
							this.annotation,
							annotation.annotationType()
					);
				}
			}

			// annotation 조건이 있는 경우
			if (value != null && isAnnoType) {
				field.set(object, getType(value, annotation));
			}
		}

		return object;
	}

	private Object[] getArray(Object object) throws IllegalAccessException, InstantiationException {
		Object[] objects = (Object[]) object;
		int count = objects.length;

		Object[] result = (Object[]) Array.newInstance(object.getClass().getComponentType(), count);

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
