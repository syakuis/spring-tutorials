package org.syaku.tutorials.java.reflection.model;

import lombok.Data;
import lombok.ToString;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 21.
 */
@ToString
@Data
public class Foo {
	private String name;
	private boolean use;

	public void setName(@Anno String name) {
		this.name = name;
	}
}
