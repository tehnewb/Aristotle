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
		int flags = variables.gender();

		appearanceData.writeByte(flags);
		appearanceData.writeByte(variables.titleID());
		appearanceData.writeByte(variables.skullIcon());
		appearanceData.writeByte(variables.prayerIcon());
		appearanceData.writeByte(0);
		for (int i = 0; i < 4; i++) {
			appearanceData.writeByte(0);
		}

		appearanceData.writeShort(0x100 + variables.torso());
		appearanceData.writeByte((byte) 0);
		appearanceData.writeShort(0x100 + variables.arms());
		appearanceData.writeShort(0x100 + variables.legs());
		appearanceData.writeShort(0x100 + variables.head());
		appearanceData.writeShort(0x100 + variables.hands());
		appearanceData.writeShort(0x100 + variables.feet());
		appearanceData.writeShort(0x100 + variables.beard());

		appearanceData.writeByte(variables.hairColor());
		appearanceData.writeByte(variables.torsoColor());
		appearanceData.writeByte(variables.legColor());
		appearanceData.writeByte(variables.feetColor());
		appearanceData.writeByte(variables.skinColor());

		appearanceData.writeShort(variables.renderAnimation());
		appearanceData.writeRSString("Player" + (int) (Math.random() * 100));
		appearanceData.writeByte(138);
		appearanceData.writeByte(138);
		appearanceData.writeShort(0);
		appearanceData.writeByte(0);

		appearanceData.limit(appearanceData.position());

		frame.writeByteA(appearanceData.position());
		frame.writeBytes(appearanceData.buffer());
	}

}
