package versions.ver637.pane.tabs;

import versions.ver637.model.player.Player;
import versions.ver637.pane.ComponentClick;
import versions.ver637.pane.GameInterface;
import versions.ver637.pane.primary.GraphicSelectionInterface;
import versions.ver637.pane.primary.SoundSelectionInterface;

public class GraphicSettingsTab extends GameInterface {

	public static final int GraphicSettingsID = 261;
	public static final int CombinationVarp = 1438;
	public static final int ChatEffectsVarp = 171;
	public static final int MouseButtonsVarp = 170;
	public static final int AcceptingAidVarp = 427;
	public static final int PrivateChatColorVarp = 287;

	public static final int ProfanityFilterComponent = 3;
	public static final int ChatEffectsComponent = 4;
	public static final int PrivateChatComponent = 5;
	public static final int MouseButtonsComponent = 6;
	public static final int AcceptAidComponent = 7;
	public static final int GraphicSelectionComponent = 14;
	public static final int SoundSelectionComponent = 16;

	public GraphicSettingsTab() {
		super(GraphicSettingsID, true);
	}

	@Override
	public void onOpen() {
		this.setVarp(ChatEffectsVarp, player.getChatVariables().isChatEffects() ? 0 : 1);
		this.setVarp(MouseButtonsVarp, player.getMiscVariables().isSingleMouseButton() ? 1 : 0);
		this.setVarp(AcceptingAidVarp, player.getMiscVariables().isAcceptingAid() ? 1 : 0);
		this.setVarp(PrivateChatColorVarp, player.getChatVariables().getPrivateChatColor());
		this.setVarp(CombinationVarp, getCombination(player));
	}

	@Override
	public void click(ComponentClick data) {
		switch (data.componentID()) {
			case ProfanityFilterComponent -> {
				player.getChatVariables().setProfanityFilter(!player.getChatVariables().isProfanityFilter());
				this.setVarp(CombinationVarp, player.getChatVariables().isProfanityFilter() ? 0 : -1);
			}
			case ChatEffectsComponent -> {
				player.getChatVariables().setChatEffects(!player.getChatVariables().isChatEffects());
				this.setVarp(ChatEffectsVarp, player.getChatVariables().isChatEffects() ? 0 : 1);
			}
			case PrivateChatComponent -> {
				player.getPane().open(new ChatColorTab());
			}
			case MouseButtonsComponent -> {
				player.getMiscVariables().setSingleMouseButton(!player.getMiscVariables().isSingleMouseButton());
				this.setVarp(MouseButtonsVarp, player.getMiscVariables().isSingleMouseButton() ? 1 : 0);
			}
			case AcceptAidComponent -> {
				player.getMiscVariables().setAcceptingAid(!player.getMiscVariables().isAcceptingAid());
				this.setVarp(AcceptingAidVarp, player.getMiscVariables().isAcceptingAid() ? 1 : 0);
			}
			case GraphicSelectionComponent -> {
				player.getPane().open(new GraphicSelectionInterface());
			}
			case SoundSelectionComponent -> {
				player.getPane().open(new SoundSelectionInterface());
			}
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
		return resizable ? 99 : 214;
	}

	public static int getCombination(Player player) {
		int clanChatColor = player.getChatVariables().getClanChatColor();
		boolean profanityFilter = player.getChatVariables().isProfanityFilter();
		int taskMasterEmote = 0;
		return (3612 << (profanityFilter ? 4 : 3)) | (clanChatColor) | (taskMasterEmote);
	}
}
