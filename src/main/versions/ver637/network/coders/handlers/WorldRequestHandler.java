package versions.ver637.network.coders.handlers;

import com.framework.network.RSFrame;

import versions.ver637.model.player.Player;
import versions.ver637.network.coders.FrameHandler;

public class WorldRequestHandler implements FrameHandler {

	@Override
	public void handleFrame(Player player, RSFrame frame) {
		RSFrame buffer = RSFrame.varShort(98);
		buffer.writeByte(1);
		buffer.writeByte(2);
		buffer.writeByte(1);
		buffer.writeSmart(1); // world count
		buffer.writeSmart(225); // country id
		buffer.writeGJString("United States");
		buffer.writeSmart(0); // world start index
		buffer.writeSmart(2); // world end size + 1
		buffer.writeSmart(1); // world size
		buffer.writeSmart(1);
		buffer.writeByte(0); // world index
		buffer.writeInt(1); // world flags
		buffer.writeGJString(""); // activity
		buffer.writeGJString("127.0.0.1"); // host
		buffer.writeInt(0x94DA4A87);
		buffer.writeSmart(1);
		buffer.writeShort(0);

		player.getSession().write(buffer);
	}

	@Override
	public int[] opcodesHandled() {
		return new int[] { 84 };
	}

}
