package versions.ver637.model.player;

import com.google.common.base.Strings;

import lombok.Getter;
import lombok.Setter;
import versions.ver637.model.player.clan.Clan;
import versions.ver637.model.player.clan.ClanMember;
import versions.ver637.model.player.clan.ClanVariables;
import versions.ver637.network.coders.frames.ClanMessageFrame;
import versions.ver637.network.coders.frames.PublicChatFrame;

@Getter
@Setter
public class ChatVariables {

	private static final String[] Profanity = { "ass", "bitch", "nigger", "fuck", "shit", "gay" };

	private transient Chat currentChat;
	private transient QuickChat currentQuickChat;
	private transient boolean messagingClan;

	private boolean profanityFilter;
	private boolean chatEffects = true;
	private int privateChatColor;
	private int clanChatColor;

	public static void processChat(Player player) {
		ChatVariables variables = player.getChatVariables();
		if (variables.getCurrentChat() != null) {
			Chat chat = variables.getCurrentChat();
			if (variables.isMessagingClan()) {
				ClanVariables clanVariables = player.getClanVariables();
				String clanName = clanVariables.getClanName();
				Clan clan = clanName == null ? null : Clan.getClan(clanName);

				variables.setMessagingClan(false);

				if (clan != null) {
					for (ClanMember member : clan.getMembers()) {
						Player other = Player.get(member.username());
						if (other == null)
							continue;

						if (FriendVariables.isIgnoring(other, player))
							continue;

						if (other.getChatVariables().isProfanityFilter()) {
							other.getSession().write(new ClanMessageFrame(player, clan, chat.filtered()));
						} else {
							other.getSession().write(new ClanMessageFrame(player, clan, chat.text()));
						}
					}
					variables.setCurrentChat(null);
					return;
				}
			}
			if (variables.isProfanityFilter()) {
				player.getSession().write(new PublicChatFrame(player, chat.filtered(), chat.chatEffects()));
			} else {
				player.getSession().write(new PublicChatFrame(player, chat.text(), chat.chatEffects()));
			}
			for (Player other : player.getModel().getLocalPlayers()) {
				if (FriendVariables.isIgnoring(other, player))
					continue;

				if (other.getChatVariables().isProfanityFilter()) {
					other.getSession().write(new PublicChatFrame(player, chat.filtered(), chat.chatEffects()));
				} else {
					other.getSession().write(new PublicChatFrame(player, chat.text(), chat.chatEffects()));
				}
			}

			variables.setCurrentChat(null);
		} else if (variables.getCurrentQuickChat() != null) {
			QuickChat chat = variables.getCurrentQuickChat();
			if (variables.isMessagingClan()) {
				ClanVariables clanVariables = player.getClanVariables();
				String clanName = clanVariables.getClanName();
				Clan clan = clanName == null ? null : Clan.getClan(clanName);

				variables.setMessagingClan(false);

				if (clan != null) {
					for (ClanMember member : clan.getMembers()) {
						Player other = Player.get(member.username());
						if (other == null)
							continue;

						if (FriendVariables.isIgnoring(other, player))
							continue;

						if (other.getChatVariables().isProfanityFilter()) {
							other.getSession().write(new ClanMessageFrame(player, clan, "quickchat"));
						} else {
							other.getSession().write(new ClanMessageFrame(player, clan, "quickchat"));
						}
					}
					variables.setCurrentChat(null);
					return;
				}
			}
			player.getSession().write(new PublicChatFrame(player, chat.fileID(), "0"));
			for (Player other : player.getModel().getLocalPlayers()) {
				if (FriendVariables.isIgnoring(other, player))
					continue;
				other.getSession().write(new PublicChatFrame(player, chat.fileID(), "0"));
			}
			variables.setCurrentQuickChat(null);
		}
	}

	public record QuickChat(int fileID) {}

	public record Chat(String text, int chatEffects) {

		public String filtered() {
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
