package org.syaku.tutorials.java.reflection;

import lombok.Data;
import lombok.ToString;

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
	private List<Too> tooList;
	private String[] stringArray;
	private Map<String, String> stringMap;
	private Map<String, List<Too>> tooListMap;
}
