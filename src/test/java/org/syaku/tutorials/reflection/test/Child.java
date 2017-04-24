package org.syaku.tutorials.reflection.test;

import lombok.Data;
import lombok.ToString;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 14.
 */
@Data
@ToString
public class Child {
	String name;
	String type;

	public Child() {
	}

	public Child(String name, String type) {
		this.name = name;
		this.type = type;
	}
}
