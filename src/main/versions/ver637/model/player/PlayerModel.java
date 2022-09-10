package versions.ver637.model.player;

import java.util.BitSet;

import com.framework.entity.RSEntityList;
import com.framework.map.RSChunk;
import com.framework.network.RSFrame;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import versions.ver637.model.UpdateFlag;
import versions.ver637.model.UpdateModel;
import versions.ver637.model.player.flags.MovementFlag;

@RequiredArgsConstructor
public class PlayerModel extends UpdateModel {

	private final Player player;
	private final BitSet local = new BitSet(2048);

	@Getter
	@Setter
	private boolean inWorld;

	@Override
	public void prepare() {
		player.getScripts().process();
		player.getTimers().process();

		LocationVariables.processRoute(player);
		TickVariables.processTicks(player);
		ChatVariables.processChat(player);
	}

	@Override
	public void update() {
		if (!inWorld)
			return;

		RSFrame frame = RSFrame.varShort(70);
		RSFrame block = new RSFrame();

		frame.beginBitAccess();
		writePlayerFlags(block, player);
		frame.writeBits(1, isUpdateRequired() ? 1 : 0);

		if (isUpdateRequired())
			writeUpdateLocalPlayer(frame, player);

		for (int index = 1; index < local.length(); index++) {
			Player other = Player.get(index);

			if (!local.get(index))
				continue;

			if (other == null || !other.getLocation().inRange(player.getLocation(), 16) || !other.getModel().isInWorld()) {
				frame.writeBits(1, 1);
				frame.writeBits(11, index);
				frame.writeBits(1, 0);
				frame.writeBits(2, 0);
				frame.writeBits(1, 0);
				local.clear(index);
			} else {
				writePlayerFlags(block, other);
				frame.writeBits(1, other.getModel().isUpdateRequired() ? 1 : 0);

				if (other.getModel().isUpdateRequired()) {
					frame.writeBits(11, other.getIndex());
					writeUpdateLocalPlayer(frame, other);
				}
			}
		}

		RSEntityList<Player> players = Player.getOnlinePlayers();
		for (int index = players.head(); index <= players.tail(); index++) {
			Player other = players.get(index);
			if (index == player.getIndex())
				continue;

			if (other == null || local.get(index) || !other.getLocation().inRange(player.getLocation(), 16))
				continue;

			if (!other.getModel().isInWorld())
				continue;

			AppearanceVariables.updateAppearance(other);

			local.set(index);

			frame.writeBits(1, 1);
			frame.writeBits(11, other.getIndex());
			writeAddLocalPlayer(frame, other);
			writePlayerFlags(block, other);
		}

		frame.writeBits(1, 0);
		frame.finishBitAccess();
		frame.writeBytes(block);

		player.getSession().write(frame);
	}

	private void writeAddLocalPlayer(RSFrame frame, Player other) {
		frame.writeBits(2, 0);
		frame.writeBits(1, 1);
		frame.writeBits(2, 3);
		frame.writeBits(18, other.getLocation().packTo18Bits());
		frame.writeBits(6, other.getLocation().getX() % RSChunk.TileCount);
		frame.writeBits(6, other.getLocation().getY() % RSChunk.TileCount);
		frame.writeBits(1, 1);
	}

	private void writeUpdateLocalPlayer(RSFrame frame, Player other) {
		frame.writeBits(1, 1);

		if (other.getModel().activated(UpdateFlag.TeleportOrdinal)) {
			frame.writeBits(2, 3); // #3 is teleport bit
			frame.writeBits(1, 1);
			frame.writeBits(30, other.getLocation().getY() | other.getLocation().getZ() << 28 | other.getLocation().getX() << 14);
			return;
		}

		if (other.getModel().activated(UpdateFlag.MovementOrdinal)) {
			MovementFlag flag = other.getModel().getFlag(UpdateFlag.MovementOrdinal, MovementFlag.class);
			int walkDir = flag.getFlagValue();
			int runDir = flag.getFlagValue();
			if (flag.isRunning()) {
				frame.writeBits(2, 2); // #2 is run bit
				frame.writeBits(4, runDir);
			} else {
				frame.writeBits(2, 1); // #1 is walk bit
				frame.writeBits(3, walkDir);
			}
			return;
		}
		frame.writeBits(2, 0);
	}

	private void writePlayerFlags(RSFrame block, Player other) {
		if (!other.getModel().isUpdateRequired())
			return;

		int maskData = other.getModel().maskData();

		if (maskData > 128)
			maskData |= 0x20;
		if (maskData > 32768)
			maskData |= 0x800;
		block.writeByte((byte) maskData);
		if (maskData > 128)
			block.writeByte((byte) (maskData >> 8));
		if (maskData > 32768)
			block.writeByte((byte) (maskData >> 16));

		UpdateFlag[] flags = other.getModel().getFlags();
		for (int i = 0; i < flags.length; i++) {
			UpdateFlag updateFlag = flags[i];
			if (updateFlag == null)
				continue;
			updateFlag.write(block);
		}
	}

	@Override
	public void reset() {}

	/**
	 * Returns an array of local players.
	 * 
	 * @return local players
	 */
	public Player[] getLocalPlayers() {
		Player[] localPlayers = new Player[local.cardinality()];
		int count = 0;
		for (int i = 1; i < local.length(); i++) {
			if (local.get(i)) {
				Player player = Player.get(i);
				localPlayers[count++] = player;
			}
		}
		return localPlayers;
	}
}
