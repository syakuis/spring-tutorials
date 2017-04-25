package org.syaku.tutorials.reflection.test.test;

import lombok.Data;
import lombok.ToString;
import org.syaku.tutorials.spring.xss.support.Defence;

import java.util.List;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 15.
 */
@ToString
@Data
public class Too {
	@Defence
	private String name;
	private List<String> too2s;

	public Too() {
	}

	public Too(String name) {
		this.name = name;
	}
}
