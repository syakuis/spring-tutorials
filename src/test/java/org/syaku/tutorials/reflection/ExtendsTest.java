package org.syaku.tutorials.reflection;

import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 클래스 상속된 필드까지 출력하기.
 *
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 27.
 */
public class ExtendsTest {
	private static final Logger logger = LoggerFactory.getLogger(ExtendsTest.class);


	@Test
	public void test() {
		Man man = new Man("ggg");

		Class<?> clz = man.getClass();

		Class<?> superClz = clz.getSuperclass();

		Class<?> superClz2 = superClz.getSuperclass();
		Class<?> superClz3 = superClz2.getSuperclass();

		logger.debug("{}", superClz);
		logger.debug("{}", superClz2);
		logger.debug("{}", superClz3);

		final List<Field> allFields = FieldUtils.getAllFieldsList(Man.class);

		for (Field field : allFields) {
			logger.debug(field.getName());
		}
	}
}

@ToString
@Data
class Man extends Boy {
	private String manName;

	public Man(String boyName3) {
		super(boyName3);
	}
}

@ToString
@Data
class Boy extends Child {
	private String boyName;
	public String boyName2;
	public final String boyName3;

	public Boy(String boyName3) {
		this.boyName3 = boyName3;
	}
}

@ToString
@Data
class Child {
	private String childName;
}