package org.syaku.spring.tutorials.aspectj.xss.domain;

import lombok.Data;
import lombok.ToString;
import org.syaku.spring.tutorials.aspectj.xss.support.XssFilter;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 13.
 */
@Data
@ToString
public class Foo {
	@XssFilter
	private String filter;
	private String noFilter;
	private String name;
	private int count;
}
