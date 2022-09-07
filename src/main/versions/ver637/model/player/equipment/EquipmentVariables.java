package versions.ver637.model.player.equipment;

import lombok.Getter;
import lombok.Setter;
import versions.ver637.model.item.Item;
import versions.ver637.model.item.ItemContainer;
import versions.ver637.model.player.Player;
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
	private int[] bonuses = new int[18];

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

}
