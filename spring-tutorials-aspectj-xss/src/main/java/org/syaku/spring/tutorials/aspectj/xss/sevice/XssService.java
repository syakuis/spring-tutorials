package org.syaku.spring.tutorials.aspectj.xss.sevice;

import org.springframework.stereotype.Service;
import org.syaku.spring.tutorials.aspectj.xss.support.XssFilter;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 14.
 */
@Service
public class XssService {
	@XssFilter
	public void test() {
		System.out.println("mm,.mm");
	}
}
