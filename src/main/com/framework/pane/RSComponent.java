package com.framework.pane;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RSComponent {

	private final int ID;

	private RSWindow parent;
	private RSComponentSettings settings;
	private String text;
	private String[] options;
	private boolean hidden;
}
