package versions.ver637.model.player.scripts;

import com.framework.mechanics.queue.RSQueueType;
import com.framework.mechanics.queue.RSScript;

import lombok.RequiredArgsConstructor;
import versions.ver637.model.player.Player;

@RequiredArgsConstructor
public class SwitchItemScript implements RSScript<Player> {

	private final int fromSlot;
	private final int toSlot;

	@Override
	public void process(Player owner) {
		owner.getInventory().swap(fromSlot, toSlot);
	}

	@Override
	public RSQueueType type() {
		return RSQueueType.Weak;
	}

}
