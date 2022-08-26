package com.framework.entity;

import java.util.Iterator;

import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * The {@code RSEntityList} class holds an array of {@code RSEntity} types. The
 * {@code RSEntityList} class works similarly to the {@link java.util.ArrayList}
 * class except with specific {@code RSEntity} functions.
 * 
 * @author Albert Beaupre
 * 
 * @param <E> The {@code RSEntity} type
 */
@SuppressWarnings("unchecked")
@Accessors(fluent = true)
public class RSEntityList<E extends RSEntity> implements Iterable<E> {

	private RSEntity[] data;

	@Getter
	private int size, head = 1, tail = 1;

	/**
	 * Constructs a new {@code RSEntityList} with a specified {@code capacity}.
	 * 
	 * @param capacity the capacity of the data array in this {@code RSEntityList}
	 */
	public RSEntityList(int capacity) {
		this.data = new RSEntity[capacity];
	}

	/**
	 * Finds an empty index in the array of this {@code RSEntityList}. If no index
	 * is found, -1 is returned.
	 * 
	 * @return an empty index, otherwise -1 is returned
	 * @throws Exception is there was an error while increasing the array size
	 */
	private int findEmptyIndex() {
		for (int i = 1; i < data.length; i++)
			if (data[i] == null)
				return i;
		return -1;
	}

	/**
	 * Adds the specified {@code RSEntity} type to this {@code RSEntityList} and
	 * sets its index value to the available index in this {@code RSEntityList} that
	 * has not been used and returns {@code true} if the {@code RSEntity} was added;
	 * otherwise {@code false} is returned;
	 * 
	 * @param RSEntity the {@code RSEntity} type to add
	 * @return true if the {@code RSEntity} was added; return false otherwise
	 * 
	 * @see java.RSEntity.RSEntity#getIndex()
	 */
	public boolean add(E RSEntity) {
		int index = findEmptyIndex();
		if (index != -1) {
			if (index < head)
				head = index;
			if (index > tail)
				tail = index;

			data[index] = RSEntity;
			data[index].setIndex(index);
			size++;
			return true;
		}
		return false;
	}

	/**
	 * Removes the specified {@code RSEntity} from this {@code RSEntityList} and
	 * returns {@code true} if the {@code RSEntity} was removed; otherwise
	 * {@code false} is returned.
	 * 
	 * @param RSEntity the RSEntity to remove
	 * @return true if the {@code RSEntity} was removed; return false otherwise
	 */
	public boolean remove(E RSEntity) {
		int index = RSEntity.getIndex();
		if (index < 0)
			return false;
		if (index == head)
			head++;
		if (index == tail)
			tail--;
		data[index] = null;
		size--;
		RSEntity.setIndex(-1);
		return true;
	}

	/**
	 * Removes the {@code RSEntity} at the specified {@code index} of this
	 * {@code RSEntityList}.
	 * 
	 * @param index the index to remove the {@code RSEntity} at
	 * @return true if the {@code RSEntity} was removed
	 * 
	 * @see #remove(RSEntity)
	 */
	public boolean remove(int index) {
		if (index < 0 || data[index] == null)
			return false;
		data[index].setIndex(-1);
		data[index] = null;
		size--;
		return true;
	}

	/**
	 * Sets the given {@code RSEntity} at the given {@coden index}.
	 * 
	 * @param index    the index to set at
	 * @param RSEntity the entity to set
	 * @return the previous entity
	 */
	public E set(int index, E RSEntity) {
		RSEntity previous = this.data[index];
		this.data[index] = RSEntity;
		return (E) previous;
	}

	/**
	 * Returns the {@code RSEntity} placed at the specified {@code index} of this
	 * {@code RSEntityList}.
	 * 
	 * @param index the index to retrieve the {@code RSEntity} at
	 * @return the {@code RSEntity} at the index; return null if non-existent
	 */
	public E get(int index) {
		if (index >= data.length)
			return null;
		return (E) data[index];
	}

	/**
	 * Clears this {@code RSEntityList} of every contained {@code RSEntity}.
	 */
	public void clear() {
		for (int i = 0; i < data.length; i++)
			data[i] = null;
		head = tail = 1;
		size = 0;
	}

	/**
	 * Returns true if this {@code RSEntityList} doesn't contain any
	 * {@code RSEntity}.
	 * 
	 * @return true if empty; return false otherwise
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Checks if the specified {@code Object} is within this {@code RSEntityList},
	 * and if so, returns {@code true}, {@code false} is returned otherwise. The
	 * argument {@code o} must be of type {@code RSEntity}, otherwise it will return
	 * {@code false} no matter what.
	 * 
	 * @param o the object to check
	 * @return true if the object is within this {@code RSEntityList}; return false
	 *         otherwise
	 */
	public boolean contains(Object o) {
		if (!RSEntity.class.isAssignableFrom(o.getClass()))
			return false;
		RSEntity e = (RSEntity) o;
		if (e.getIndex() < 0 || e.getIndex() >= data.length)
			return false;
		return data[e.getIndex()] != null && data[e.getIndex()].equals(o);
	}

	/**
	 * Creates a new {@code Iterator} for this {@code RSEntityList}.
	 */
	public Iterator<E> iterator() {
		return new Iterator<E>() {
			private int index;

			@Override
			public boolean hasNext() {
				return index < data.length;
			}

			@Override
			public E next() {
				return (E) data[index++];
			}

			@Override
			public void remove() {
				if (index >= data.length)
					return;
				RSEntityList.this.remove(index);
			}
		};
	}

	/**
	 * Returns the capacity of this {@code RSEntityList}.
	 * 
	 * @return the capacity
	 */
	public int capacity() {
		return data.length;
	}

	/**
	 * Returns a <b>copy</b> of the {@code RSEntity} data array in this
	 * {@code RSEntityList}.
	 * 
	 * @return a copy of the data
	 */
	public RSEntity[] toArray() {
		return data;
	}

	public <T> T[] toArray(T[] a) {
		int count = head;
		for (int i = 0; i < Math.min(size, a.length); i++) {
			T t = (T) data[count];
			if (t == null)
				continue;
			a[i] = t;
			count++;
		}
		return a;
	}
}
