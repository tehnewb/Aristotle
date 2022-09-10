package versions.ver637.model.player.inventory;

import com.framework.mechanics.queue.RSQueueType;
import com.framework.mechanics.queue.RSScript;

import lombok.RequiredArgsConstructor;
import versions.ver637.model.player.Player;

@RequiredArgsConstructor
public class SwapItemScript implements RSScript<Player> {

	private final int fromSlot;
	private final int toSlot;

	@Override
	public void process(Player player) {
		player.getInventory().swap(fromSlot, toSlot);
	}

	@Override
	public RSQueueType type() {
		return RSQueueType.Instant;
	}

}
