package versions.ver637.network.coders.handlers;

import com.framework.RSFramework;
import com.framework.map.RSLocation;
import com.framework.map.path.RSRouteBuilder;
import com.framework.network.RSFrame;
import com.google.common.primitives.Ints;

import lombok.extern.slf4j.Slf4j;
import versions.ver637.map.GameObject;
import versions.ver637.map.GameObjectReachRequest;
import versions.ver637.map.PlayerClickObjectEvent;
import versions.ver637.map.WorldMap;
import versions.ver637.model.player.Player;
import versions.ver637.network.coders.FrameHandler;

@Slf4j
public class GameObjectHandler implements FrameHandler {

	@Override
	public void handleFrame(Player player, RSFrame frame) {
		int index = Ints.indexOf(opcodesHandled(), frame.opcode());
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
		GameObject object = WorldMap.getMap().getGameObject(location);
		if (object == null) {
			object = new GameObject(objectID, location, 10, 0);
		}

		if (object.getID() != objectID) {
			log.error("Locale {} at {} does not match frame", objectID, location);
			return;
		}
		
		RSFramework.post(new PlayerClickObjectEvent(player, object, object.getData().getOptions()[index]));

		RSRouteBuilder builder = new RSRouteBuilder();
		builder.startingAt(player.getLocation());
		builder.endingAt(location);
		builder.target(object);
		builder.destWidth(object.getSizeX());
		builder.destHeight(object.getSizeY());
		builder.objectRotation(object.getRotation());
		builder.objectShape(object.getType());
		builder.accessBitMask(object.getAccessFlag());
		builder.reachRequest(new GameObjectReachRequest(player, object.getData().getOptions()[index]));
		player.getLocationVariables().setRunning(running || player.getLocationVariables().isRunning());
		player.getLocationVariables().setRoute(builder.findPath());
	}

	@Override
	public int[] opcodesHandled() {
		return new int[] { 76, 55, 81, 60, 25, 48 };
	}

}
