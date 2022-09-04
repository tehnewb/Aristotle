package versions.ver637.model.player;

import java.util.ArrayList;

import com.framework.RSFramework;
import com.framework.util.StringUtil;

import lombok.Getter;
import lombok.Setter;
import versions.ver637.model.player.ChatVariables.Chat;
import versions.ver637.network.account.Account;
import versions.ver637.network.account.SimpleAccountResource;
import versions.ver637.network.coders.frames.AddIgnoreFrame;
import versions.ver637.network.coders.frames.ReceivePrivateMessageFrame;
import versions.ver637.network.coders.frames.SendPrivateMessageFrame;
import versions.ver637.network.coders.frames.UnlockIgnoreFrame;
import versions.ver637.network.coders.frames.UpdateFriendFrame;
import versions.ver637.network.coders.frames.UpdateFriendFrame.FriendState;

@Getter
@Setter
public class FriendVariables {

	private ArrayList<String> friends = new ArrayList<>();
	private ArrayList<String> ignores = new ArrayList<>();

	public static void initializeFriendsList(Player player) {
		FriendVariables variables = player.getFriendVariables();

		player.getSession().write(new UpdateFriendFrame());

		for (String name : variables.getFriends()) {
			Player friend = Player.get(name);

			if (friend == null) {
				RSFramework.queueResource(new SimpleAccountResource(name), account -> {
					if (account == null)
						return;
					player.getSession().write(new UpdateFriendFrame(player, account, FriendState.Offline));
				});
				continue;
			}

			FriendState state = friend.getModel().isInWorld() ? FriendState.World : FriendState.Lobby;
			player.getSession().write(new UpdateFriendFrame(player, friend.getAccount(), state));
		}
	}

	public static void alertOnline(Player player) {
		for (Player other : Player.getOnlinePlayers()) {
			if (other == null)
				continue;
			ArrayList<String> friends = other.getFriendVariables().getFriends();

			if (friends.stream().anyMatch(player.getAccount().getUsername()::equalsIgnoreCase)) {
				FriendState state = player.getModel().isInWorld() ? FriendState.World : FriendState.Lobby;
				other.getSession().write(new UpdateFriendFrame(player, player.getAccount(), state));
			}
		}

	}

	public static void alertOffline(Player player) {
		for (Player other : Player.getOnlinePlayers()) {
			if (other == null)
				continue;
			ArrayList<String> friends = other.getFriendVariables().getFriends();

			if (friends.stream().anyMatch(player.getAccount().getUsername()::equalsIgnoreCase)) {
				other.getSession().write(new UpdateFriendFrame(player, player.getAccount(), FriendState.Offline));
			}
		}
	}

	public static void addFriend(Player player, String username) {
		FriendVariables variables = player.getFriendVariables();

		if (variables.getFriends().contains(username))
			return;

		Player other = Player.get(username);

		if (other == null) {
			RSFramework.queueResource(new SimpleAccountResource(username), account -> {
				if (account == null) {
					player.sendMessage("{0} does not exist.", StringUtil.upperFirst(username));
					return;
				}

				variables.getFriends().add(username);
				player.getSession().write(new UpdateFriendFrame(player, account, FriendState.Offline));
			});
			return;
		}
		FriendState state = other.getModel().isInWorld() ? FriendState.World : FriendState.Lobby;
		player.getSession().write(new UpdateFriendFrame(player, other.getAccount(), state));

		variables.getFriends().add(username);
	}

	public static void removeFriend(Player player, String username) {
		FriendVariables variables = player.getFriendVariables();

		variables.getFriends().remove(username);
	}

	public static void sendPrivateMessage(Player sender, String receiver, Chat chat) {
		Player other = Player.get(receiver);
		if (other == null) {
			sender.sendMessage("{0} is not online.", StringUtil.upperFirst(receiver));
			return;
		}

		sender.getSession().write(new SendPrivateMessageFrame(sender, sender.getChatVariables().isProfanityFilter() ? chat.filtered() : chat.text()));

		if (isIgnoring(other, sender))
			return;

		other.getSession().write(new ReceivePrivateMessageFrame(sender, other.getChatVariables().isProfanityFilter() ? chat.filtered() : chat.text()));
	}

	public static void initializeIgnoreList(Player player) {
		FriendVariables variables = player.getFriendVariables();
		ArrayList<Account> accounts = new ArrayList<>();
		ArrayList<String> ignores = variables.getIgnores();
		for (String name : ignores) {
			RSFramework.queueResource(new SimpleAccountResource(name), account -> {
				if (account == null)
					return;
				accounts.add(account);

				if (account.getUsername().equalsIgnoreCase(ignores.get(ignores.size() - 1)))
					player.getSession().write(new UnlockIgnoreFrame(accounts.toArray(Account[]::new)));
			});
		}
	}

	public static void addIgnore(Player player, String username) {
		FriendVariables variables = player.getFriendVariables();

		if (variables.getIgnores().contains(username))
			return;

		variables.getIgnores().add(username);

		RSFramework.queueResource(new SimpleAccountResource(username), account -> {
			if (account == null) {
				player.sendMessage("{0} does not exist.", StringUtil.upperFirst(username));
				return;
			}
			player.getSession().write(new AddIgnoreFrame(account));
		});
	}

	public static void removeIgnore(Player player, String username) {
		FriendVariables variables = player.getFriendVariables();

		variables.getIgnores().remove(username);
	}

	public static boolean isMutualFriend(Player player, Player other) {
		FriendVariables variables = player.getFriendVariables();
		FriendVariables variablesOther = other.getFriendVariables();

		boolean hasOther = variables.getFriends().stream().anyMatch(other.getAccount().getUsername()::equalsIgnoreCase);
		boolean hasPlayer = variablesOther.getFriends().stream().anyMatch(player.getAccount().getUsername()::equalsIgnoreCase);
		return hasOther && hasPlayer;
	}

	public static boolean isIgnoring(Player player, Player other) {
		FriendVariables variables = player.getFriendVariables();
		return variables.getIgnores().stream().anyMatch(other.getAccount().getUsername()::equalsIgnoreCase);
	}

}
