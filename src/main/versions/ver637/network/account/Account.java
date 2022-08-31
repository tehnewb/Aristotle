package versions.ver637.network.account;

import lombok.Data;
import versions.ver637.model.player.AppearanceVariables;
import versions.ver637.model.player.LocationVariables;

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

	private String username;
	private String password;
	private String lastKnownIP = "127.0.0.1";
	private short memberDays = 30;
	private short daysFromLastLogin = 0;
	private byte rank = 2;
	private byte unreadMessageCount = 0;
	private byte recoveryQuestionState = 1;
	private byte emailRegistrationState = 3;

}
