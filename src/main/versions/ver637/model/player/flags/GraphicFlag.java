package versions.ver637.model.player.flags;

import com.framework.network.RSFrame;

import lombok.RequiredArgsConstructor;
import versions.ver637.model.UpdateFlag;

@RequiredArgsConstructor
public class GraphicFlag implements UpdateFlag {

	private final int graphicID;
	private final int height;
	private final int delay;
	private final int rotation;

	public GraphicFlag(int graphicID) {
		this(graphicID, 0, 0, 0);
	}

	public GraphicFlag(int graphicID, int height) {
		this(graphicID, height, 0, 0);
	}

	public GraphicFlag(int graphicID, int height, int delay) {
		this(graphicID, height, delay, 0);
	}

	@Override
	public int mask() {
		return 0x4000;
	}

	@Override
	public int ordinal() {
		return GraphicOrdinal;
	}

	@Override
	public void write(RSFrame block) {
		block.writeLEShortA(graphicID);
		block.writeInt2((height << 16) | delay);
		block.writeByte(rotation);
	}

}
