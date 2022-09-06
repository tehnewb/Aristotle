package versions.ver637.pane.primary;

import versions.ver637.model.player.skills.SkillType;
import versions.ver637.pane.ComponentClick;
import versions.ver637.pane.GameInterfaceAdapter;

public class SkillGuideInterface extends GameInterfaceAdapter {

	public static final int SkillGuideID = 499;
	private final SkillType type;

	public SkillGuideInterface(SkillType type) {
		super(SkillGuideID, false);
		this.type = type;
	}

	@Override
	public void onOpen() {
		this.setVarp(965, type.getSkillGuideConfigValue());
	}

	@Override
	public void click(ComponentClick data) {
		this.setVarp(965, (1024 * (data.componentID() - 10)) + type.getSkillGuideConfigValue());
	}

}
