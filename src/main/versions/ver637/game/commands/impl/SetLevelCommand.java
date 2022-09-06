package versions.ver637.game.commands.impl;

import versions.ver637.game.commands.Command;
import versions.ver637.model.player.Player;
import versions.ver637.model.player.skills.Skill;
import versions.ver637.model.player.skills.SkillType;
import versions.ver637.network.coders.frames.SkillFrame;

public class SetLevelCommand implements Command {

	@Override
	public String[] names() {
		return new String[] { "setlevel", "lvl", "level" };
	}

	@Override
	public String description() {
		return "Sets the level of the given skill";
	}

	@Override
	public void onExecute(Player player, String... arguments) {
		int skillID = Integer.parseInt(arguments[0]);
		int level = Integer.parseInt(arguments[1]);

		SkillType type = SkillType.forID(skillID);
		Skill skill = player.getSkillVariables().getSkill(type);
		player.getSkillVariables().setLevel(type, level);
		player.getSession().write(new SkillFrame(skill));
	}

	@Override
	public void onFail(Player player) {
		player.sendMessage("Use command like this: setlevel [skillID] [skillLevel]");
	}

}
