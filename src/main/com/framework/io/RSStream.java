package com.framework.io;

import java.nio.BufferUnderflowException;
import java.util.Arrays;
import java.util.function.Function;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * The {@code RSStream} class allows for easy reading and writing from a byte
 * buffer that contains data used by anything relating to the RuneScape cache or
 * network.
 * 
 * @author Albert Beaupre
 */
@Accessors(fluent = true, chain = true)
public class RSStream {

	// This function is used for writing bits to a stream
	private static final Function<Integer, Integer> BitMaskFunction = index -> (1 << index) - 1;

	@Getter
	private byte[] buffer; // the bytes holding all data

	@Getter
	@Setter
	private int position; // the index at when reading the buffer

	@Getter
	private boolean writing;

	private int bitIndex;

	/**
	 * Constructs a new {@code RStream} with the given {@code buffer} as the buffer
	 * for reading and writing to/from.
	 */
	public RSStream(@NonNull byte[] buffer) {
		this.buffer = buffer;
	}

	/**
	 * Constructs an empty {@code RSStream} with an initial capacity of the given
	 * {@code arrayLength}.
	 */
	public RSStream(int arrayLength) {
		this(new byte[arrayLength]);
	}

	/**
	 * Constructs an empty {@code RSStream} with an initial capacity of 256 bytes.
	 */
	public RSStream() {
		this(new byte[256]);
	}

	/**
	 * Checks for reader and writer index issues
	 */
	private final void ensureCapacity() {
		if (this.position >= this.buffer.length)
			this.buffer = Arrays.copyOf(this.buffer, this.buffer.length + 16);
	}

	/**
	 * Sets the bit index based on the current writer index. This method should be
	 * called before calling {@link #writeBits(int, int)} .
	 * 
	 * @return this current instance, used for chaining
	 */
	public RSStream beginBitAccess() {
		bitIndex = position * Byte.SIZE;
		return this;
	}

	/**
	 * Sets the writer index based on the current bit index. The method should be
	 * called once finishing calling {@link #writeBits(int, int)}.
	 * 
	 * @return this current instance, used for chaining
	 */
	public RSStream finishBitAccess() {
		position = (bitIndex + 7) / Byte.SIZE;
		return this;
	}

	/**
	 * Writes the given {@code value} with the {@code numBits}.
	 * 
	 * @param numBits the number of bits
	 * @param value   the value to write
	 * @return this current instance, used for chaining
	 */
	public RSStream writeBits(int numBits, int value) {
		if (this.position == 0) {
			this.writing = true;
		} else if (this.position > 0 && !this.writing) {
			throw new IllegalStateException("flip() must be called to change from reading to writing on this buffer");
		}

		int bytePos = bitIndex >> 3;
		int bitOffset = 8 - (bitIndex & 7);

		bitIndex += numBits;
		position = (bitIndex + 7) / Byte.SIZE;
		ensureCapacity();

		byte b;
		for (; numBits > bitOffset; bitOffset = Byte.SIZE) {
			b = (byte) getByte(bytePos);
			setByte(bytePos, (byte) (b & ~BitMaskFunction.apply(bitOffset)));
			setByte(bytePos++, (byte) (b | (value >> (numBits - bitOffset)) & BitMaskFunction.apply(bitOffset)));
			numBits -= bitOffset;
		}
		b = (byte) getByte(bytePos);
		if (numBits == bitOffset) {
			setByte(bytePos, (byte) (b & ~BitMaskFunction.apply(bitOffset)));
			setByte(bytePos, (byte) (b | value & BitMaskFunction.apply(bitOffset)));
		} else {
			setByte(bytePos, (byte) (b & ~(BitMaskFunction.apply(numBits) << (bitOffset - numBits))));
			setByte(bytePos, (byte) (b | (value & BitMaskFunction.apply(numBits)) << (bitOffset - numBits)));
		}
		return this;
	}

