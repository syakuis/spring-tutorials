package org.syaku.tutorials.spring.xss.support.reflection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

/**
 * 리플랙션을 이용하여 객체를 조작한다.
 *
 * 1. 모든 타입의 객체를 변경한다.
 * 2. 원하는 어노테이션의 객체를 변경한다.
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
		return getValue(value, null, clazz);
	}

	/**
	 * 객체의 값을 수정한다.
	 *
	 * @param <T>        the type parameter
	 * @param value      the value
	 * @param annotation the annotation
	 * @param clazz      the clazz
	 * @return the value
	 */
	@SuppressWarnings("unchecked")
	public <T> T getValue(Object value, Annotation annotation, Class<T> clazz) {
		return clazz.cast(getType(value, annotation));
	}

	/**
	 * 메서드의 파라메터값을 수정한다.
	 *
	 * @param method the method
	 * @param args   the args
	 */
	public void getMethodParameter(Method method, Object[] args) {
		// v1.7
		int i = 0;
		Annotation[][] methodAnnotations = method.getParameterAnnotations();
		for (Annotation[] annotations : methodAnnotations) {
			for (Annotation annotation : annotations) {
				logger.debug("Method anno {}", annotation);
				logger.debug("Method anno {}", this.annotation);
				if (annotation != null && this.annotation.equals(annotation.annotationType())) {
					logger.debug("Method parameter before value {}", args[i]);
					// 배열은 reflection array 사용해야 데이터를 변경할 수 있다.
					// args[i] = getType(args[i], annotation);
					Array.set(args, i, getType(args[i], annotation));
					logger.debug("Method parameter after value {}", args[i]);
				}
			}
			i++;
		}

		// 메서드 파라메터 모든 어노에티션을 가져온다.
		// v1.8
		/*
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
		*/
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

	private Object getType(Object value) {
		return getType(value, null);
	}

	private Object getType(Object value, Annotation annotation) {
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