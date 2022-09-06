package versions.ver637.model.item;

import lombok.Getter;
import lombok.Setter;
import versions.ver637.model.item.ItemResource.ItemData;

@Getter
@Setter
public class Item {

	private final int ID;
	private int amount;

	private transient ItemData data;

	public Item(int ID, int amount) {
		this.ID = ID;
		this.amount = amount;

		this.data = ItemResource.getItemData(ID);
	}

	public Item(int ID) {
		this(ID, 1);
	}

	public boolean isStackable() {
		return data.isStackable();
	}

	public String getName() {
		return data.getName();
	}

	public int getValue() {
		return data.getValue();
	}

}