	/**
	 * Returns the byte read in the buffer at the current reader index and increases
	 * the reader index by 1.
	 * 
	 * @return the byte read
	 */
	public byte readByte() {
		if (position >= this.buffer.length)
			throw new BufferUnderflowException();
		return buffer[position++];
	}

	/**
	 * Returns the a-type byte read in the buffer at the current reader index and
	 * increases the reader index by 1.
	 * 
	 * @return the byte read
	 */
	public byte readByteA() {
		return (byte) (readByte() - 128);
	}

	/**
	 * Returns the s-type byte read in the buffer at the current reader index and
	 * increases the reader index by 1.
	 * 
	 * @return the byte read
	 */
	public byte readByteS() {
		return (byte) (128 - readByte());
	}

	/**
	 * Returns the unsigned byte read in the buffer at the current reader index and
	 * increases the reader index by 1.
	 * 
	 * @return the unsigned byte read
	 */
	public int readUnsignedByte() {
		return readByte() & 0xFF;
	}

	/**
	 * Reads the bytes in the buffer at the current reader index through to the
	 * given {@code length} and returns the bytes within that space.
	 * 
	 * @param length the length of the bytes to read
	 * @return the array with the bytes read
	 */
	public byte[] readBytes(int length) {
		if (length > buffer.length)
			throw new ArrayIndexOutOfBoundsException("Buffer index=" + (position + length) + " when Buffer length=" + buffer.length);

		byte[] array = Arrays.copyOfRange(buffer, position, position + length);
		position += length;
		return array;
	}

	/**
	 * Reads the bytes in the buffer at the current reader index through to the
	 * given {@code length} and returns the bytes within that space.
	 * 
	 * @param length the length of the bytes to read
	 * @return the array with the bytes read
	 */
	public void readBytesInto(byte[] array) {
		for (int i = 0; i < array.length; i++) {
			array[i] = (byte) readByte();
		}
	}

	/**
	 * Returns the short read in the buffer at the current reader index and
	 * increases the reader index by 2.
	 * 
	 * @return the short read
	 */
	public int readShort(int index) {
		return ((getByte(index) & 0xFF) << 8) | (getByte(index + 1) & 0xFF);
	}

	/**
	 * Returns the short read in the buffer at the current reader index and
	 * increases the reader index by 2.
	 * 
	 * @return the short read
	 */
	public short readShort() {
		return (short) ((readUnsignedByte() << 8) | (readUnsignedByte()));
	}

	/**
	 * Returns the unsigned short read in the buffer at the current reader index and
	 * increases the reader index by 2.
	 * 
	 * @return the unsigned short read
	 */
	public int readUnsignedShort() {
		return readShort() & 0xFFFF;
	}

	/**
	 * Returns the a-type short read in the buffer at the current reader index and
	 * increases the reader index by 2.
	 * 
	 * @return the a-type short read
	 */
	public int readShortA() {
		return (readUnsignedByte() << 8) | (readByte() - 128 & 0xFF);
	}

	/**
	 * Returns the little-endian short read in the buffer at the current reader
	 * index and increases the reader index by 2.
	 * 
	 * @return the little-endian short read
	 */
	public int readLEShort() {
		return readUnsignedByte() | (readUnsignedByte() << 8);
	}

	/**
	 * Returns the little-endian a-type short read in the buffer at the current
	 * reader index and increases the reader index by 2.
	 * 
	 * @return the little-endian a-type short read
	 */
	public int readLEShortA() {
		return (readByte() - 128 & 0xFF) | (readUnsignedByte() << 8);
	}

	/**
	 * Returns the medium read in the buffer at the current reader index and
	 * increases the reader index by 2.
	 * 
	 * @return the medium short read
	 */
	public int readMedium() {
		return (readUnsignedByte() << 16) | (readUnsignedByte() << 8) | (readUnsignedByte());
	}

	/**
	 * Returns the int read in the buffer at the current reader index and increases
	 * the reader index by 4.
	 * 
	 * @return the int read
	 */
	public int readInt() {
		return (readUnsignedByte() << 24) | (readUnsignedByte() << 16) | (readUnsignedByte() << 8) | readUnsignedByte();
	}

