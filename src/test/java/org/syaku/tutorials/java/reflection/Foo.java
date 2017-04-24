package org.syaku.tutorials.java.reflection;

import lombok.Data;
import lombok.ToString;
import org.syaku.spring.tutorials.aspectj.xss.support.XssClean;

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
	@XssClean
	private List<Too> tooList;
	private String[] stringArray;
	private Map<String, Integer> stringMap;
	private Map<String, List<Too>> tooListMap;

	public void getTest(@XssClean String name, @XssClean List<String> lists) {
		System.out.println(name);
	}
}
