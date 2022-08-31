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

	private byte gender;
	private short torso;
	private short arms;
	private short legs;
	private short head;
	private short hands;
	private short feet;
	private short beard;
	private short hairColor;
	private short torsoColor;
	private short legColor;
	private short feetColor;
	private short skinColor;
	private short renderAnimation = 1426;
	private short skullIcon = -1;
	private short prayerIcon = -1;
	private short titleID;

	public AppearanceVariables() {
		gender = 0;
		head = 309;
		torso = 443;
		arms = 599;
		hands = 390;
		legs = 646;
		feet = 438;
		beard = 307;
		hairColor = 6;
		torsoColor = 40;
		legColor = 216;
		feetColor = 4;
		skinColor = 0;
	}

}
