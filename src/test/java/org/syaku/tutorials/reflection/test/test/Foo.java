package org.syaku.tutorials.reflection.test.test;

import lombok.Data;
import lombok.ToString;
import org.syaku.tutorials.spring.xss.support.Defence;

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
	@Defence
	private List<String> lists;

	@Defence
	private List<Too> toos;

	@Defence
	private Too too;

	@Defence
	private Map<String, String> map;

	public void putMap(String name, String value) {
		map.put(name, value);
	}
}
