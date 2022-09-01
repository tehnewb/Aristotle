package versions.ver637.network.account;

import java.time.LocalDate;

import lombok.Data;
import versions.ver637.model.player.AppearanceVariables;
import versions.ver637.model.player.ChatVariables;
import versions.ver637.model.player.FriendVariables;
import versions.ver637.model.player.LocationVariables;
import versions.ver637.model.player.TickVariables;

@Data
public class Account {

	/**
	 * Holds all variables for the appearance update flag
	 */
	private AppearanceVariables appearanceVariables = new AppearanceVariables();

	/**
	 * Holds all variables regarding the player's location
	 */
	private LocationVariables locationVariables = new LocationVariables();

	/**
	 * Holds all variables regarding ticks
	 */
	private TickVariables tickVariables = new TickVariables();

	/**
	 * Holds all variables regarding chat
	 */
	private ChatVariables chatVariables = new ChatVariables();

	/**
	 * Holds all variables regarding friends
	 */
	private FriendVariables friendVariables = new FriendVariables();

	private String username;
	private String password;
	private String lastKnownIP = "127.0.0.1";
	private short memberDays = 30;
	private byte rank = 2;
	private byte unreadMessageCount = 0;
	private byte recoveryQuestionState = 1;
	private byte emailRegistrationState = 3;
	private String lastLoginDate = LocalDate.now().toString();

}
