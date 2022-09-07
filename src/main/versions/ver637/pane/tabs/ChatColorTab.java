package versions.ver637.pane.tabs;

import versions.ver637.pane.ComponentClick;
import versions.ver637.pane.GameInterfaceAdapter;

public class ChatColorTab extends GameInterfaceAdapter {

	public static final int ChatColorTab = 982;

	public ChatColorTab() {
		super(ChatColorTab, true);
	}

	@Override
	public void onOpen() {
		this.setVarp(GraphicSettingsTab.PrivateChatColorVarp, player.getChatVariables().getPrivateChatColor());
	}

	@Override
	public void click(ComponentClick data) {
		if (data.componentID() == 5) {
			player.getPane().open(new GraphicSettingsTab());
			return;
		}
		if (data.componentID() == 37) {
			player.getChatVariables().setPrivateChatColor(0);
			this.setVarp(GraphicSettingsTab.PrivateChatColorVarp, player.getChatVariables().getPrivateChatColor());
			return;
		}
		if (data.componentID() >= 13 && data.componentID() <= 32) {
			int index = data.componentID() - 13;

			player.getChatVariables().setClanChatColor(index);
			this.setVarp(GraphicSettingsTab.CombinationVarp, GraphicSettingsTab.getCombination(player));
		} else if (data.componentID() >= 45 && data.componentID() <= 62) {
			int index = data.componentID() - 44;

			player.getChatVariables().setPrivateChatColor(index);
			this.setVarp(GraphicSettingsTab.PrivateChatColorVarp, player.getChatVariables().getPrivateChatColor());
		}
	}

	@Override
	public void onClose() {
		player.getPane().open(new GraphicSettingsTab());
	}

	@Override
	public boolean clickThrough() {
		return true;
	}

	@Override
	public int position(boolean resizable) {
		return resizable ? 99 : 214;
	}

}
