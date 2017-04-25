package org.syaku.tutorials.spring.xss.support;

import lombok.Data;
import lombok.ToString;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 25.
 */
@ToString
@Data
public class ReferenceType {
	private String str;

	private Integer aNumber;
	private int number;

	private boolean bool;
	private Boolean aBool;

	private float flo;
	private Float aFlo;

	private Long aLon;
	private long lon;
}
