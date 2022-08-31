package versions.ver637.network.coders;

import java.util.List;

import com.framework.RSFramework;
import com.framework.io.RSStream;
import com.framework.io.XTEAKey;
import com.framework.network.RSNetworkSession;
import com.framework.network.RSSessionCoder;

import versions.ver637.cache.CacheResource;
import versions.ver637.network.account.AccountLoginRequest;
import versions.ver637.network.account.AccountLoadResource;

public class LoginCoder implements RSSessionCoder {

	@Override
	public void decode(RSNetworkSession session, RSStream in, List<Object> out) {
		if (in.readableBytes() < 3)
			return;

		int requestID = in.readUnsignedByte();
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

		AccountLoginRequest request = switch (requestID) {
			case 16, 18 -> AccountLoginRequest.Login;
			case 19 -> AccountLoginRequest.Lobby;
			default -> throw new IllegalArgumentException("Unexpected login request ID: " + requestID);
		};

		RSFramework.queueResource(new AccountLoadResource(session, request, username, password));
	}

	@Override
	public RSStream encode(RSNetworkSession session, RSStream in) {
		return in;
	}

}
