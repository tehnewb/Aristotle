package versions.ver637.model.player;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import versions.ver637.model.item.Item;
import versions.ver637.model.item.ItemContainer;
import versions.ver637.model.player.equipment.EquipmentVariables;
import versions.ver637.model.player.flags.AppearanceFlag;

@Getter
@Setter
@Accessors(fluent = true, chain = true)
public class AppearanceVariables {

	public static final byte MaleGender = 0;
	public static final byte FemaleGender = 1;

	public static final byte ShirtStyle = 0;
	public static final byte SleeveStyle = 1;
	public static final byte PantsStyle = 2;
	public static final byte HairStyle = 3;
	public static final byte HandStyle = 4;
	public static final byte ShoeStyle = 5;
	public static final byte BeardStyle = 6;

	private byte gender = MaleGender;
	private short shirt = 443;
	private short sleeves = 599;
	private short pants = 646;
	private short hair = 309;
	private short hands = 390;
	private short shoes = 438;
	private short beard = 307;
	private short hairColor = 6;
	private short torsoColor = 40;
	private short legColor = 216;
	private short feetColor = 4;
	private short skinColor;
	private short renderAnimation = 1426;
	private short skullIcon = -1;
	private short prayerIcon = -1;
	private short titleID;
	private String username;
	private String previousUserName;
	private transient int[] flags = new int[12];

	private static final int ClothingFlag = 0x100;
	private static final int EquipmentFlag = 0x8000;
	private static final int ClearedFlag = 0;

	/**
	 * Flags the player's appearance data with an equipment flag at the given
	 * {@code flagIndex}.
	 * 
	 * @param flagIndex the index to assign the flag
	 * @param item      the item to use for the flag
	 */
	public void flagEquipment(int flagIndex, Item item) {
		this.flags[flagIndex] = item.getData().getEquipID() + EquipmentFlag;
	}

	/**
	 * Flags the player's appearance data with a style flag at the given
	 * {@code flagIndex}.
	 * 
	 * @param flagIndex     the index to assign the flag
	 * @param clothingIndex the clothing index to assign to the flag index
	 */
	public void flagStyle(int flagIndex, int clothingIndex) {
		this.flags[flagIndex] = getStyles()[clothingIndex] + ClothingFlag;
	}

	/**
	 * Clears the flag at the given {@code flagIndex} by setting the value to
	 * {@link #EMPTY_FLAG}.
	 * 
	 * @param flagIndex the index to clear the flag
	 */
	public void clearFlag(int flagIndex) {
		this.flags[flagIndex] = ClearedFlag;
	}

	public static void updateAppearance(Player player) {
		AppearanceVariables variables = player.getAppearanceVariables();
		ItemContainer equipment = player.getEquipmentVariables().getEquipment();
		Item head = equipment.get(EquipmentVariables.HeadSlot);
		Item cape = equipment.get(EquipmentVariables.CapeSlot);
		Item amulet = equipment.get(EquipmentVariables.AmuletSlot);
		Item weapon = equipment.get(EquipmentVariables.WeaponSlot);
		Item chest = equipment.get(EquipmentVariables.TorsoSlot);
		Item shield = equipment.get(EquipmentVariables.ShieldSlot);
		Item legs = equipment.get(EquipmentVariables.LegSlot);
		Item hands = equipment.get(EquipmentVariables.HandSlot);
		Item feet = equipment.get(EquipmentVariables.FeetSlot);

		if (head != null) {
			variables.flagEquipment(0, head);
		} else {
			variables.clearFlag(0);
		}
		if (cape != null) {
			variables.flagEquipment(1, cape);
		} else {
			variables.clearFlag(1);
		}
		if (amulet != null) {
			variables.flagEquipment(2, amulet);
		} else {
			variables.clearFlag(2);
		}
		if (weapon != null) {
			variables.renderAnimation((short) weapon.getData().getRenderID());

			variables.flagEquipment(3, weapon);
		} else {
			variables.renderAnimation((short) 1426);
			variables.clearFlag(3);
		}
		if (chest != null) {
			variables.flagEquipment(4, chest);
		} else {
			variables.flagStyle(4, ShirtStyle);
		}
		if (shield != null) {
			variables.flagEquipment(5, shield);
		} else {
			variables.clearFlag(5);
		}
		if (chest != null && chest.getData().isFullBody()) {
			variables.clearFlag(6);
		} else {
			variables.flagStyle(6, SleeveStyle);
		}
		if (legs != null) {
			variables.flagEquipment(7, legs);
		} else {
			variables.flagStyle(7, PantsStyle);
		}
		if (head != null && (head.getData().isFullMask() || head.getData().isFullHat())) {
			variables.clearFlag(8);
		} else {
			variables.flagStyle(8, HairStyle);
		}
		if (hands != null) {
			variables.flagEquipment(9, hands);
		} else {
			variables.flagStyle(9, HandStyle);
		}
		if (feet != null) {
			variables.flagEquipment(10, feet);
		} else {
			variables.flagStyle(10, ShoeStyle);
		}
		if (head != null && head.getData().isFullMask()) {
			variables.clearFlag(11);
		} else {
			variables.flagStyle(11, BeardStyle);
		}

		player.getModel().registerFlag(new AppearanceFlag(variables));
	}

	public int[] getStyles() {
		return new int[] { shirt, sleeves, pants, hair, hands, shoes, beard };
	}

}
