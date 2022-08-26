package versions.ver637.network.coders;

import java.util.List;

import com.framework.io.RSStream;
import com.framework.io.XTEAKey;
import com.framework.map.RSLocation;
import com.framework.network.RSFrame;
import com.framework.network.RSNetworkSession;
import com.framework.network.RSSessionCoder;

import versions.ver637.cache.CacheResource;
import versions.ver637.model.player.Player;
import versions.ver637.network.account.Account;
import versions.ver637.network.account.AccountLoginResponse;
import versions.ver637.network.account.AccountResource;
import versions.ver637.network.coders.frames.RegionFrame;
import versions.ver637.network.coders.frames.RegionFrame.View;

public class LoginCoder implements RSSessionCoder {

	/**
	 * The stage code of the lobby
	 */
	private static final int LOBBY_STAGE = 19;

	/**
	 * The stage code of the lobby
	 */
	private static final int LOGIN_STAGE = 16;

	/**
	 * The stage code of the lobby
	 */
	private static final int REJOIN_STAGE = 18;

	@Override
	public void decode(RSNetworkSession session, RSStream in, List<Object> out) {
		if (in.readableBytes() < 3)
			return;

		int request = in.readUnsignedByte();
		int packetSize = in.readUnsignedShort();
		if (packetSize != in.readableBytes())
			throw new IllegalStateException("Invalid login packet size");

		int revision = in.readInt();

		if (revision != CacheResource.Revision)
			throw new IllegalStateException("Unsupported revision " + revision + " on login coder");

		do {} while (in.readByte() != 10);

		int[] keysIn = in.readIntArray(4);

		in.readLong();
		String password = in.readRSString();
		in.skip(16);

		XTEAKey key = new XTEAKey(keysIn);
		RSStream streamToDecipher = new RSStream(in.readBytes(in.readableBytes()));
		key.decipher(streamToDecipher, 0, streamToDecipher.capacity());
		String username = streamToDecipher.readRSString().toLowerCase();

		if (Player.get(username) != null) {
			session.write(RSFrame.raw().writeByte(AccountLoginResponse.StillLoggedIn.getCode()));
			return;
		}

		AccountResource resource = new AccountResource(username, password, a -> {
			AccountLoginResponse response = a.response();
			session.write(RSFrame.raw().writeByte(2));
			Player player = new Player(session, a.account());
			session.set("Player", player);
			Player.add(player);
			if (response == AccountLoginResponse.SuccessfulLogin) {
				session.setCoder(new GameCoder(null, null));
				if (request == LOBBY_STAGE) {
					session.write(createLobbyResponse(a.account()));
				} else if (request == LOGIN_STAGE || request == REJOIN_STAGE) {
					session.write(createLoginResponse(a.account()));
					session.write(new RegionFrame(new RSLocation(3222, 3222, 0), View.Huge, true, true));
				}
			}
		});
		resource.queue();

	}

	@Override
	public RSStream encode(RSNetworkSession session, RSStream in) {
		return in;
	}

	private RSFrame createLoginResponse(Account account) {
		RSFrame loginResponse = RSFrame.raw();
		loginResponse.writeByte(13); // length
		loginResponse.writeByte(account.getRank());
		loginResponse.writeByte(0);
		loginResponse.writeByte(0);
		loginResponse.writeByte(0);
		loginResponse.writeByte(1);
		loginResponse.writeByte(0);
		loginResponse.writeShort(1); // player index
		loginResponse.writeByte(1);
		loginResponse.writeMedium(0);
		loginResponse.writeByte(1); // members
		return loginResponse;
	}

	private RSFrame createLobbyResponse(Account account) {
		RSFrame lobbyResponse = RSFrame.raw();
		lobbyResponse.writeBytes(new byte[5]);
		lobbyResponse.writeShort(30); // member days left
		lobbyResponse.writeShort(1); // recovery questions
		lobbyResponse.writeShort(0); // unread messages
		lobbyResponse.writeShort(0);
		lobbyResponse.writeInt(0); // last ip
		lobbyResponse.writeByte(3); // email status (0 - no email, 1 - pending, 2 - pending, 3 - registered)
		lobbyResponse.writeShort(0);
		lobbyResponse.writeShort(0);
		lobbyResponse.writeByte(0);
		lobbyResponse.writeGJString(account.getUsername());
		lobbyResponse.writeByte(0);
		lobbyResponse.writeInt(1);
		lobbyResponse.writeShort(1); // current world id
		lobbyResponse.writeGJString("None");
		lobbyResponse.limit(lobbyResponse.position());

		RSFrame wrapper = RSFrame.raw();
		wrapper.writeByte(lobbyResponse.position());
		wrapper.writeBytes(lobbyResponse.buffer());
		return wrapper;
	}

}
