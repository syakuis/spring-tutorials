package org.syaku.tutorials.reflection;

import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 17.
 */
@Data
@ToString
public class Foo {
	public String name;
	private int count = 2;
	private List<String> listString;
	private List<Integer> listInter;
	private List<List<Too>> listToo;
	private Set<Too> setToo;
	private Set<String> setTypeString;
	private Too too;
	private Map<String, Integer> map;
	private Map<String, Too> mapToo;
	private Map<String, List<String>> mapString;
	private Too[] toos;
	private String[] strs;
}