	/**
	 * Returns the int read in the buffer at the current reader index and increases
	 * the reader index by 4.
	 * 
	 * @return the int read
	 */
	public int readInt(int index) {
		return ((getByte(index) & 0xFF) << 24) | ((getByte(index + 1) & 0xFF) << 16) | ((getByte(index + 2) & 0xFF) << 8) | getByte(index + 3) & 0xFF;
	}

	/**
	 * Reads the given length of ints in this stream
	 * 
	 * @param length the array length
	 * @return the array of ints read
	 */
	public int[] readIntArray(int length) {
		int[] arr = new int[length];
		for (int i = 0; i < length; i++)
			arr[i] = readInt();
		return arr;
	}

	/**
	 * Reads the given length of shorts in this stream
	 * 
	 * @param length the array length
	 * @return the array of shorts read
	 */
	public short[] readShortArray(int length) {
		short[] arr = new short[length];
		for (int i = 0; i < length; i++)
			arr[i] = readShort();
		return arr;
	}

	/**
	 * Reads the given length of longs in this stream
	 * 
	 * @param length the array length
	 * @return the array of longs read
	 */
	public long[] readLongArray(int length) {
		long[] arr = new long[length];
		for (int i = 0; i < length; i++)
			arr[i] = readLong();
		return arr;
	}

	/**
	 * Returns the unsigned int read in the buffer at the current reader index and
	 * increases the reader index by 4. This value is read by the 3rd byte first,
	 * the 4th byte second, the 1st byte third and the 2nd byte fourth.
	 * 
	 * @return the unsigned int read
	 */
	public int readInt1() {
		int unsignedFirst = readUnsignedByte();
		int unsignedSecond = readUnsignedByte();
		int unsignedThird = readUnsignedByte();
		int unsignedFourth = readUnsignedByte();
		return (unsignedThird << 24 | unsignedFourth << 16 | unsignedFirst << 8 | unsignedSecond);
	}

	/**
	 * Returns the unsigned int read in the buffer at the current reader index and
	 * increases the reader index by 4. This value is read by the 2nd byte first,
	 * the 1st byte second, the 4th byte third and the 3rd byte fourth.
	 * 
	 * @return the unsigned int read
	 */
	public int readInt2() {
		int unsignedFirst = readUnsignedByte();
		int unsignedSecond = readUnsignedByte();
		int unsignedThird = readUnsignedByte();
		int unsignedFourth = readUnsignedByte();
		return (unsignedSecond << 24 | unsignedFirst << 16 | unsignedFourth << 8 | unsignedThird);
	}

	/**
	 * Returns the little-endian int read in the buffer at the current reader index
	 * and increases the reader index by 4.
	 * 
	 * @return the little-endian int read
	 */
	public int readLEInt() {
		return readUnsignedByte() + (readUnsignedByte() << 8) + (readUnsignedByte() << 16) + (readUnsignedByte() << 24);
	}

	/**
	 * Returns the unsigned int read in the buffer at the current reader index and
	 * increases the reader index by 4.
	 * 
	 * @return the unsigned int read
	 */
	public long readUnsignedInt() {
		return readInt() & 0xFFFFFFFFL;
	}

	/**
	 * Returns the float read in the buffer at the current reader index and
	 * increases the reader index by 4.
	 * 
	 * @return the float read
	 */
	public float readFloat() {
		int intBits = readByte() << 24 | readUnsignedByte() << 16 | readUnsignedByte() << 8 | readUnsignedByte();
		return Float.intBitsToFloat(intBits);
	}

	/**
	 * Returns the long read in the buffer at the current reader index and increases
	 * the reader index by 8.
	 * 
	 * @return the long read
	 */
	public long readLong() {
		return (readUnsignedByte() << 56) | (readUnsignedByte() << 48) | (readUnsignedByte() << 40) | (readUnsignedByte() << 32) | (readUnsignedByte() << 24) | (readUnsignedByte() << 16) | (readUnsignedByte() << 8) | readUnsignedByte();
	}

