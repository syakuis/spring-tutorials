package org.syaku.tutorials.java.reflection;

import lombok.Data;
import lombok.ToString;
import org.syaku.tutorials.spring.xss.support.FilterXss;

import java.util.Set;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 20.
 */
@ToString
@Data
public class Too {
	private String name;
	@FilterXss
	private Set<String> stringArraySet;

	public Too() {
	}

	public Too(String name, Set<String> stringArraySet) {
		this.name = name;
		this.stringArraySet = stringArraySet;
	}
}
