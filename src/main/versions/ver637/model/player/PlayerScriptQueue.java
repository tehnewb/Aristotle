package versions.ver637.model.player;

import com.framework.mechanics.queue.RSScriptQueue;

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
