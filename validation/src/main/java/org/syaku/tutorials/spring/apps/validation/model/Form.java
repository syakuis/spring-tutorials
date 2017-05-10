package org.syaku.tutorials.spring.apps.validation.model;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 5. 10.
 */
@Data
public class Form {
	@NotEmpty
	private String name;
	@Min(1)
	private int age;
	@NotEmpty
	private String sex;
}
