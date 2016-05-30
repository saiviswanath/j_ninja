package com.xyz.propertyeditors;

import java.beans.PropertyEditorSupport;

import org.springframework.util.StringUtils;

public class LongPropertyEditor extends PropertyEditorSupport {
	private final boolean allowEmpty;

	public LongPropertyEditor(boolean allowEmpty) {
		this.allowEmpty = allowEmpty;
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (allowEmpty && !StringUtils.hasText(text)) {
			setValue(null);
		} else {
			setValue(text);
		}
	}

}
