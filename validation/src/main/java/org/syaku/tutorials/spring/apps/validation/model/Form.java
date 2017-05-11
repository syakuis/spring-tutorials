package org.syaku.tutorials.spring.apps.validation.model;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.syaku.tutorials.spring.apps.validation.support.policy.Edit;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 5. 10.
 */
@ToString
@Data
public class Form {
	@NotEmpty(groups = Edit.class)
	private String idx;
	@NotEmpty
	@Length(max = 50)
	private String name;
	@Min(1)
	@Max(200)
	private int age;
	@NotEmpty
	@Length(max = 1)
	private String sex;
}
