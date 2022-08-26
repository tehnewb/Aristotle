package versions.ver637.network.coders;

import com.framework.network.RSFrame;
import com.framework.network.RSNetworkSession;

public interface RSFrameHandler {

	void handleFrame(RSNetworkSession session, RSFrame frame);

	int[] opcodesHandled();

}
