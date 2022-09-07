package versions.ver637.model.player;

import java.util.Arrays;

import com.framework.util.StringUtil;

import lombok.Getter;
import lombok.Setter;
import versions.ver637.model.item.Item;
import versions.ver637.model.item.ItemContainer;
import versions.ver637.model.player.skills.SkillRequirement;
import versions.ver637.network.coders.handlers.CarriedWeightFrame;

public class EquipmentVariables {

	public static final byte HeadSlot = 0;
	public static final byte CapeSlot = 1;
	public static final byte AmuletSlot = 2;
	public static final byte WeaponSlot = 3;
	public static final byte TorsoSlot = 4;
	public static final byte ShieldSlot = 5;
	public static final byte LegSlot = 7;
	public static final byte HandSlot = 9;
	public static final byte FeetSlot = 10;
	public static final byte RingSlot = 12;
	public static final byte ArrowSlot = 13;

	public static final byte StabAttackBonus = 0;
	public static final byte SlashAttackBonus = 1;
	public static final byte CrushAttackBonus = 2;
	public static final byte MagicAttackBonus = 3;
	public static final byte RangeAttackBonus = 4;
	public static final byte StabDefenceBonus = 5;
	public static final byte SlashDefenceBonus = 6;
	public static final byte CrushDefenceBonus = 7;
	public static final byte MagicDefenceBonus = 8;
	public static final byte RangeDefenceBonus = 9;
	public static final byte SummoningDefenceBonus = 10;
	public static final byte StrengthBonus = 11;
	public static final byte RangeStrengthBonus = 12;
	public static final byte PrayerBonus = 13;
	public static final byte MagicBonus = 14;
	public static final byte AbsorbMeleeBonus = 15;
	public static final byte AbsorbMagicBonus = 16;
	public static final byte AbsorbRangeBonus = 17;

	@Getter
	private ItemContainer equipment = new ItemContainer(15, true);
	@Getter
	@Setter
	private float weight;
	@Getter
	private short[] bonuses = new short[18];

	public static void calculateWeight(Player player) {
		EquipmentVariables variables = player.getEquipmentVariables();
		ItemContainer inventory = player.getInventory();
		ItemContainer equipment = variables.getEquipment();

		float weight = 0;
		for (int i = 0; i < inventory.getCapacity(); i++) {
			Item item = inventory.get(i);
			if (item == null)
				continue;
			weight += item.getData().getWeight();
		}
		for (int i = 0; i < equipment.getCapacity(); i++) {
			Item item = equipment.get(i);
			if (item == null)
				continue;
			weight += item.getData().getWeight();
		}
		player.getEquipmentVariables().setWeight(weight);
		player.getSession().write(new CarriedWeightFrame((int) weight));
	}

	public static void calculateBonuses(Player player) {
		EquipmentVariables variables = player.getEquipmentVariables();
		ItemContainer equipment = variables.getEquipment();

		Arrays.fill(variables.bonuses, (short) 0);

		for (int itemIndex = equipment.getCapacity() - 1; itemIndex >= 0; itemIndex--) {
			Item item = equipment.get(itemIndex);
			if (item != null) {
				short[] itemBonuses = item.getData().getBonuses();
				if (itemBonuses != null) {
					for (int bonusIndex = 0; bonusIndex < 18; bonusIndex++) {
						if (bonusIndex == RangeStrengthBonus && itemBonuses[bonusIndex] > 0) {
							variables.bonuses[bonusIndex] = (short) itemBonuses[bonusIndex];
						} else {
							variables.bonuses[bonusIndex] += itemBonuses[bonusIndex];
						}
					}
				}
			}
		}
	}

	public static void equip(Player player, int itemID, int inventorySlot) {
		EquipmentVariables variables = player.getEquipmentVariables();
		ItemContainer equipment = variables.getEquipment();
		ItemContainer inventory = player.getInventory();
		Item itemToEquip = player.getInventory().get(inventorySlot);

		if (itemToEquip == null)
			return; // shouldn't happen unless there is packet injection

		if (itemID != itemToEquip.getID())
			return;

		player.getQueue().interrupt();

		int slotToEquip = itemToEquip.getData().getEquipmentSlot();

		if (itemToEquip.getData().getEquipmentSlot() == -1) {
			player.sendMessage("That item cannot be equipped!");
			return;
		}

		SkillRequirement[] requirements = itemToEquip.getData().getRequirements();
		if (requirements != null) {
			for (int i = 0; i < requirements.length; i++) {
				SkillRequirement req = requirements[i];
				if (!req.has(player)) {
					String withPrefix = StringUtil.withPrefix(req.type().getName());
					player.sendMessage("You need {0} level of {1} to equip this.", withPrefix, req.level());
					return;
				}
			}
		}

		Item shield = equipment.get(EquipmentVariables.ShieldSlot);
		Item weapon = equipment.get(EquipmentVariables.WeaponSlot);
		if (slotToEquip == EquipmentVariables.WeaponSlot && itemToEquip.getData().isTwoHanded()) {
			if (shield != null) {
				if (inventory.isFull() && weapon != null) {
					player.sendMessage("Your inventory is too full to equip that item.");
					return;
				}
				inventory.set(inventorySlot, weapon);
				equipment.set(slotToEquip, itemToEquip);
				equipment.set(EquipmentVariables.ShieldSlot, null);
				inventory.addItem(shield);

				AppearanceVariables.updateAppearance(player);
				return;
			}
		}

		if (slotToEquip == EquipmentVariables.ShieldSlot) {
			if (weapon != null && weapon.getData().isTwoHanded()) {
				if (inventory.isFull()) {
					player.sendMessage("Your inventory is too full to equip that item.");
					return;
				}
				equipment.set(EquipmentVariables.ShieldSlot, itemToEquip);
				equipment.set(EquipmentVariables.WeaponSlot, null);
				inventory.set(inventorySlot, weapon);

				AppearanceVariables.updateAppearance(player);
				return;
			}
		}
		Item itemToUnEquip = equipment.get(slotToEquip);

		if (itemToEquip.isStackable() && equipment.hasItem(itemToEquip.getID())) {
			inventory.set(inventorySlot, null);
			equipment.addItem(itemToEquip);
		} else {
			if (itemToUnEquip != null && itemToUnEquip.isStackable() && inventory.hasItem(itemToUnEquip.getID())) {
				inventory.removeItem(itemToEquip);
				inventory.addItem(itemToUnEquip);
			} else {
				inventory.set(inventorySlot, itemToUnEquip);
			}
			equipment.set(slotToEquip, itemToEquip);
		}

		AppearanceVariables.updateAppearance(player);
	}

	public static void unEquip(Player player, int equipmentSlot) {
		EquipmentVariables variables = player.getEquipmentVariables();
		ItemContainer equipment = variables.getEquipment();
		ItemContainer inventory = player.getInventory();
		Item toUnEquip = equipment.get(equipmentSlot);

		if (toUnEquip == null)
			return;

		player.getQueue().interrupt();

		if (inventory.isFull()) {
			if (toUnEquip.isStackable() && inventory.hasItem(toUnEquip.getID())) {
				equipment.set(equipmentSlot, null);
				inventory.addItem(toUnEquip);

				AppearanceVariables.updateAppearance(player);
				return;
			}

			player.sendMessage("Your inventory is too full to unequip that item.");
			return;
		}

		equipment.set(equipmentSlot, null);
		inventory.addItem(toUnEquip);
		AppearanceVariables.updateAppearance(player);
	}

}
