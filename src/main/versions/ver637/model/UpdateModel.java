package versions.ver637.model;

import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
public abstract class UpdateModel {

	private final UpdateFlag[] updateFlags;

	@Getter
	private int maskData;

	/**
	 * Constructs a new {@code UpdateModel}.
	 */
	public UpdateModel() {
		this.updateFlags = new UpdateFlag[18];
	}

	/**
	 * Prepares this model
	 */
	public abstract void prepare();

	/**
	 * Updates this model
	 */
	public abstract void update();

	/**
	 * This method is called after all models have been updated on the
	 * {@code GameProcessTick}.
	 */
	public abstract void reset();

	/**
	 * Registers the given {@code flag} to be updated for this {@code UpdateModel}.
	 * 
	 * @param updateFlag the flag to register
	 */
	public void registerFlag(UpdateFlag updateFlag) {
		this.updateFlags[updateFlag.ordinal()] = updateFlag;
		maskData |= updateFlag.mask();
	}

	/**
	 * Returns the array of {@code UpdateFlag} that is held in this
	 * {@code UpdateModel} for updating.
	 * 
	 * @return the current masks
	 */
	public UpdateFlag[] getFlags() {
		return this.updateFlags;
	}

	/**
	 * Returns the flag correlating to the given {@code ordinal}.
	 * 
	 * @param <T>
	 * 
	 * @param ordinal the correlating ordinal of the flag
	 * @return the flag relating to the ordinal
	 */
	public <T> T getFlag(int ordinal, Class<T> clazz) {
		return clazz.cast(this.updateFlags[ordinal]);
	}

	/**
	 * Finishes updating the masks of this {@code UpdateModel}.
	 */
	public final void finish() {
		reset();
		maskData = 0;
		for (int i = 0; i < this.updateFlags.length; i++)
			this.updateFlags[i] = null;
	}

	/**
	 * Checks if an update is required.
	 * 
	 * @return {@code True} if so, {@code false} if not.
	 */
	public boolean isUpdateRequired() {
		return maskData > 0;
	}

	/**
	 * Checks if a flag was registered based on the given ordinal
	 * 
	 * @param ordinal the ordinal of the flag
	 * @return true if the flag was registered, false if not.
	 */
	public boolean activated(int ordinal) {
		return this.updateFlags[ordinal] != null;
	}
}
