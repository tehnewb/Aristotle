package versions.ver637.model.player.equipment;

import com.framework.mechanics.queue.RSQueueType;
import com.framework.mechanics.queue.RSScript;
import com.framework.util.StringUtil;

import lombok.RequiredArgsConstructor;
import versions.ver637.model.item.Item;
import versions.ver637.model.item.ItemContainer;
import versions.ver637.model.player.AppearanceVariables;
import versions.ver637.model.player.Player;
import versions.ver637.model.player.skills.SkillRequirement;

@RequiredArgsConstructor
public class EquipItemScript implements RSScript<Player> {

	private final int fromItemID;
	private final int fromInventorySlot;

	@Override
	public void process(Player owner) {
		EquipmentVariables variables = owner.getEquipmentVariables();
		ItemContainer equipment = variables.getEquipment();
		ItemContainer inventory = owner.getInventory();
		Item itemToEquip = owner.getInventory().get(fromInventorySlot);

		if (itemToEquip == null)
			return; // shouldn't happen unless there is packet injection

		if (fromItemID != itemToEquip.getID())
			return;

		int slotToEquip = itemToEquip.getData().getEquipmentSlot();

		if (itemToEquip.getData().getEquipmentSlot() == -1) {
			owner.sendMessage("That item cannot be equipped!");
			return;
		}

		SkillRequirement[] requirements = itemToEquip.getData().getRequirements();
		if (requirements != null) {
			for (int i = 0; i < requirements.length; i++) {
				SkillRequirement req = requirements[i];
				if (!req.has(owner)) {
					String withPrefix = StringUtil.withPrefix(req.type().getName());
					owner.sendMessage("You need {0} level of {1} to equip this.", withPrefix, req.level());
					return;
				}
			}
		}

		Item shield = equipment.get(EquipmentVariables.ShieldSlot);
		Item weapon = equipment.get(EquipmentVariables.WeaponSlot);
		if (slotToEquip == EquipmentVariables.WeaponSlot && itemToEquip.getData().isTwoHanded()) {
			if (shield != null) {
				if (inventory.isFull() && weapon != null) {
					owner.sendMessage("Your inventory is too full to equip that item.");
					return;
				}
				inventory.set(fromInventorySlot, weapon);
				equipment.set(slotToEquip, itemToEquip);
				equipment.set(EquipmentVariables.ShieldSlot, null);
				inventory.addItem(shield);

				AppearanceVariables.updateAppearance(owner);
				return;
			}
		}

		if (slotToEquip == EquipmentVariables.ShieldSlot) {
			if (weapon != null && weapon.getData().isTwoHanded()) {
				if (inventory.isFull()) {
					owner.sendMessage("Your inventory is too full to equip that item.");
					return;
				}
				equipment.set(EquipmentVariables.ShieldSlot, itemToEquip);
				equipment.set(EquipmentVariables.WeaponSlot, null);
				inventory.set(fromInventorySlot, weapon);

				AppearanceVariables.updateAppearance(owner);
				return;
			}
		}
		Item itemToUnEquip = equipment.get(slotToEquip);

		if (itemToEquip.isStackable() && equipment.hasItem(itemToEquip.getID())) {
			inventory.set(fromInventorySlot, null);
			equipment.addItem(itemToEquip);
		} else {
			if (itemToUnEquip != null && itemToUnEquip.isStackable() && inventory.hasItem(itemToUnEquip.getID())) {
				inventory.removeItem(itemToEquip);
				inventory.addItem(itemToUnEquip);
			} else {
				inventory.set(fromInventorySlot, itemToUnEquip);
			}
			equipment.set(slotToEquip, itemToEquip);
		}

		AppearanceVariables.updateAppearance(owner);
	}

	@Override
	public RSQueueType type() {
		return RSQueueType.Weak;
	}

}
