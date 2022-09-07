package versions.ver637.model.item;

import lombok.Getter;
import lombok.Setter;
import versions.ver637.model.item.ItemResource.ItemData;

@Getter
@Setter
public class Item {

	private final int ID;
	private int amount;

	public Item(int ID, int amount) {
		this.ID = ID;
		this.amount = amount;
	}

	public Item(int ID) {
		this(ID, 1);
	}

	public ItemData getData() {
		return ItemResource.getItemData(ID);
	}

	public boolean isStackable() {
		return getData().isStackable();
	}

	public String getName() {
		return getData().getName();
	}

	public int getValue() {
		return getData().getValue();
	}

}
