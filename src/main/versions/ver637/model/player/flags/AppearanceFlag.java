package versions.ver637.model.player.flags;

import com.framework.io.RSStream;
import com.framework.network.RSFrame;

import lombok.RequiredArgsConstructor;
import versions.ver637.model.UpdateFlag;
import versions.ver637.model.player.AppearanceVariables;

@RequiredArgsConstructor
public class AppearanceFlag implements UpdateFlag {

	private final AppearanceVariables variables;

	@Override
	public int mask() {
		return 0x40;
	}

	@Override
	public int ordinal() {
		return UpdateFlag.AppearanceOrdinal;
	}

	@Override
	public void write(RSFrame frame) {
		RSStream appearanceData = new RSStream();
		int dataHash = (variables.getGender() & 0x1) | 0x4;

		appearanceData.writeByte(dataHash);
		appearanceData.writeByte(variables.getTitleID());
		appearanceData.writeByte(variables.getSkullIcon());
		appearanceData.writeByte(variables.getPrayerIcon());
		appearanceData.writeByte(0);

		int[] flags = variables.getFlags();
		for (int i = 0; i < flags.length; i++) {
			if (flags[i] == 0) {
				appearanceData.writeByte(0);
			} else {
				appearanceData.writeShort(flags[i]);
			}
		}

		appearanceData.writeByte(variables.getHairColor());
		appearanceData.writeByte(variables.getTorsoColor());
		appearanceData.writeByte(variables.getLegColor());
		appearanceData.writeByte(variables.getFeetColor());
		appearanceData.writeByte(variables.getSkinColor());

		appearanceData.writeShort(variables.getRenderAnimation());
		appearanceData.writeRSString(variables.getUsername());
		appearanceData.writeByte(138);
		appearanceData.writeByte(0); // skill level, eg -> skill: 1526
		appearanceData.writeShort(0);
		appearanceData.writeByte(0);

		appearanceData.limit(appearanceData.position());

		frame.writeByteA(appearanceData.position());
		frame.writeBytes(appearanceData.buffer());
	}

}
