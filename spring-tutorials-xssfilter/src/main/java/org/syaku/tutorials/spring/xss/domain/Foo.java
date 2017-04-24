package org.syaku.tutorials.spring.xss.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;
import org.syaku.tutorials.spring.xss.support.FilterXss;
import org.syaku.tutorials.spring.xss.support.XssType;

import java.util.List;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 13.
 */
@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Foo {
	@FilterXss(XssType.DOM)
	private String filter;
	@FilterXss(XssType.SAX)
	private String saxFilter;
	private String name;
	private int count;
	@FilterXss(XssType.ESCAPE)
	private String escape;
	@FilterXss(XssType.ESCAPE)
	private List<String> lists;
}
