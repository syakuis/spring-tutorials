package org.syaku.tutorials.reflection.test.test;

import lombok.Data;
import lombok.ToString;
import org.syaku.tutorials.spring.xss.support.FilterXss;

import java.util.List;
import java.util.Map;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 15.
 */
@ToString
@Data
public class Foo {
	@FilterXss
	private List<String> lists;

	@FilterXss
	private List<Too> toos;

	@FilterXss
	private Too too;

	@FilterXss
	private Map<String, String> map;

	public void putMap(String name, String value) {
		map.put(name, value);
	}
}
