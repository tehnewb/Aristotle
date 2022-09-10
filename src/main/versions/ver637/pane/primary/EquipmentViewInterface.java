package versions.ver637.pane.primary;

import versions.ver637.model.player.equipment.EquipmentVariables;
import versions.ver637.model.player.equipment.UnequipItemScript;
import versions.ver637.pane.ComponentClick;
import versions.ver637.pane.ComponentSettings;
import versions.ver637.pane.GameInterfaceAdapter;
import versions.ver637.pane.side.EquipmentSideInterface;

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
			player.getScripts().queue(new UnequipItemScript(data.slot()));
		}
	}

	@Override
	public void onClose() {
		player.getPane().close(EquipmentSideInterface.class);
	}

	public void displayBonuses() {
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
		setVarc(779, player.getAppearanceVariables().getRenderAnimation());
	}

}
