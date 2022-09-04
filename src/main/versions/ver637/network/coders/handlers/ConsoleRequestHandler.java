package versions.ver637.network.coders.handlers;

import java.util.Arrays;

import com.framework.network.RSFrame;

import versions.ver637.game.commands.Command;
import versions.ver637.model.player.Player;
import versions.ver637.network.coders.FrameHandler;

public class ConsoleRequestHandler implements FrameHandler {

	@Override
	public void handleFrame(Player player, RSFrame frame) {
		frame.skip(2);

		String string = frame.readRSString();
		String[] split = string.split(" ");
		String name = split[0].toLowerCase();
		String[] args = Arrays.copyOfRange(split, 1, split.length);

		Command command = Command.getCommand(name);
		try {
			command.onExecute(player, args);
		} catch (Throwable t) {
			t.printStackTrace();
			command.onFail(player);
		}
	}

	@Override
	public int[] opcodesHandled() {
		return new int[] { 79 };
	}

}
