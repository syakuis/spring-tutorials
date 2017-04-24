package org.syaku.tutorials.reflection;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 17.
 */
@Data
@ToString
public class Too {
	String name;
	List<String> names;

	public Too() {
	}

	public Too(String name, List<String> names) {
		this.name = name;
		this.names = names;
	}
}
