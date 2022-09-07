package versions.ver637.network.coders.frames;

import com.framework.network.RSFrame;

import versions.ver637.model.item.Item;
import versions.ver637.model.item.ItemContainer;

public class ContainerFrame extends RSFrame {

	public static final int ContainerOpcode = 113;

	public ContainerFrame(int containerID, boolean split, ItemContainer container) {
		super(ContainerOpcode, VarShortType);

		writeShort(containerID);
		writeByte(split ? 1 : 0);
		writeShort(container.getCapacity());
		for (int i = 0; i < container.getCapacity(); i++) {
			Item item = container.get(i);
			int itemID = item == null ? 0 : item.getID() + 1;
			int itemAmount = item == null ? 0 : item.getAmount();

			writeShortA(itemID);
			writeByteC(itemAmount > 254 ? 0xff : itemAmount);
			if (itemAmount > 254)
				writeInt1(itemAmount);
		}
	}

}
