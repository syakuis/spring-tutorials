package org.syaku.tutorials.java.effective.chapter3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syaku.tutorials.java.effective.chapter3.model.Foo;

/**
 * 3장 모든 객체의 공통 메서드
 *
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 25.
 */
public class Chapter3 {
	private static final Logger logger = LoggerFactory.getLogger(Chapter3.class);

	public static void main(String[] args) {
		// Object 는 객체 생성이 가능한 클래스이긴 하지만...
		Object object = new Object();

		logger.debug("equals {}", object.equals(object));
		logger.debug("hashCode {}", object.hashCode());
		logger.debug("toString {}", object.toString());

		Class clz = object.getClass();
		Foo foo2 = (Foo) clz.cast(new Foo());

		logger.debug("equals {}", foo2.equals(object));
		logger.debug("hashCode {}", foo2.hashCode());
		logger.debug("toString {}", foo2.toString());

		Foo foo = new Foo();

		logger.debug("equals {}", foo.equals(foo));
		logger.debug("hashCode {}", foo.hashCode());
		logger.debug("toString {}", foo.toString());

		// gc에 의해 호출되는 메서드
		// foo.finalize();
	}
}
