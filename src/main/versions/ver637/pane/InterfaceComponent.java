package versions.ver637.pane;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import versions.ver637.model.player.Player;
import versions.ver637.network.coders.frames.ComponentHiddenFrame;
import versions.ver637.network.coders.frames.ComponentSettingsFrame;
import versions.ver637.network.coders.frames.ComponentTextFrame;

@RequiredArgsConstructor
@Getter
public class InterfaceComponent {

	private final int ID;

	@Setter
	private Player player;
	@Setter
	private Interface parent;
	private ComponentSettings settings;
	private String text;
	@Setter
	private String[] options;
	private boolean hidden;

	public void setSettings(ComponentSettings settings, int offset, int length) {
		this.settings = settings;
		if (player != null)
			player.getSession().write(new ComponentSettingsFrame(settings.getValue(), parent.getID(), ID, offset, length));
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
		if (player != null)
			player.getSession().write(new ComponentHiddenFrame(parent.getID(), ID, hidden));
	}

	public void setText(String text) {
		this.text = text;
		if (player != null)
			player.getSession().write(new ComponentTextFrame(parent.getID(), ID, text));
	}
}
