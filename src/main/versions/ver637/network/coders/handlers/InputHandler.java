package versions.ver637.network.coders.handlers;

import com.framework.network.RSFrame;

import versions.ver637.model.player.Player;
import versions.ver637.network.coders.FrameHandler;

public class InputHandler implements FrameHandler {

	public static final int IntRequestOpcode = 34;
	public static final int StringRequestOpcode = 37;
	public static final int LongStringRequestOpcode = 63;

	@Override
	public void handleFrame(Player player, RSFrame frame) {
		switch (frame.opcode()) {
			case IntRequestOpcode -> {
				int value = frame.readInt();

				player.getPane().getIntegerRequest().handleRequest(value);
			}
			case StringRequestOpcode -> {
				int length = frame.readableBytes() - 1;
				String value = new String(frame.readBytes(length));
				player.getPane().getStringRequest().handleRequest(value);
			}
			case LongStringRequestOpcode -> {
				int length = frame.readableBytes() - 1;
				String value = new String(frame.readBytes(length));
				player.getPane().getLongStringRequest().handleRequest(value);
			}
		}
	}

	@Override
	public int[] opcodesHandled() {
		return new int[] { IntRequestOpcode, StringRequestOpcode, LongStringRequestOpcode };
	}

}
