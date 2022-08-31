package com.framework.tick;

public abstract class RSGameTick extends RSTick {

	public static final long GameTickDuration = 600L;

	public RSGameTick() {
		this.delay(1);
	}

	@Override
	public RSGameTick delay(long delay) {
		return RSGameTick.class.cast(super.delay(delay * GameTickDuration));
	}

}
