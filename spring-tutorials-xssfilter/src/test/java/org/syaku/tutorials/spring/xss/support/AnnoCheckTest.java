package org.syaku.tutorials.spring.xss.support;

import lombok.Data;
import lombok.ToString;
import org.junit.Test;
import org.syaku.tutorials.spring.xss.support.reflection.ObjectRef;
import org.syaku.tutorials.spring.xss.support.reflection.ObjectRefConverter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 26.
 */
public class AnnoCheckTest {

	@Test
	public void test() {
		Foo foo = new Foo();
		foo.setList(Arrays.asList("a", "a", "a", "a"));
		foo.setName("good");

		Map<String, Too> map = new HashMap<>();
		map.put("good", new Too("aaa", 1));
		foo.setMap(map);

		ObjectRefConverter converter = new AnnoTypeConverter();
		ObjectRef ref = new ObjectRef(converter);
		ref.getValue(foo, Foo.class);
	}


}

@ToString
@Data
@Anno
class Foo {
	private String name;
	private List<String> list;
	private Map<String, Too> map;
}

@ToString
@Data
@Anno
class Too {
	private String name;
	private int number;

	public Too(String name, int number) {
		this.name = name;
		this.number = number;
	}
}