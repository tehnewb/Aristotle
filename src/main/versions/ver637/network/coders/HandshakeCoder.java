package versions.ver637.network.coders;

import java.util.List;

import com.framework.io.RSStream;
import com.framework.network.RSNetworkSession;
import com.framework.network.RSSessionCoder;

import versions.ver637.cache.CacheResource;

public class HandshakeCoder implements RSSessionCoder {

	private static final int[] UpdateKeys = new int[] { 56, 79325, 55568, 46770, 24563, 299978, 44375, 0, 4176, 3589, 109125, 604031, 176138, 292288, 350498, 686783, 18008, 20836, 16339, 1244, 8142, 743, 119, 699632, 932831, 3931, 2974 };

	private static final byte JS5 = 15;
	private static final byte LOGIN = 14;

	@Override
	public void decode(RSNetworkSession session, RSStream stream, List<Object> out) {
		int request = stream.readUnsignedByte();

		switch (request) {
			case JS5 -> {
				int revision = stream.readInt();

				if (revision != CacheResource.Revision)
					throw new UnsupportedOperationException("Revision " + revision + " not supported on JS5");

				RSStream response = new RSStream(UpdateKeys.length * 4 + 1);
				response.writeByte(0);
				for (int key : UpdateKeys)
					response.writeInt(key);
				out.add(response);

				session.setCoder(new CacheUpdateCoder());
			}
			case LOGIN -> {
				RSStream response = new RSStream(1);
				response.writeByte(0);

				out.add(response);
				session.setCoder(new LoginCoder());
			}
		}
	}

	@Override
	public RSStream encode(RSNetworkSession session, RSStream in) {
		return in;
	}

}
