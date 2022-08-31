package versions.ver637.network.coders.handlers;

import com.framework.map.RSLocation;
import com.framework.map.path.RSPathFinderBuilder;
import com.framework.network.RSFrame;
import com.google.common.primitives.Ints;

import lombok.extern.slf4j.Slf4j;
import versions.ver637.map.GameObject;
import versions.ver637.map.GameObjectReachRequest;
import versions.ver637.map.WorldMap;
import versions.ver637.model.player.Player;
import versions.ver637.network.coders.FrameHandler;

@Slf4j
public class LocaleHandler implements FrameHandler {

	@Override
	public void handleFrame(Player player, RSFrame frame) {
		final int[] opcodes = { 76, 55, 81, 60, 25, 48 };
		int index = Ints.indexOf(opcodes, frame.opcode());
		int x = -1, y = -1;
		int objectID = -1;
		boolean running = false;
		switch (index) {
			case 0 -> {
				objectID = frame.readLEShort();
				running = frame.readByte() == 1;
				x = frame.readLEShortA();
				y = frame.readShortA();
			}
			case 1 -> {
				y = frame.readShort();
				objectID = frame.readLEShort();
				x = frame.readShort();
				running = frame.readByteS() == 1;
			}
			case 2 -> {
				y = frame.readLEShort();
				x = frame.readLEShort();
				objectID = frame.readShortA();
				running = frame.readByteS() == 1;
			}
			case 3 -> {}
			case 4 -> {
				x = frame.readShortA();
				objectID = frame.readLEShortA();
				running = frame.readByteA() == 1;
				y = frame.readLEShortA();
			}
			case 5 -> {
				objectID = frame.readUnsignedShort();
				return;
			}
		}
		RSLocation location = new RSLocation(x, y, player.getLocation().getZ());
		GameObject locale = WorldMap.getMap().getGameObject(location);
		if (locale == null) {
			locale = new GameObject(objectID, location, 10, 0);
		}

		if (locale.getID() != objectID) {
			log.error("Locale {} at {} does not match frame", objectID, location);
			return;
		}

		RSPathFinderBuilder builder = new RSPathFinderBuilder();
		builder.startingAt(player.getLocation());
		builder.endingAt(location);
		builder.target(locale);
		builder.destWidth(locale.getSizeX());
		builder.destHeight(locale.getSizeY());
		builder.objectRotation(locale.getRotation());
		builder.objectShape(locale.getType());
		builder.accessBitMask(locale.getAccessFlag());
		builder.reachRequest(new GameObjectReachRequest(player, locale.getData().getOptions()[index]));

		player.getAccount().getLocationVariables().setRunning(running);
		player.getAccount().getLocationVariables().setRoute(builder.findPath());
	}

	@Override
	public int[] opcodesHandled() {
		return new int[] { 25, 81, 76, 55, 60, 48 };
	}

}