	/**
	 * Returns the smart read in the buffer at the current reader index and
	 * increases the reader index by 2 if a short was read or 1 if a byte was read.
	 * 
	 * @return the smart read
	 */
	public int readSmart() {
		int value = getByte(position);
		if (value <= Byte.MAX_VALUE) {
			return readUnsignedByte();
		} else {
			return readUnsignedShort() - 32768;
		}
	}

	/**
	 * Returns the unsigned smart read in the buffer at the current reader index and
	 * increases the reader index by 2 while the current smart read is equal to
	 * 0x7FFF. The position will continue to increase by 2 until the smart read is
	 * not equal to 0x7FFF.
	 * 
	 * @return the smart read
	 */
	public int readExtendedSmart() {
		int total = 0;
		int smart = readUnsignedSmart();
		while (smart == 0x7FFF) {
			smart = readUnsignedSmart();
			total += 0x7FFF;
		}
		total += smart;
		return total;
	}

	/**
	 * Returns the unsigned smart read in the buffer at the current reader index and
	 * increases the reader index by 2 if a short was read or 1 if a byte was read.
	 * 
	 * @return the unsigned smart read
	 */
	public int readUnsignedSmart() {
		int value = getByte(position) & 0xFF;
		if (value <= Byte.MAX_VALUE) {
			return readUnsignedByte();
		} else {
			return readUnsignedShort() - 32768;
		}
	}

	/**
	 * Returns the string read in the buffer at the current reader index. The reader
	 * index is increased until the next byte found equals 0.
	 * 
	 * @return the string read
	 */
	public String readRSString() {
		StringBuilder result = new StringBuilder();
		int characterRead;
		while ((characterRead = readByte()) > 0)
			result.append((char) characterRead);
		return result.toString();
	}

	/**
	 * Writes the byte {@code value} to the buffer at the current writer index and
	 * increases the writer index by 1.
	 * 
	 * @param value the value to write
	 * @return this current instance, used for chaining
	 */
	public RSStream writeByte(long value) {
		ensureCapacity();
		if (this.position == 0) {
			this.writing = true;
		} else if (this.position > 0 && !this.writing) {
			throw new IllegalStateException("flip() must be called to change from reading to writing on this buffer");
		}
		buffer[position++] = (byte) value;
		return this;
	}

	/**
	 * Writes the byte as 128 - {@code value} to the buffer at the current writer
	 * index and increases the writer index by 1.
	 * 
	 * @param value the value to write
	 * @return this current instance, used for chaining
	 */
	public RSStream writeByteS(int value) {
		return writeByte((byte) (128 - value));
	}

	/**
	 * Writes the byte as 128 - {@code value} to the buffer at the current writer
	 * index and increases the writer index by 1.
	 * 
	 * @param value the value to write
	 * @return this current instance, used for chaining
	 */
	public RSStream writeByteA(int value) {
		return writeByte(128 + value);
	}

	/**
	 * Writes the given {@code bytes} array to the buffer at the current writer
	 * index and increases the writer index by the length of the array.
	 * 
	 * @param bytes the array to write
	 * @return this current instance, used for chaining
	 */
	public RSStream writeBytes(byte[] bytes) {
		for (int i = 0; i < bytes.length; i++) {
			writeByte(bytes[i]);
		}
		return this;
	}

	/**
	 * Writes the given {@code bytes} array to the buffer at the current writer
	 * index and increases the writer index by the length of the array.
	 * 
	 * @param bytes the array to write
	 * @return this current instance, used for chaining
	 */
	public RSStream writeBytes(byte[] bytes, int offset, int length) {
		return writeBytes(Arrays.copyOfRange(bytes, offset, length));
	}

	/**
	 * Writes the bytes from the given {@code stream} to this {@code RSStream}.
	 * 
	 * @param stream the stream to transfer the bytes from
	 * @return this current instance, used for chaining
	 */
	public RSStream writeBytes(RSStream stream) {
		return writeBytes(Arrays.copyOfRange(stream.buffer, 0, stream.position));
	}

