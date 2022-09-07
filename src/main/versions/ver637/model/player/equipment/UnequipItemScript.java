package versions.ver637.model.player.equipment;

import com.framework.mechanics.queue.RSQueueType;
import com.framework.mechanics.queue.RSScript;

import lombok.RequiredArgsConstructor;
import versions.ver637.model.item.Item;
import versions.ver637.model.item.ItemContainer;
import versions.ver637.model.player.AppearanceVariables;
import versions.ver637.model.player.Player;

@RequiredArgsConstructor
public class UnequipItemScript implements RSScript<Player> {

	private final int equipmentSlot;

	@Override
	public void process(Player owner) {
		EquipmentVariables variables = owner.getEquipmentVariables();
		ItemContainer equipment = variables.getEquipment();
		ItemContainer inventory = owner.getInventory();
		Item toUnEquip = equipment.get(equipmentSlot);

		if (toUnEquip == null)
			return;

		if (inventory.isFull()) {
			if (toUnEquip.isStackable() && inventory.hasItem(toUnEquip.getID())) {
				equipment.set(equipmentSlot, null);
				inventory.addItem(toUnEquip);
				
				AppearanceVariables.updateAppearance(owner);
				return;
			}

			owner.sendMessage("Your inventory is too full to unequip that item.");
			return;
		}

		equipment.set(equipmentSlot, null);
		inventory.addItem(toUnEquip);
		AppearanceVariables.updateAppearance(owner);
	}

	@Override
	public RSQueueType type() {
		return RSQueueType.Weak;
	}

}
