package versions.ver637.network.coders.frames;

import com.framework.network.RSFrame;

public class MusicFrame extends RSFrame {

	public static final int MusicOpcode = 73;

	public MusicFrame(int musicID) {
		super(MusicOpcode, StandardType);
		writeByteC(255);
		writeByteC(100);
		writeLEShort(musicID);
	}

}
