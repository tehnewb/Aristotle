package versions.ver637.model;

import com.framework.network.RSFrame;

/**
 * Represents a type of flag, such as movement, facing, graphics, and animations
 * that will require updating for a player or npcs model.
 * 
 * @author Albert Beaupre
 */
public interface UpdateFlag {

	public static final byte GraphicOrdinal = 0;
	public static final byte TeleportOrdinal = 1;
	public static final byte ForceMoveOrdinal = 2;
	public static final byte FaceLocationOrdinal = 3;
	public static final byte FaceEntityOrdinal = 4;
	public static final byte DamageOrdinal = 5;
	public static final byte AnimateOrdinal = 6;
	public static final byte AppearanceOrdinal = 7;
	public static final byte ForceSayOrdinate = 8;
	public static final byte MovementOrdinal = 9;

	/**
	 * Returns the mask integer value for this mask
	 * 
	 * @return the mask data
	 */
	public abstract int mask();

	/**
	 * Returns the ordinal of this {@code mask}
	 * 
	 * @return the ordinal
	 */
	public abstract int ordinal();

	/**
	 * Writes the flag buffer to the given {@code block}.
	 */
	public abstract void write(RSFrame block);

}
