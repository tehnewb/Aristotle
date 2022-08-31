package versions.ver637.network.account;

import java.io.File;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.framework.network.RSFrame;
import com.framework.network.RSNetworkSession;
import com.framework.resource.RSResource;
import com.framework.util.StringUtil;
import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;
import versions.ver637.cache.CacheResource;
import versions.ver637.model.player.Player;
import versions.ver637.model.player.flags.AppearanceFlag;
import versions.ver637.network.coders.GameCoder;
import versions.ver637.network.coders.frames.RegionFrame;

@RequiredArgsConstructor
public class AccountLoadResource implements RSResource<AccountLoginCallback> {

	private final RSNetworkSession session;
	private final AccountLoginRequest request;
	private final String username;
	private final String password;

	@Override
	public AccountLoginCallback load() throws Exception {
		if (Player.get(username) != null)
			return new AccountLoginCallback(null, AccountLoginResponse.StillLoggedIn);

		Gson gson = new Gson();
		String path = "./resources/" + CacheResource.Revision + "/accounts/" + username + ".json";
		File file = new File(path);
		if (file.exists()) {
			String jsonString = Files.readString(Paths.get(path));
			Account account = gson.fromJson(jsonString, Account.class);
			if (!account.getPassword().equals(password))
				return new AccountLoginCallback(account, AccountLoginResponse.InvalidCredentials);

			return new AccountLoginCallback(account, AccountLoginResponse.SuccessfulLogin);
		} else {
			Account account = new Account();
			account.setUsername(username);
			account.setPassword(password);
			Files.writeString(Paths.get(path), gson.toJson(account));
			return new AccountLoginCallback(account, AccountLoginResponse.SuccessfulLogin);
		}
	}

	@Override
	public void finish(AccountLoginCallback a) {
		AccountLoginResponse response = a.response();
		session.getChannel().writeAndFlush(RSFrame.raw().writeByte(response.getCode()));
		if (response == AccountLoginResponse.SuccessfulLogin) {
			Player player = new Player(session, a.account());
			Player.addToOnline(player);

			InetSocketAddress address = (InetSocketAddress) session.getChannel().remoteAddress();
			a.account().setLastKnownIP(address.getHostName());

			session.set("Player", player);
			session.setConnectionListener(new AccountLogoutListener());
			session.setCoder(new GameCoder(null, null));
			if (request == AccountLoginRequest.Lobby) {
				session.write(createLobbyResponse(a.account()));
			} else if (request == AccountLoginRequest.Login) {
				session.write(createLoginResponse(player));

				session.write(new RegionFrame(player, player.getAccount().getLocationVariables().getView(), true, true));
				player.getModel().setInWorld(true);
				player.getModel().registerFlag(new AppearanceFlag(player.getAccount().getAppearanceVariables()));
			}
		}
	}

	private RSFrame createLoginResponse(Player player) {
		RSFrame loginResponse = RSFrame.raw();
		loginResponse.writeByte(13); // length
		loginResponse.writeByte(player.getAccount().getRank());
		loginResponse.writeByte(0);
		loginResponse.writeByte(0);
		loginResponse.writeByte(0);
		loginResponse.writeByte(1);
		loginResponse.writeByte(0);
		loginResponse.writeShort(player.getIndex()); // player index
		loginResponse.writeByte(1);
		loginResponse.writeMedium(0);
		loginResponse.writeByte(player.getAccount().getMemberDays() > 0 ? 1 : 0); // members
		return loginResponse;
	}

	private RSFrame createLobbyResponse(Account account) {
		RSFrame lobbyResponse = RSFrame.raw();
		lobbyResponse.writeBytes(new byte[5]);
		lobbyResponse.writeShort(account.getMemberDays()); // member days left
		lobbyResponse.writeShort(account.getRecoveryQuestionState()); // recovery questions
		lobbyResponse.writeShort(account.getUnreadMessageCount()); // unread messages
		lobbyResponse.writeShort(7487 - account.getDaysFromLastLogin());
		lobbyResponse.writeInt(StringUtil.IPAddressToNumber(account.getLastKnownIP())); // last ip
		lobbyResponse.writeByte(account.getEmailRegistrationState()); // email status (0 - no email, 1 - pending, 2 - pending, 3 - registered)
		lobbyResponse.writeShort(0);
		lobbyResponse.writeShort(0);
		lobbyResponse.writeByte(0);
		lobbyResponse.writeGJString(account.getUsername());
		lobbyResponse.writeByte(0);
		lobbyResponse.writeInt(1);
		lobbyResponse.writeShort(1); // current world id
		lobbyResponse.writeGJString(account.getLastKnownIP());

		RSFrame wrapper = RSFrame.raw();
		wrapper.writeByte(lobbyResponse.position());
		wrapper.writeBytes(lobbyResponse);
		return wrapper;
	}
}
