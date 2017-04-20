package org.syaku.tutorials.java.reflection;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 18.
 */
public class ObjectControlTest {
	private static final Logger logger = LoggerFactory.getLogger(ObjectControlTest.class);

	ObjectControl objectControl;
	ObjectControl objectControl2;

	@Before
	public void setUp() {
		ObjectControlSupport objectControlSupport = new NoAnnoObjectControlSupport();
		objectControl = new ObjectControl(objectControlSupport);

		ObjectControlSupport objectControlSupport2 = new XssFilterObjectControlSupport();
		objectControl2 = new ObjectControl(objectControlSupport2);
	}

	@Test
	public void fooTest() throws InstantiationException, IllegalAccessException {
		Foo foo = new Foo();

		Foo foo1 = (Foo) objectControl.getType(foo);
		logger.debug("success {}", foo1);

		Foo foo11 = (Foo) objectControl2.getType(foo);
		logger.debug("success {}", foo11);

		foo.setName("syaku");
		foo.setNumber(1);
		foo.setStringArray(new String[]{ "string", "string2", "string3" });

		logger.debug("foo {}", foo.getName());
		logger.debug("foo {}", foo.getNumber());
		logger.debug("foo {}", Arrays.asList(foo.getStringArray()).toString());
		logger.debug("foo {}", foo.getStringMap());
		logger.debug("foo {}", foo.getTooList());
		logger.debug("foo {}", foo.getTooListMap());

		Foo foo2 = (Foo) objectControl.getType(foo);
		logger.debug("success {}", foo2);

		Foo foo22 = (Foo) objectControl2.getType(foo);
		logger.debug("success {}", foo22);


	}

}
