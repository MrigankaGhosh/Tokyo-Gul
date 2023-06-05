package com.vending.machine.tokyogul.dto;

import java.util.List;

import org.springframework.context.annotation.Configuration;

@Configuration
public class StringListDTO {
	private List<String> strings;

	public List<String> getStrings() {
		return strings;
	}

	public void setStrings(List<String> strings) {
		this.strings = strings;
	}

}