	/**
	 * Writes the given {@code value} as a negative byte.
	 * 
	 * @param value the value to write
	 * @return this current instance, used for chaining
	 */
	public RSStream writeByteC(int value) {
		return writeByte(-value);
	}

	/**
	 * Writes the short {@code value} to the buffer at the current writer index and
	 * increases the writer index by 2.
	 * 
	 * @param value the value to write
	 * @return this current instance, used for chaining
	 */
	public RSStream writeShort(int value) {
		return writeByte(value >> 8).writeByte(value);
	}

	/**
	 * Writes the a-type short {@code value} to the buffer at the current writer
	 * index and increases the writer index by 2.
	 * 
	 * @param value the value to write
	 * @return this current instance, used for chaining
	 */
	public RSStream writeShortA(int value) {
		return writeByte((byte) (value >> 8)).writeByte((byte) (value + 128));
	}

	/**
	 * Writes the little-endian short {@code value} to the buffer at the current
	 * writer index and increases the writer index by 2.
	 * 
	 * @param value the value to write
	 * @return this current instance, used for chaining
	 */
	public RSStream writeLEShort(int value) {
		return writeByte((byte) value).writeByte((byte) (value >> 8));
	}

	/**
	 * Writes the little-endian a-type short {@code value} to the buffer at the
	 * current writer index and increases the writer index by 2.
	 * 
	 * @param value the value to write
	 * @return this current instance, used for chaining
	 */
	public RSStream writeLEShortA(int value) {
		return writeByte(value + 128).writeByte(value >> 8);
	}

	/**
	 * Writes a 24Bit value by writing 3 bytes. The first byte if shifted to the
	 * right by 16, the second byte is shift to the right by 8, and the third byte
	 * is the value itself.
	 * 
	 * @param value the value to write
	 * @return this current instance, used for chaining
	 */
	public RSStream writeMedium(int value) {
		return writeByte((byte) (value >> 16)).writeByte((byte) (value >> 8)).writeByte((byte) value);
	}

	/**
	 * Writes the int {@code value} to the buffer at the current writer index and
	 * increases the writer index by 4.
	 * 
	 * @param value the value to write
	 * @return this current instance, used for chaining
	 */
	public RSStream writeInt(int value) {
		return writeByte(value >> 24).writeByte(value >> 16).writeByte(value >> 8).writeByte(value);
	}

	/**
	 * Writes the int {@code value} to the buffer at the given {@code index}. This
	 * does not icrease the writer index.
	 * 
	 * @param value the value to write
	 * @return this current instance, used for chaining
	 */
	public RSStream writeInt(int index, int value) {
		return setByte(index, value >> 24).setByte(index + 1, value >> 16).setByte(index + 2, value >> 8).setByte(index + 3, value);
	}

	/**
	 * Writes the little-endian int {@code value} to the buffer at the current
	 * writer index and increases the writer index by 4.
	 * 
	 * @param value the value to write
	 * @return this current instance, used for chaining
	 */
	public RSStream writeLEInt(int value) {
		return writeByte((byte) value).writeByte((byte) (value >> 8)).writeByte((byte) (value >> 16)).writeByte((byte) (value >> 24));
	}

	/**
	 * Writes the int {@code value} to the buffer at the current writer index and
	 * increases the writer index by 4.
	 * 
	 * @param value the value to write
	 * @return this current instance, used for chaining
	 */
	public RSStream writeInt1(int value) {
		return writeByte((byte) (value >> 8)).writeByte((byte) value).writeByte((byte) (value >> 24)).writeByte((byte) (value >> 16));
	}

	/**
	 * Writes the int {@code value} to the buffer at the current writer index and
	 * increases the writer index by 4.
	 * 
	 * @param value the value to write
	 * @return this current instance, used for chaining
	 */
	public RSStream writeInt2(int value) {
		return writeByte((byte) (value >> 16)).writeByte((byte) (value >> 24)).writeByte((byte) value).writeByte((byte) (value >> 8));
	}

