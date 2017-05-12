package org.syaku.tutorials.spring.apps.validation.model;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.syaku.tutorials.spring.apps.validation.support.policy.Edit;

import javax.validation.constraints.*;
import java.util.Date;

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
	private String userId;

	@NotEmpty
	@Length(max = 50)
	private String password;

	@NotEmpty
	@Length(max = 50)
	private String password2;

	@NotEmpty
	@Length(max = 50)
	private String name;

	@NotNull @Min(1) @Max(200)
	private Integer age;

	@NotEmpty
	@Length(max = 1)
	private String sex;

	@Past
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthday;

	private String phone;

	@Email
	private String email;

	@NotNull
	@Size(min = 1, max = 2)
	private String[] hobby;

	@AssertTrue(message = "유요한 값이 아닙니다.")
	private boolean isSexFixed() {
		return "F,M".indexOf(this.sex) > -1;
	}

	@AssertTrue(message = "비밀번호가 일치하지 않습니다.")
	private boolean isPassowrdCompare() {
		if (this.password == null || this.password2 == null) {
			return false;
		}

		return this.password.equals(this.password2);
	}
}
