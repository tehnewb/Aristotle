package versions.ver637.network.coders;

import java.util.ArrayList;
import java.util.List;

import com.framework.io.IsaacCipher;
import com.framework.io.RSStream;
import com.framework.network.RSFrame;
import com.framework.network.RSNetworkSession;
import com.framework.network.RSSessionCoder;
import com.framework.util.ReflectUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class GameCoder implements RSSessionCoder {

	private static final int[] PacketSizes = { 8, -1, -1, 16, 6, 2, 8, 6, 3, -1, 16, 15, 0, 8, 11, 8, -1, -1, 3, 2, -3, -1, 7, 2, -1, 7, -1, 3, 3, 6, 4, 3, 0, 3, 4, 5, -1, -1, 7, 8, 4, -1, 4, 7, 3, 15, 8, 3, 2, 4, 18, -1, 1, 3, 7, 7, 4, -1, 8, 2, 7, -1, 1, -1, 3, 2, -1, 8, 3, 2, 3, 7, 3, 8, -1, 0, 7, -1, 11, -1, 3, 7, 8, 12, 4, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3, -3 };
	private static final RSFrameHandler[] FrameHandlers = new RSFrameHandler[256];

	static {
		ArrayList<Class<?>> classes = ReflectUtil.getClassesInPackage("versions.ver637.network.coders.handlers");
		for (Class<?> clazz : classes) {
			if (RSFrameHandler.class.isAssignableFrom(clazz)) {
				try {
					RSFrameHandler handler = (RSFrameHandler) clazz.getDeclaredConstructor().newInstance();
					for (int opcode : handler.opcodesHandled()) {
						FrameHandlers[opcode] = handler;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private final IsaacCipher inCipher;
	private final IsaacCipher outCipher;

	@Override
	public void decode(RSNetworkSession session, RSStream in, List<Object> out) {
		if (in.readableBytes() < 1)
			return;
		int opcode = in.readUnsignedByte();

		if (inCipher != null)
			opcode -= this.inCipher.nextValue() & 0xFF;

		if (opcode < 0) {
			in.clear();
			return;
		}

		var length = Math.min(switch (PacketSizes[opcode]) {
			case -1 -> in.readUnsignedByte();
			case -2 -> in.readUnsignedShort();
			case -3 -> in.readInt();
			default -> PacketSizes[opcode];
		}, in.readableBytes());

		byte[] payload = in.readBytes(length);
		RSFrame frame = new RSFrame(opcode, payload, RSFrame.StandardType);
		RSFrameHandler handler = FrameHandlers[frame.opcode()];
		if (handler != null) {
			handler.handleFrame(session, frame);
		} else {
			log.info("Unhandled Packet[opcode={}, length={}]", opcode, length);
		}
	}

	@Override
	public RSStream encode(RSNetworkSession session, RSStream in) {
		if (RSFrame.class.isAssignableFrom(in.getClass())) {
			RSFrame frame = RSFrame.class.cast(in);
			if (frame.isRaw()) {
				frame.limit(frame.position());
				return frame;
			}

			int opcode = frame.opcode();
			if (outCipher != null)
				opcode += this.outCipher.nextValue() & 0xFF;

			RSStream response = new RSStream();

			response.writeByte(opcode);

			if (frame.header() == RSFrame.VarByteType) {
				response.writeByte(frame.position());
			} else if (frame.header() == RSFrame.VarShortType) {
				response.writeShort(frame.position());
			}
			frame.limit(frame.position());
			response.writeBytes(frame.buffer());
			return response;
		}
		in.limit(in.position());
		return in;
	}

}
