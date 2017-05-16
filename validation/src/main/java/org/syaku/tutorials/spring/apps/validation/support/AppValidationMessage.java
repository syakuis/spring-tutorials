package org.syaku.tutorials.spring.apps.validation.support;

import org.springframework.context.support.MessageSourceAccessor;
import org.syaku.tutorials.spring.validation.ValidationMessage;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 5. 16.
 */
public class AppValidationMessage implements ValidationMessage {
	private final MessageSourceAccessor messageSourceAccessor;

	public AppValidationMessage(MessageSourceAccessor messageSourceAccessor) {
		this.messageSourceAccessor = messageSourceAccessor;
	}

	@Override
	public String getFieldName(String field) {
		return messageSourceAccessor.getMessage("text.field." + field, field);
	}

	@Override
	public String getBindingFailure(String message) {
		return messageSourceAccessor.getMessage("text.valid.Invalid", message);
	}
}
