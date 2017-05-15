package org.syaku.tutorials.spring.boot;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 13.
 */
@Configuration
@EnableAspectJAutoProxy
@ComponentScan(
		basePackages = "org.syaku.tutorials.spring.boot.config.**",
		useDefaultFilters = false,
		includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class Bootstrap {

}
