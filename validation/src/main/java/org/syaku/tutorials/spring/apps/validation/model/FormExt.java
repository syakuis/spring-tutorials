package org.syaku.tutorials.spring.apps.validation.model;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 5. 12.
 */
@ToString
@Data
public class FormExt {
	@NotEmpty
	@Length(max = 50)
	private String name;
	@Length(max = 100)
	private String value;
}
