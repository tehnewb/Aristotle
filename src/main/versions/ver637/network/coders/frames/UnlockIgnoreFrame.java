package versions.ver637.network.coders.frames;

import com.framework.network.RSFrame;

import versions.ver637.network.account.Account;

public class UnlockIgnoreFrame extends RSFrame {

	public static final int UnlockIgnoreOpcode = 37;

	public UnlockIgnoreFrame(Account... ignores) {
		super(UnlockIgnoreOpcode, VarShortType);

		writeByte(ignores.length);

		for (int i = 0; i < ignores.length; i++) {
			Account account = ignores[i];
			String displayName = account.getAppearanceVariables().getUsername();
			String previousName = account.getAppearanceVariables().getPreviousUserName();

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
}
