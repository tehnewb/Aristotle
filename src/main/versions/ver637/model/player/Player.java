package versions.ver637.model.player;

import java.util.stream.Stream;

import com.framework.entity.RSEntity;
import com.framework.entity.RSEntityList;
import com.framework.network.RSNetworkSession;

import lombok.Getter;
import lombok.NonNull;
import versions.ver637.network.account.Account;
import versions.ver637.network.coders.frames.WindowPaneFrame;
import versions.ver637.window.RSWindow;

public class Player extends RSEntity {

	private static final RSEntityList<Player> OnlinePlayers = new RSEntityList<>(2048);

	@Getter
	private final RSNetworkSession session;

	@Getter
	private final Account account;

	@Getter
	private RSWindow pane;

	public Player(RSNetworkSession session, Account account) {
		this.session = session;
		this.account = account;
	}

	/**
	 * Sets the window pane of this {@code Player}.
	 * 
	 * @param pane the pane to set it to
	 */
	public void setWindowPane(RSWindow pane) {
		this.pane = pane;
		this.session.write(new WindowPaneFrame(pane.getID(), true));
	}

	/**
	 * Returns the player at the given {@code index}. If no player has been set to
	 * that index, null is returned.
	 * 
	 * @param index the index to get the player at
	 * @return the player; null possibly
	 */
	public static Player get(int index) {
		if (index < 1 || index > 2047)
			throw new IndexOutOfBoundsException("Player index must be between 1 and 2047 inclusive");
		return OnlinePlayers.get(index);
	}

	/**
	 * Returns the {@code Player} found in the list players with the username same
	 * as the given username. This will return null if no player has been set to the
	 * list.
	 * 
	 * @param username the username of the player
	 * @return the player found; null possibly
	 */
	public static Player get(String username) {
		Stream<Player> stream = Stream.of(OnlinePlayers.toArray(new Player[OnlinePlayers.size()]));
		Stream<Player> filtered = stream.filter(p -> p.getAccount().getUsername().equalsIgnoreCase(username));
		return filtered.findFirst().orElse(null);
	}

	/**
	 * Adds the given {@code player} to the player list. The player cannot be null.
	 * 
	 * @param player the player to add
	 */
	public static void add(@NonNull Player player) {
		OnlinePlayers.add(player);
	}

	/**
	 * Removes the player at the given {@code index} from the list of players.
	 * 
	 * @param index the index to remove
	 */
	public static void remove(int index) {
		if (index < 1 || index > 2047)
			throw new IndexOutOfBoundsException("Player index must be between 1 and 2047 inclusive");
		OnlinePlayers.remove(index);
	}

}
