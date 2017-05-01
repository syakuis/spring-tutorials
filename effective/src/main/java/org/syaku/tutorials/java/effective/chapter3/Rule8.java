package org.syaku.tutorials.java.effective.chapter3;

/**
 * 규칙 8 equals 를 재정의할 때는 일반 규약을 따르라
 *
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 25.
 */
public class Rule8 {

	public static void main(String[] args) {
		String name = "syaku";
		String name2 = "syaku";
		name2 = "3333";

		System.out.println(name.hashCode());
		System.out.println(name2.hashCode());

		System.out.println(name.equals(name2));
	}
}