	/**
	 * Writes the float {@code value} to the buffer at the current writer index and
	 * increases the writer index by 4.
	 * 
	 * @param value the value to write
	 * @return this current instance, used for chaining
	 */
	public RSStream writeFloat(float value) {
		int intBits = Float.floatToIntBits(value);
		return writeByte(intBits >> 24).writeByte(intBits >> 16).writeByte(intBits >> 8).writeByte(intBits);
	}

	/**
	 * Writes the long {@code value} to the buffer at the current writer index and
	 * increases the writer index by 8.
	 * 
	 * @param value the value to write
	 * @return this current instance, used for chaining
	 */
	public RSStream writeLong(long value) {
		return writeByte(value).writeByte(value >> 8).writeByte(value >> 16).writeByte(value >> 24).writeByte(value >> 32).writeByte(value >> 40).writeByte(value >> 48).writeByte(value >> 56);
	}

	/**
	 * Writes the int {@code value} to the buffer at the current writer index and
	 * increases the writer index by 2 if the value is > {@link Byte#MAX_VALUE}
	 * otherwise the writer index is increased by 1.
	 * 
	 * @param value the value to write
	 * @return this current instance, used for chaining
	 */
	public RSStream writeSmart(int value) {
		if (value > Byte.MAX_VALUE) {
			return writeShort(value + 32768);
		} else {
			return writeByte(value);
		}
	}

	/**
	 * Writes the given {@code string} to the buffer at the current writer index and
	 * increases the writer index by the string length plus 1. A byte value of 0 is
	 * written after writing the string bytes.
	 * 
	 * @param string the string to write
	 * @return this current instance, used for chaining
	 */
	public RSStream writeRSString(String string) {
		return writeBytes(string.getBytes()).writeByte((byte) 0);
	}

	/**
	 * Writes the given {@code string} to the buffer at the current writer index and
	 * increases the writer index by the string length plus 2. A byte value of 0 is
	 * written before and after writing the string bytes.
	 * 
	 * @param string the string to write
	 * @return this current instance, used for chaining
	 */
	public RSStream writeGJString(String string) {
		return writeByte(0).writeBytes(string.getBytes()).writeByte(0);
	}

	/**
	 * Sets the byte {@code value} at the given {@code index} in the buffer. This
	 * does not change the reader or writer index.
	 * 
	 * @param index the index at set the value at
	 * @param value the value to set
	 * @throws RSStreamException if the index given is
	 * @return this current instance, used for chaining
	 */
	public RSStream setByte(int index, int value) {
		if (index >= buffer.length)
			throw new IndexOutOfBoundsException("cannot set value at index " + index + " because buffer size is only " + buffer.length);
		if (index < 0)
			throw new IndexOutOfBoundsException("cannot set value at negative index " + index);
		buffer[index] = (byte) value;
		return this;
	}

	/**
	 * Returns the byte value at the given {@code index} in this buffer. This does
	 * not change the reader or writer index.
	 * 
	 * @param index the index to get the value from
	 * @return the value in the buffer at the index
	 */
	public int getByte(int index) {
		if (index >= buffer.length)
			throw new IndexOutOfBoundsException("cannot get value at index " + index + " because buffer size is only " + buffer.length);
		if (index < 0)
			throw new IndexOutOfBoundsException("cannot get value at negative index " + index);
		return buffer[index];
	}

	public int readableBytes() {
		return this.capacity() - this.position();
	}

	public void skip(int skip) {
		this.position += skip;
	}

	public void limit(int limit) {
		this.buffer = Arrays.copyOf(this.buffer, limit);
	}

	public void flip() {
		this.position = 0;
		this.writing = !writing;
	}

	/**
	 * Clears this {@code RSStream} buffer.
	 */
	public void clear() {
		this.position = 0;
		for (int i = 0; i < buffer.length; i++) {
			buffer[i] = 0;
		}
	}

	/**
	 * Returns the capacity of the buffer.
	 * 
	 * @return the capacity
	 */
	public int capacity() {
		return buffer.length;
	}

}
