package versions.ver637.model.player;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(fluent = true, chain = true)
public class AppearanceVariables {

	public static final byte MaleGender = 0;
	public static final byte FemaleGender = 1;

	private byte gender = MaleGender;
	private short torso = 443;
	private short arms = 599;
	private short legs = 646;
	private short head = 309;
	private short hands = 390;
	private short feet = 438;
	private short beard = 307;
	private short hairColor = 6;
	private short torsoColor = 40;
	private short legColor = 216;
	private short feetColor = 4;
	private short skinColor;
	private short renderAnimation = 1426;
	private short skullIcon = -1;
	private short prayerIcon = -1;
	private short titleID;
	private String username;
	private String previousUserName;

}
