package versions.ver637.model.player.mechanics;

import com.framework.mechanics.timer.RSTimerQueue;

import versions.ver637.model.player.Player;
import versions.ver637.model.player.RunTimer;
import versions.ver637.model.player.prayer.PrayerTimer;

public class PlayerTimerQueue extends RSTimerQueue<Player> {

	public PlayerTimerQueue(Player owner) {
		super(owner);

		this.addTimer(new PrayerTimer());
		this.addTimer(new RunTimer());
	}

	@Override
	public boolean hasNonModalInterface() {
		return owner.getPane().hasNonModal();
	}

}
