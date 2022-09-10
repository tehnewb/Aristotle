package versions.ver637.model.player.mechanics;

/**
 * This script shows others how to use the player script
 */
import com.framework.mechanics.queue.RSQueueType;

public class TestScript extends PlayerScript {

	@Override
	public void process() {
		msg("Hey"); // messages the player "Hey"
		
	}

	@Override
	public RSQueueType type() {
		return RSQueueType.Normal;
	}
}
