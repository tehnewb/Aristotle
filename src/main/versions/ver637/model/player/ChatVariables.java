package versions.ver637.model.player;

import com.google.common.base.Strings;

import lombok.Getter;
import lombok.Setter;
import versions.ver637.network.coders.frames.PublicChatFrame;

@Getter
@Setter
public class ChatVariables {

	private static final String[] Profanity = { "ass", "bitch", "nigger", "fuck", "shit", "gay" };

	private transient Chat currentChat;

	private boolean profanityFilter;
	private int privateChatColor;

	public static void processChat(Player player) {
		ChatVariables variables = player.getChatVariables();
		Chat chat = variables.getCurrentChat();
		if (chat != null) {
			if (variables.isProfanityFilter()) {
				player.getSession().write(new PublicChatFrame(player, chat.getFiltered(), chat.chatEffects()));
			} else {
				player.getSession().write(new PublicChatFrame(player, chat.text(), chat.chatEffects()));
			}
			for (Player other : player.getModel().getLocalPlayers()) {
				if (other.getChatVariables().isProfanityFilter()) {
					player.getSession().write(new PublicChatFrame(player, chat.getFiltered(), chat.chatEffects()));
				} else {
					other.getSession().write(new PublicChatFrame(player, chat.text(), chat.chatEffects()));
				}
			}

			variables.setCurrentChat(null);
		}
	}

	public record Chat(String text, int chatEffects) {

		public String getFiltered() {
			String filtered = text;
			for (String p : Profanity) {
				int index = text.toLowerCase().indexOf(p.toLowerCase());
				if (index != -1) {
					String replace = filtered.substring(index, index + p.length());
					filtered = filtered.replace(replace, Strings.repeat("*", replace.length()));
				}
			}
			return filtered;
		}
	}

}
