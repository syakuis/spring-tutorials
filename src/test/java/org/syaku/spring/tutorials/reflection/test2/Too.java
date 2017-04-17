package org.syaku.spring.tutorials.reflection.test2;

import lombok.Data;
import lombok.ToString;
import org.syaku.spring.tutorials.aspectj.xss.support.XssClean;

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
	@ExString
	@ValueString
	List<String> names;

	public Too() {
	}

	public Too(String name, List<String> names) {
		this.name = name;
		this.names = names;
	}
}
