package versions.ver637.network.coders.frames;

import com.framework.network.RSFrame;

import versions.ver637.model.player.skills.Skill;

public class SkillFrame extends RSFrame {

	public static final int SkillOpcode = 8;

	public SkillFrame(Skill skill) {
		super(SkillOpcode, StandardType);
		writeInt1((int) skill.getExperience());
		writeByteS(skill.getType().getID());
		writeByteS(skill.getLevel() + skill.getModifier());
	}
}
