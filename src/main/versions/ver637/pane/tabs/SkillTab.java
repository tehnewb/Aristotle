package versions.ver637.pane.tabs;

import versions.ver637.model.player.skills.SkillType;
import versions.ver637.model.player.skills.SkillVariables;
import versions.ver637.pane.ComponentClick;
import versions.ver637.pane.GameInterfaceAdapter;
import versions.ver637.pane.chat.IntegerRequest;
import versions.ver637.pane.primary.SkillGuideInterface;

public class SkillTab extends GameInterfaceAdapter {

	public static final int SkillID = 320;
	public static final int ExperienceCounterVarp = 1801;
	public static final int SkillTargetIDVarp = 1966;
	public static final int SkillTargetValueVarp = 1968;

	public SkillTab() {
		super(SkillID, true);
	}

	@Override
	public void onOpen() {
		SkillVariables.initializeSkills(player);

		this.setVarp(ExperienceCounterVarp, (int) (player.getSkillVariables().getExperienceCounter() * 10));
	}

	@Override
	public void click(ComponentClick data) {
		SkillVariables variables = player.getSkillVariables();
		SkillType type = SkillType.forButtonID(data.componentID());
		if (data.option() == 0) {
			player.getPane().open(new SkillGuideInterface(type));
		} else if (data.option() == 1) {
			player.getPane().requestInteger(new IntegerRequest("Set Level Target") {
				@Override
				public void handleRequest(int value) {
					if (value > 120) {
						player.sendMessage("You can only set a target up to level 120.");
						return;
					}
					variables.setTarget(type, value, true);
					SkillVariables.updateTargetLevels(player);
					player.sendMessage("Your target level for {0} has been set to {1}", type.name(), value);
				}
			});
		} else if (data.option() == 2) {
			player.getPane().requestInteger(new IntegerRequest("Set XP Target") {
				@Override
				public void handleRequest(int value) {
					if (value > 200000000) {
						player.sendMessage("You can only set target XP up to 200,000,000.");
						return;
					}
					variables.setTarget(type, value, false);
					SkillVariables.updateTargetLevels(player);
					player.sendMessage("Your target XP for {0} has been set to {1}", type.name(), String.format("%,d", value));
				}
			});
		} else if (data.option() == 3) {
			variables.clearTarget(type);
			SkillVariables.updateTargetLevels(player);
			player.sendMessage("Your target for {0} has been cleared", type.name());
		}
	}

	@Override
	public void onClose() {

	}

	@Override
	public boolean clickThrough() {
		return true;
	}

	@Override
	public int position(boolean resizable) {
		return resizable ? 89 : 204;
	}

}
