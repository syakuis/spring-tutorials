package org.syaku.tutorials.java.reflection;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syaku.tutorials.spring.xss.support.reflection.ObjectRef;
import org.syaku.tutorials.spring.xss.support.reflection.ObjectRefConverter;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 18.
 */
public class ObjectControlTest {
	private static final Logger logger = LoggerFactory.getLogger(ObjectControlTest.class);

	ObjectRef ref;
	ObjectRef ref2;

	@Before
	public void setUp() {
		ObjectRefConverter converter = new NoAnnoObjectControlSupport();
		ref = new ObjectRef(converter);

		ObjectRefConverter converter2 = new XssFilterObjectControlSupport();
		ref2 = new ObjectRef(converter2);
	}

	@Test
	public void fooTest() throws InstantiationException, IllegalAccessException {
		Foo foo = new Foo();

		Foo foo1 = (Foo) ref.getType(foo);
		logger.debug("success {}", foo1);

		Foo foo11 = (Foo) ref2.getType(foo);
		logger.debug("success {}", foo11);

		foo.setName("syaku");
		foo.setNumber(1);
		foo.setStringArray(new String[]{ "string", "string2", "string3" });

		Set<String> set = new HashSet<>();
		set.add("good2");
		foo.setTooList(Arrays.asList(new Too("good", set)));

		Map<String, Integer> map = new HashMap<>();
		map.put("1", 1);
		map.put("2", 2);
		map.put("3", 3);
		foo.setStringMap(map);

		logger.debug("foo {}", foo.getName());
		logger.debug("foo {}", foo.getNumber());
		logger.debug("foo {}", foo.getTooList());
		logger.debug("foo {}", Arrays.asList(foo.getStringArray()).toString());
		logger.debug("foo {}", foo.getStringMap());
		logger.debug("foo {}", foo.getTooList());
		logger.debug("foo {}", foo.getTooListMap());

		Foo foo2 = (Foo) ref.getType(foo);
		logger.debug("success foo2 {}", foo2);

		Foo foo22 = (Foo) ref2.getType(foo);
		logger.debug("success foo22 {}", foo22);
		logger.debug("success foo {}", foo);

		//Map<String, Integer> map2 = foo22.getStringMap();
		//logger.debug("{}", map2.get("1"));


	}

	@Test
	public void method() throws NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InstantiationException {
		Foo foo = new Foo();

		Class clz = foo.getClass();
		Method method = clz.getMethod("getTest", String.class, List.class);
		Too too = new Too();

		Object[] args = new Object[]{ "syaku", Arrays.asList("a","a","a","a") };

		logger.debug("before Args => {}", Arrays.asList(args));

		ref2.getMethodParameter(method, args);

		logger.debug("after Args => {}", Arrays.asList(args));
	}

}
