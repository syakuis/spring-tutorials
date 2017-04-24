package org.syaku.tutorials.reflection.test;

import lombok.Data;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 14.
 */
@Data
@ToString
public class Foo {
	private String name;
	private List<String> labels;
	private List<Child> childList;

	public void labelsArray2List(String... label) {
		this.labels = Arrays.asList(label);
	}

	public void childArray2List(Child... child) {
		childList = Arrays.asList(child);
	}
}
