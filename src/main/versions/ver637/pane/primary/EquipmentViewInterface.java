package versions.ver637.pane.primary;

import versions.ver637.model.player.EquipmentVariables;
import versions.ver637.pane.ComponentClick;
import versions.ver637.pane.ComponentSettings;
import versions.ver637.pane.ComponentSwap;
import versions.ver637.pane.GameInterfaceAdapter;

public class EquipmentViewInterface extends GameInterfaceAdapter {

	public static final int EquipmentViewID = 667;

	public EquipmentViewInterface() {
		super(EquipmentViewID, false);
	}

	@Override
	public void onOpen() {
		ComponentSettings settings = new ComponentSettings();
		settings.setSecondaryOption(0, true);
		settings.setSecondaryOption(1, true);
		settings.setSecondaryOption(2, true);
		settings.setSecondaryOption(3, true);
		this.getComponent(7).setSettings(settings, 0, 16);

		displayBonuses();

		player.getPane().open(new EquipmentSideInterface());
	}

	@Override
	public void click(ComponentClick data) {
		if (data.componentID() == 7) {
			EquipmentVariables.unEquip(player, data.slot());

			displayBonuses();
		}
	}

	@Override
	public void onClose() {
		player.getPane().close(EquipmentSideInterface.class);
	}

	private class EquipmentSideInterface extends GameInterfaceAdapter {

		public EquipmentSideInterface() {
			super(670, false);
		}

		@Override
		public void onOpen() {
			ComponentSettings settings = new ComponentSettings();
			for (int i = 0; i < 10; i++)
				settings.setSecondaryOption(i, true);
			settings.setUseOnSettings(true, true, true, true, false, true);
			settings.setDragDepth(1);
			settings.setCanDragOnto(true);
			settings.setIsUseOnTarget(true);
			this.getComponent(0).setSettings(settings, 0, 27);
		}

		@Override
		public void click(ComponentClick data) {
			if (data.componentID() == 0) {
				EquipmentVariables.equip(player, data.itemID(), data.slot());
				displayBonuses();
			}
		}

		@Override
		public void swap(ComponentSwap data) {
			player.getInventory().swap(data.fromSlot(), data.toSlot());
		}

		@Override
		public int position(boolean resizable) {
			return resizable ? 84 : 197;
		}

	}

	private void displayBonuses() {
		short[] bonuses = player.getEquipmentVariables().getBonuses();

		// Attack bonus
		getComponent(30).setText("Stab: " + (int) bonuses[EquipmentVariables.StabAttackBonus]);
		getComponent(31).setText("Slash: " + (int) bonuses[EquipmentVariables.SlashAttackBonus]);
		getComponent(32).setText("Crush: " + (int) bonuses[EquipmentVariables.CrushAttackBonus]);
		getComponent(33).setText("Magic: " + (int) bonuses[EquipmentVariables.MagicAttackBonus]);
		getComponent(34).setText("Ranged: " + (int) bonuses[EquipmentVariables.RangeAttackBonus]);
		// Defence bonus
		getComponent(35).setText("Stab: " + (int) bonuses[EquipmentVariables.StabDefenceBonus]);
		getComponent(36).setText("Slash: " + (int) bonuses[EquipmentVariables.SlashDefenceBonus]);
		getComponent(37).setText("Crush: " + (int) bonuses[EquipmentVariables.CrushDefenceBonus]);
		getComponent(38).setText("Magic: " + (int) bonuses[EquipmentVariables.MagicDefenceBonus]);
		getComponent(39).setText("Ranged: " + (int) bonuses[EquipmentVariables.RangeDefenceBonus]);
		getComponent(40).setText("Summoning: " + (int) bonuses[EquipmentVariables.SummoningDefenceBonus]);

		// Absorb bonus
		getComponent(41).setText("Absorb Melee: " + bonuses[EquipmentVariables.AbsorbMeleeBonus] + "%");
		getComponent(42).setText("Absorb Magic: " + bonuses[EquipmentVariables.AbsorbMagicBonus] + "%");
		getComponent(43).setText("Absorb Range: " + bonuses[EquipmentVariables.AbsorbRangeBonus] + "%");

		// Other bonus
		getComponent(44).setText("Strength: " + (int) bonuses[EquipmentVariables.StrengthBonus]);
		getComponent(45).setText("Ranged Strength: " + (int) bonuses[EquipmentVariables.RangeStrengthBonus]);
		getComponent(46).setText("Prayer: " + (int) bonuses[EquipmentVariables.PrayerBonus]);
		getComponent(47).setText("Magic Damage: " + bonuses[EquipmentVariables.MagicBonus] + "%");
		setVarc(779, player.getAppearanceVariables().renderAnimation());
	}

}
