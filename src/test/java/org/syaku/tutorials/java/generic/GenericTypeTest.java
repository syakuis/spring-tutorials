package org.syaku.tutorials.java.generic;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 20.
 */
public class GenericTypeTest {
	private static final Logger logger = LoggerFactory.getLogger(GenericTypeTest.class);

	@Test
	public void generic존재유무에따른테스트() {
		List list = new ArrayList();

		List<String> list2 = new ArrayList<>();

		logger.debug("{}", list2.getClass().getGenericSuperclass());
		logger.debug("{}", ((ParameterizedType) list2.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);


	}
}
