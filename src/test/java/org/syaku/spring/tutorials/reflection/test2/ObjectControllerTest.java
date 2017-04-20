package org.syaku.spring.tutorials.reflection.test2;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syaku.spring.tutorials.aspectj.xss.support.ObjectControl;
import org.syaku.spring.tutorials.aspectj.xss.support.ObjectControlSupport;

import java.util.*;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 18.
 */
public class ObjectControllerTest {
	private static final Logger logger = LoggerFactory.getLogger(ObjectControllerTest.class);

	ObjectControl objectControl;

	@Before
	public void setUp() {
		ObjectControlSupport objectControlSupport = new XssFilterObjectControlSupport();
		objectControl = new ObjectControl(objectControlSupport);
	}

	@Test
	public void fooTest() throws InstantiationException, IllegalAccessException {
		Foo foo = new Foo();

		foo.setName("syaku");
		foo.setCount(10000);
		foo.setListString(Arrays.asList("Arrays.ArrayList - syaku - 1", "Arrays.ArrayList - syaku - 2"));
		foo.setListInter(Arrays.asList(10000, 222222, 33333));

		foo.setListToo(Arrays.asList(Arrays.asList(
				new Too("Arrays.ArrayList  good Too - 1",
						Arrays.asList("ArrayList  good Too - 1","ArrayList Too - 1","ArrayList Too - 1","ArrayList Too - 1","ArrayList Too - 1")),
				new Too("Arrays.ArrayList Too - 2", null),
				new Too("Arrays.ArrayList Too - 3", null),
				new Too("Arrays.ArrayList Too - 4", null)
		),Arrays.asList(
				new Too(),
				new Too("Arrays.ArrayList Too - 2", null),
				new Too("Arrays.ArrayList Too - 3", null),
				new Too("Arrays.ArrayList Too - 4", null)
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

		Foo foo2 = (Foo) objectControl.getType(foo);

		logger.debug("{}", foo2);
	}

}
