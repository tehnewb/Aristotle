package versions.ver637.model.player;

import java.util.ArrayList;

import com.framework.RSFramework;
import com.framework.util.StringUtil;

import lombok.Getter;
import lombok.Setter;
import versions.ver637.network.account.SimpleAccountResource;
import versions.ver637.network.coders.frames.ReceivePrivateMessageFrame;
import versions.ver637.network.coders.frames.SendPrivateMessageFrame;
import versions.ver637.network.coders.frames.UpdateFriendFrame;
import versions.ver637.network.coders.frames.UpdateFriendFrame.FriendState;

@Getter
@Setter
public class FriendVariables {

	private ArrayList<String> friends = new ArrayList<>();

	public static void initializeFriendsList(Player player) {
		FriendVariables variables = player.getFriendVariables();

		player.getSession().write(new UpdateFriendFrame());

		for (String name : variables.getFriends()) {
			Player friend = Player.get(name);

			if (friend == null) {
				RSFramework.queueResource(new SimpleAccountResource(name), account -> {
					if (account == null) {
						//TODO account does not exist for some reason
						return;
					}
					player.getSession().write(new UpdateFriendFrame(account, FriendState.Offline));
				});
				continue;
			}

			FriendState state = friend.getModel().isInWorld() ? FriendState.World : FriendState.Lobby;
			player.getSession().write(new UpdateFriendFrame(friend.getAccount(), state));
		}
	}

	public static void alertOnline(Player player) {
		FriendVariables variables = player.getFriendVariables();

		for (String name : variables.getFriends()) {
			Player friend = Player.get(name);
			if (friend == null)
				continue;

			if (isMutualFriend(player, friend)) {
				FriendState state = player.getModel().isInWorld() ? FriendState.World : FriendState.Lobby;
				friend.getSession().write(new UpdateFriendFrame(player.getAccount(), state));
			}
		}

	}

	public static void alertOffline(Player player) {
		FriendVariables variables = player.getFriendVariables();
		for (String name : variables.getFriends()) {
			Player friend = Player.get(name);
			if (friend == null)
				continue;
			if (isMutualFriend(player, friend)) {
				friend.getSession().write(new UpdateFriendFrame(player.getAccount(), FriendState.Offline));
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
					player.sendMessage("That username does not exist.");
					return;
				}

				variables.getFriends().add(username);
				player.getSession().write(new UpdateFriendFrame(account, FriendState.Offline));
			});
			return;
		}
		FriendState state = other.getModel().isInWorld() ? FriendState.World : FriendState.Lobby;
		player.getSession().write(new UpdateFriendFrame(other.getAccount(), state));

		variables.getFriends().add(username);
	}

	public static void removeFriend(Player player, String username) {
		FriendVariables variables = player.getFriendVariables();

		variables.getFriends().remove(username);
	}

	public static void sendPrivateMessage(Player sender, String receiver, String message) {
		Player other = Player.get(receiver);
		if (other == null) {
			sender.sendMessage("{0} is not online.", StringUtil.upperFirst(receiver));
			return;
		}

		other.getSession().write(new ReceivePrivateMessageFrame(sender, message));
		sender.getSession().write(new SendPrivateMessageFrame(sender, message));
	}

	public static boolean isMutualFriend(Player player, Player other) {
		FriendVariables variables = player.getFriendVariables();
		FriendVariables variablesOther = other.getFriendVariables();

		boolean hasOther = variables.getFriends().stream().anyMatch(other.getAccount().getUsername()::equalsIgnoreCase);
		boolean hasPlayer = variablesOther.getFriends().stream().anyMatch(player.getAccount().getUsername()::equalsIgnoreCase);
		return hasOther && hasPlayer;
	}

}
