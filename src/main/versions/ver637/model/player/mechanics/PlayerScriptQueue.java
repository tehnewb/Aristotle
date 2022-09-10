package versions.ver637.model.player.mechanics;

import com.framework.mechanics.queue.RSScriptQueue;

import versions.ver637.model.player.Player;

public class PlayerScriptQueue extends RSScriptQueue<Player> {

	public PlayerScriptQueue(Player owner) {
		super(owner);
	}

	@Override
	public boolean hasNonModalInterfaces() {
		return owner.getPane().hasNonModal();
	}

	@Override
	public void closeNonModalInterfaces() {
		owner.getPane().closeNonModal();
	}

}
