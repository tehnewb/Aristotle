package versions.ver637.network.coders.handlers;

import com.framework.network.RSFrame;
import com.framework.network.RSNetworkSession;

import versions.ver637.network.coders.RSFrameHandler;

public class PingPacketHandler implements RSFrameHandler {

	@Override
	public void handleFrame(RSNetworkSession session, RSFrame frame) {}

	@Override
	public int[] opcodesHandled() {
		return new int[] { 12 };
	}

}
