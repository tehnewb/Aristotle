package com.framework.tick;

import lombok.Getter;

public abstract class RSGameTick extends RSTick {

	public static final long GameTickDuration = 600L;

	@Getter
	private final String name;

	public RSGameTick(String name) {
		this.delay(1);
		this.name = name;
	}

	@Override
	public RSGameTick delay(long delay) {
		return RSGameTick.class.cast(super.delay(delay * GameTickDuration));
	}

}
