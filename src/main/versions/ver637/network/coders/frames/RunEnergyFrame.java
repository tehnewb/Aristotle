package versions.ver637.network.coders.frames;

import com.framework.network.RSFrame;

public class RunEnergyFrame extends RSFrame {

	public static final int RunEnergyOpcode = 13;

	public RunEnergyFrame(int value) {
		super(RunEnergyOpcode, StandardType);
		writeByte(value);
	}
}
