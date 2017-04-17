package org.syaku.spring.tutorials.aspectj.xss.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;
import org.syaku.spring.tutorials.aspectj.xss.support.XssClean;
import org.syaku.spring.tutorials.aspectj.xss.support.XssType;

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
	@XssClean(XssType.DOM)
	private String filter;
	@XssClean(XssType.SAX)
	private String saxFilter;
	private String name;
	private int count;
	@XssClean(XssType.ESCAPE)
	private String escape;
	@XssClean(XssType.ESCAPE)
	private List<String> lists;
}
