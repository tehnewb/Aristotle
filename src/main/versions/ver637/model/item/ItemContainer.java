package versions.ver637.model.item;

import java.util.Arrays;
import java.util.Comparator;

/**
 * An {@code ItemContainer} acts as a limited capacity container that holds
 * items for inserting, adding, removing, shifting, and sorting.
 * 
 * @author Albert Beaupre
 */
public class ItemContainer {

	private Item[] items;
	private final boolean stackOnly;
	private short size;

	public ItemContainer(int capacity, boolean stackOnly) {
		this.items = new Item[capacity];
		this.stackOnly = stackOnly;
	}

	/**
	 * Adds the given {@code item} to this {@code ItemContainer}.
	 * 
	 * @param item the item to add.
	 */
	public void addItem(Item item) {
		if (item.isStackable() || stackOnly) {
			int index = this.indexOf(item.getID());
			if (index == -1) {
				this.set(this.getFreeSlot(), item);
			} else {
				this.set(index, new Item(item.getID(), this.get(index).getAmount() + item.getAmount()));
			}
		} else {
			for (int i = 0; i < item.getAmount(); i++) {
				int freeSlot = this.getFreeSlot();
				if (freeSlot == -1)
					break;
				this.set(freeSlot, new Item(item.getID(), 1));
			}
		}
	}

	/**
	 * Removes the given {@code item} from this {@code ItemContainer}.
	 * 
	 * @param item the item to remove
	 */
	public void removeItem(Item item) {
		int index = this.indexOf(item.getID());
		if (index == -1)
			return;

		if (item.isStackable() || stackOnly) {
			this.set(index, new Item(item.getID(), get(index).getAmount() - item.getAmount()));
		} else {
			this.set(index, null);
		}
	}

	/**
	 * Sets the given {@code item} at the given {@code slot}.
	 * 
	 * @param slot the slot to set the item at
	 * @param item the item to set
	 */
	public void set(int slot, Item item) {
		if (item.getAmount() < 0)
			item = null;

		Item current = get(slot);
		if (current == null && item != null)
			size++;
		if (current != null && item == null)
			size--;
		this.items[slot] = item;
	}

	/**
	 * Returns the item at the given {@code slot}. Possibly returns null.
	 * 
	 * @param slot the slot of the item
	 * @return the item; possibly null
	 */
	public Item get(int slot) {
		return items[slot];
	}

	/**
	 * Shifts all items that are not null in this {@code ItemContainer} to the left.
	 */
	public void shift() {
		Item[] newItems = new Item[this.items.length];
		int newIndex = 0;
		for (int i = 0; i < this.items.length; i++) {
			if (items[i] == null)
				continue;
			newItems[newIndex++] = items[i];
		}
		this.items = newItems;
	}

	/**
	 * Sorts this {@code ItemContainer} based on this given {@code comparator}.
	 * 
	 * @param comparator the sorting comparator
	 */
	public void sort(Comparator<Item> comparator) {
		Arrays.sort(items, comparator);
	}

	/**
	 * Returns true if this {@code ItemContainer} has any of the items with the
	 * given {@code itemIDs}.
	 * 
	 * @param itemIDs the array of item ids to check
	 * @return true if contains any; false otherwise
	 */
	public boolean hasAnyOf(int... itemIDs) {
		for (int i = 0; i < itemIDs.length; i++) {
			if (indexOf(itemIDs[i]) > -1)
				return true;
		}
		return false;
	}

	/**
	 * Returns true if this {@code ItemContainer} has an item with the same ID and
	 * amount more than or equal to the given {@code item}.
	 * 
	 * @param item the item to check
	 * @return true if contains the item; false otherwise
	 */
	public boolean hasItem(Item item) {
		int index = indexOf(item.getID());
		if (index == -1)
			return false;
		return get(index).getAmount() >= item.getAmount();
	}

	/**
	 * Returns true if this {@code ItemContainer} has an item with the same ID as
	 * the given {@code itemID}.
	 * 
	 * @param itemID the id of the item
	 * @return true if contains the item; false otherwise
	 */
	public boolean hasItem(int itemID) {
		return indexOf(itemID) > -1;
	}

	/**
	 * Returns the index of an item with the given {@code itemID}. If no item is
	 * found, -1 is returned.
	 * 
	 * @param itemID the item id to get the index of
	 * @return the index; -1 otherwise
	 */
	public int indexOf(int itemID) {
		for (int i = 0; i < items.length; i++) {
			if (items[i] == null)
				continue;
			if (items[i].getID() == itemID)
				return i;
		}
		return -1;
	}

	/**
	 * Returns an unusued slot in this {@code ItemContainer}. If there is no
	 * available slot, -1 is returned.
	 * 
	 * @return an available slot; -1 otherwise
	 */
	public int getFreeSlot() {
		for (int i = 0; i < items.length; i++) {
			if (items[i] == null)
				return i;
		}
		return -1;
	}

	/**
	 * Returns the capacity of this {@code ItemContainer}.
	 * 
	 * @return the capacity
	 */
	public int getCapacity() {
		return this.items.length;
	}

	/**
	 * Returns the size of this {@code ItemContainer}.
	 * 
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Returns true if this {@code ItemContainer} is full.
	 * 
	 * @return true if full; false otherwise
	 */
	public boolean isFull() {
		return size >= items.length;
	}

	/**
	 * Returns true if this {@code ItemContainer} is empty.
	 * 
	 * @return true if empty; false otherwise
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Returns true if this {@code ItemContainer} has free slots.
	 * 
	 * @return true if free slots; false otherwise
	 */
	public boolean hasFreeSlots() {
		return size < items.length;
	}

	/*
	 * @see javadoc
	 */
	public String toString() {
		return Arrays.toString(items);
	}
}
