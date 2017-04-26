package org.syaku.tutorials.spring.xss.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;
import org.syaku.tutorials.spring.xss.support.Defence;
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
@Defence(XssType.DOM)
public class Foo {
	private String filter;
	private String saxFilter;
	private String name;
	private int count;
	private String escape;
	@Defence(XssType.ESCAPE)
	private List<String> lists;
}
