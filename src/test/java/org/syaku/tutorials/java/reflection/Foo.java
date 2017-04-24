package org.syaku.tutorials.java.reflection;

import lombok.Data;
import lombok.ToString;
import org.syaku.tutorials.spring.xss.support.FilterXss;

import java.util.List;
import java.util.Map;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 20.
 */
@ToString
@Data
public class Foo {

	private String name;
	private int number;
	@FilterXss
	private List<Too> tooList;
	private String[] stringArray;
	private Map<String, Integer> stringMap;
	private Map<String, List<Too>> tooListMap;

	public void getTest(@FilterXss String name, @FilterXss List<String> lists) {
		System.out.println(name);
	}
}
