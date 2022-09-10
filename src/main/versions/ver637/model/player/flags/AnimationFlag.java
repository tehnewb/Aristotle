package versions.ver637.model.player.flags;

import com.framework.network.RSFrame;

import lombok.RequiredArgsConstructor;
import versions.ver637.model.UpdateFlag;

@RequiredArgsConstructor
public class AnimationFlag implements UpdateFlag {

	private final int animationID;
	private final int delay;

	public AnimationFlag(int animationID) {
		this(animationID, 0);
	}

	@Override
	public int mask() {
		return 0x10;
	}

	@Override
	public int ordinal() {
		return AnimateOrdinal;
	}

	@Override
	public void write(RSFrame block) {
		block.writeLEShortA(animationID);
		block.writeLEShortA(animationID);
		block.writeLEShortA(animationID);
		block.writeLEShortA(animationID);
		block.writeByteC(delay);
	}

}
