package org.syaku.spring.tutorials.reflection.test2;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 18.
 */
public class ArrayListTest {
	private static final Logger logger = LoggerFactory.getLogger(ArrayListTest.class);


	@Test
	public void test() throws InstantiationException, IllegalAccessException {
		List<List<String>> result = Arrays.asList(
				Arrays.asList("good1", "good2", "good3", "good4"),
				Arrays.asList("good1", "good2", "good3", "good4")
		);
		logger.debug("result @{} ({}) {}", result.hashCode(), result.getClass(), result);
		List<String> result2 = getList(result);
		logger.debug(" + result @{} ({}) {} // {}", result2.hashCode(), result2.getClass(), result2);
	}

	public static <T> List<T> createListOfType(Class<T> type) {
		return new ArrayList<>();
	}

	private Object replaceStr(Object object) {
		logger.debug("{}", object);
		if (object != null && object.getClass() == String.class) {
			return ((String) object).replaceAll("good", "GOOD");
		}

		return object;
	}

	private List getList(List object) throws InstantiationException, IllegalAccessException {
		List result = new ArrayList<>();

		for(Object item : object) {
			Class itemClz = item.getClass();

			if (List.class.isAssignableFrom(itemClz)) {
				result.add(getList((List) item));
			} else {
				result.add(replaceStr(item));
			}
		}

		return result;
	}
}
