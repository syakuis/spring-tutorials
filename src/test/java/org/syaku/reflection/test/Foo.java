package org.syaku.reflection.test;

import lombok.Data;
import lombok.ToString;
import org.syaku.spring.tutorials.aspectj.xss.support.XssClean;

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
	@XssClean
	private List<String> lists;

	@XssClean
	private List<Too> toos;

	@XssClean
	private Too too;

	@XssClean
	private Map<String, String> map;

	public void putMap(String name, String value) {
		map.put(name, value);
	}
}
