package org.syaku.tutorials.spring.xss.support.reflection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * 1. 리플랙션을 이용하여 데이터 출력
 * 2. 자료형의 깊숙한 데이터까지 어떻게 출력할까? 데이터 타입도 생각해야함.
 * 3. 자료형의 깊숙한 곳까지 데이터를 어떻게 변경할까? 데이터 타입도 생각해야함.
 * 4. 자료형의 깊숙한 곳까지 데이터를 변경은 했는 데.. 모든 데이터가 아닌 어노테이션에 해당하는 것들만 변경하기.
 *    자료형의 깊숙한 곳까지 어노테이션에 맞는 데이터를 변경할때 어노에티션의 값을 이용하여 처리하기.
 * 5. 기본형, 참조형 그리고 컬랙션(Primitive, Reference, Collection, Map)인 경우 어노테이션 설정을 무시하고 작동이 되어야한다.
 *    위와 같은 경우가 메서드인 경우이다. 즉 이미 어노테이션으로 검색이 된 데이터 객체를 넘겨주기때문에 어노테이션이 없는 상태로 넘어오게 된다.
 *    그래서 최초로 분석될 대상이 객체또는 메서드가 될 수 있어야 한다.
 * 6. aspect 에서 포인트 값이 최종적으로 변경되지 않음.
 * 7. primitive type autoboxing 주의 원래 타입으로 돌려주기
 * 7. 안정성 및 기존 데이터 타입을 유지했는 지
 *
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http ://syaku.tistory.com
 * @since 2017. 4. 24.
 */
public class ObjectRef {
	private static final Logger logger = LoggerFactory.getLogger(ObjectRef.class);

	private final ObjectRefConverter converter;
	private Class<? extends Annotation> annotation;

	public ObjectRef(ObjectRefConverter converter) {
		this.converter = converter;
		this.annotation = converter.getAnnotation();
	}

	@SuppressWarnings("unchecked")
	public <T> T getValue(Object value, Class<T> clazz) {
		return clazz.cast(getType(value, null));
	}

	@SuppressWarnings("unchecked")
	public <T> T getValue(Object value, Annotation annotation, Class<T> clazz) {
		return clazz.cast(getType(value, annotation));
	}

	public void getMethodParameter(Method method, Object[] args) {
		// 메서드 파라메터 모든 어노에티션을 가져온다.
		Parameter[] parameters = method.getParameters();

		int size = args.length;

		if (size != parameters.length) {
			throw new IndexOutOfBoundsException();
		}

		for (int i = 0; i < size; i++) {
			Annotation annotation = parameters[i].getAnnotation(this.annotation);
			if (annotation != null) {
				logger.debug("Method parameter before value {}", args[i]);
				// 배열은 reflection array 사용해야 데이터를 변경할 수 있다.
				// args[i] = getType(args[i], annotation);
				Array.set(args, i, getType(args[i], annotation));
				logger.debug("Method parameter after value {}", args[i]);
			}
		}
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

	public Object getType(Object value) {
		return getType(value, null);
	}

	public Object getType(Object value, Annotation annotation) {
		if (value == null) return null;
		Class clz = value.getClass();

		try {
			if (isWrapperType(clz) || clz == String.class) {
				logger.debug("Object Type {}, Annotation {}", clz.getName(), annotation);
				return converter.value(value, annotation);
			} else if (clz.isArray()) {
				return getArray(value);
			} else if (Collection.class.isAssignableFrom(clz)) {
				return getCollection(value);
			} else if (Map.class.isAssignableFrom(clz)) {
				return getMap(value);
			} else {
				return getObject(value);
			}
		} catch (IllegalAccessException iae) {
			logger.error(iae.getMessage(), iae);
		} catch (InstantiationException ie) {
			logger.error(ie.getMessage(), ie);
		}

		return value;
	}

	private Object getObject(Object object) throws IllegalAccessException, InstantiationException {
		Class clz = object.getClass();
		Field[] fields = clz.getDeclaredFields();

		logger.debug("in > {} ({}) {}", clz.getComponentType(), clz.getTypeName(), object.hashCode());

		for(Field field : fields) {
			field.setAccessible(true);
			Object value = field.get(object);
			Annotation annotation = null;
			boolean isAnnoType = true;

			if (this.annotation != null) {
				annotation = field.getAnnotation(this.annotation);
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
			}

			// annotation 조건이 있는 경우
			if (value != null && isAnnoType) {
				Object result = getType(value, annotation);
				logger.debug("change > anno: {}, value: {}, result: {}", isAnnoType, value, result);

				// primitive type 은 null 넣을 수 없다.
				if (result == null) {
					result = value;
				}

				field.set(object, result);
			}
		}

		logger.debug("out > @{} {}", object.hashCode(), object.getClass().getTypeName());

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

		Iterator<Map.Entry> iterator = map.entrySet().iterator();
		while(iterator.hasNext()) {
			Map.Entry entry = iterator.next();
			Object key = entry.getKey();
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