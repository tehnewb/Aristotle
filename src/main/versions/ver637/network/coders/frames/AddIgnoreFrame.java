package versions.ver637.network.coders.frames;

import com.framework.network.RSFrame;

import versions.ver637.network.account.Account;

public class AddIgnoreFrame extends RSFrame {

	public static final int AddIgnoreOpcode = 85;

	public AddIgnoreFrame(Account account) {
		super(AddIgnoreOpcode, VarByteType);
		writeByte(0);

		String displayName = account.getAppearanceVariables().username();
		String previousName = account.getAppearanceVariables().previousUserName();

		if (displayName == null)
			displayName = account.getUsername();
		if (previousName == null)
			previousName = "";

		writeRSString(displayName);
		writeRSString(displayName);
		writeRSString(previousName);
		writeRSString(displayName);
	}
}
