package versions.ver637.network.coders;

import com.framework.network.RSFrame;

import versions.ver637.model.player.Player;

public interface FrameHandler {

	void handleFrame(Player player, RSFrame frame);

	int[] opcodesHandled();

}
