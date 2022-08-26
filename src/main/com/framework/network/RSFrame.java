package com.framework.network;

import com.framework.io.RSStream;

import lombok.Getter;
import lombok.experimental.Accessors;

@Accessors(fluent = true)
public class RSFrame extends RSStream {

	/**
	 * Indicates that the frame is raw (no opcode)
	 */
	public static final int RawOpcode = -1;

	/**
	 * The standard header type
	 */
	public static final byte StandardType = 0;

	/**
	 * The byte header type
	 */
	public static final byte VarByteType = 1;

	/**
	 * The short header type
	 */
	public static final byte VarShortType = 2;

	@Getter
	private final short opcode;
	@Getter
	private final byte header;

	/**
	 * Constructs an {@code RSFrame} with the given {@code buffer} as the data for
	 * this frame.
	 * 
	 * @param opcode the opcode identifier
	 * @param buffer the data buffer
	 * @param header the header type
	 */
	public RSFrame(int opcode, byte[] buffer, byte header) {
		super(buffer);
		this.opcode = (short) opcode;
		this.header = header;
	}

	/**
	 * Constructs an {@code RSFrame} with the given {@code buffer} as the data for
	 * this frame. The header for this {@code RSFrame} will be set to
	 * {@value #StandardType} and the opcode will be set to -1;
	 * 
	 * @param buffer the data buffer
	 */
	public RSFrame(byte[] buffer) {
		super(buffer);
		this.opcode = RawOpcode;
		this.header = StandardType;
	}

	/**
	 * Constructs an {@code RSFrame} with an empty buffer and the given
	 * {@code opcode} as its opcode. The header for this {@code RSFrame} will be set
	 * to {@value #StandardType}
	 * 
	 * @param opcode the opcode identifier
	 */
	public RSFrame(int opcode) {
		this(new byte[16]);
	}

	/**
	 * Constructs an {@code RSFrame} with an empty buffer.
	 * 
	 * @param opcode the opcode identifier
	 * @param header the header type
	 */
	public RSFrame(int opcode, byte header) {
		this(opcode, new byte[16], header);
	}

	/**
	 * Constructs an {@code RSFrame} with an empty buffer and no opcode assigned, so
	 * it defaults to -1.
	 */
	public RSFrame() {
		this(new byte[16]);
	}

	/**
	 * Returns true if this {@code RSFrame} has an opcode of -1.
	 * 
	 * @return true if raw; false otherwise
	 */
	public boolean isRaw() {
		return this.opcode == -1;
	}

	/**
	 * Returns a new RSFrame with the given {@code opcode} but with the header set
	 * as {@link #VarShortType}.
	 * 
	 * @param opcode the opcode of the frame
	 * @return the new constructed frame
	 */
	public static RSFrame varShort(int opcode) {
		return new RSFrame(opcode, VarShortType);
	}

	/**
	 * Returns a new RSFrame with the given {@code opcode} but with the header set
	 * as {@link #VarByteType}.
	 * 
	 * @param opcode the opcode of the frame
	 * @return the new constructed frame
	 */
	public static RSFrame varByte(int opcode) {
		return new RSFrame(opcode, VarByteType);
	}

	/**
	 * Returns a new RSFrame with the given {@code opcode} but with the header set
	 * as {@link #StandardType}.
	 * 
	 * @param opcode the opcode of the frame
	 * @return the new constructed frame
	 */
	public static RSFrame standard(int opcode) {
		return new RSFrame(opcode, StandardType);
	}

	/**
	 * Returns a new RSFrame with an opcode of -1 and the header set as
	 * {@link #StandardType}.
	 * 
	 * @param opcode the opcode of the frame
	 * @return the new constructed frame
	 */
	public static RSFrame raw() {
		return new RSFrame(RawOpcode, StandardType);
	}

}
