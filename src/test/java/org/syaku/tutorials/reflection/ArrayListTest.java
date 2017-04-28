package org.syaku.tutorials.reflection;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syaku.tutorials.spring.xss.support.reflection.ObjectRef;
import org.syaku.tutorials.spring.xss.support.reflection.ObjectRefConverter;

import java.util.*;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 18.
 */
public class ArrayListTest {
	private static final Logger logger = LoggerFactory.getLogger(ArrayListTest.class);

	ObjectRef ref;

	@Before
	public void setUp() {
		ObjectRefConverter converter = new XssFilterConverter();
		ref = new ObjectRef(converter);
	}

	@Test
	public void fooTest() throws InstantiationException, IllegalAccessException {
		Foo foo = new Foo();

		Map mapToo = new HashMap();
		mapToo.put("111", new Too());
		mapToo.put("112",  new Too("Map Too good - 1", Arrays.asList("Map Too List - 1","Map Too List - 1","Map Too List - 1","Map Too List - 1","Map Too List - 1")));
		mapToo.put("113",  new Too());
		foo.setMapToo(mapToo);

		Foo foo2 = (Foo) ref.getValue(foo, Foo.class);

		logger.debug("foo {} {}", foo.getClass().getTypeName(), foo.toString());
		logger.debug("foo2 {} {}", foo2.getClass().getTypeName(), foo2.toString());
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

		Object object = ref.getValue(list, Object.class);



		logger.debug("{}", object);
		logger.debug("{}", object.getClass().getTypeName());

	}

	public void test() throws InstantiationException, IllegalAccessException {
		List<List<String>> result = Arrays.asList(
				Arrays.asList("good1", "good2", "good3", "good4"),
				Arrays.asList("good1", "good2", "good3", "good4")
		);
		logger.debug("result @{} ({}) {}", result.hashCode(), result.getClass(), result);
		List<String> result2 = (List) ref.getValue(result, List.class);
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

		Foo foo2 = ref.getValue(foo, Foo.class);
		logger.debug(" + result @{} ({}) {} // {}", foo2.hashCode(), foo2.getClass(), foo2);
	}

}
