package versions.ver637.network.coders.frames;

import com.framework.network.RSFrame;

public class WindowPaneFrame extends RSFrame {

	/**
	 * The opcode of the window pane frame
	 */
	private static final short WindowPaneOpcode = 36;

	public WindowPaneFrame(int windowID, boolean redraw) {
		super(WindowPaneOpcode, StandardType);

		writeByteA(redraw ? 1 : 0);
		writeShortA(windowID);
	}
}
