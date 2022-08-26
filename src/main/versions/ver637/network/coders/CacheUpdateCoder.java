package versions.ver637.network.coders;

import java.util.List;

import com.displee.cache.index.Index;
import com.framework.io.RSStream;
import com.framework.network.RSNetworkSession;
import com.framework.network.RSSessionCoder;

import versions.ver637.cache.CacheResource;

public class CacheUpdateCoder implements RSSessionCoder {

	@Override
	public void decode(RSNetworkSession session, RSStream in, List<Object> out) {
		while (in.readableBytes() >= 4) {
			int opcode = in.readUnsignedByte();
			int idx = in.readUnsignedByte();
			int file = in.readUnsignedShort();
			if (opcode == 0 || opcode == 1) {
				RSStream raw;
				int length, compression;

				if (idx == 255 && file == 255) {
					raw = new RSStream(CacheResource.getLibrary().generateUkeys2(true));
					compression = 0;
					length = raw.readableBytes();
				} else {
					Index index = idx == 255 ? CacheResource.getLibrary().getIndex255() : CacheResource.getLibrary().index(idx);
					raw = new RSStream(index.readArchiveSector(file).getData());
					compression = raw.readUnsignedByte();
					length = raw.readInt();
				}

				RSStream response = new RSStream(raw.readableBytes() + 8 + ((raw.readableBytes() + 8) / 512) + 4);
				response.writeByte((byte) idx);
				response.writeShort((short) file);
				response.writeByte((byte) (byte) (opcode == 0 ? compression | 0x80 : compression));
				response.writeInt(length);
				raw.limit(raw.position() + length + (compression == 0 ? 0 : 4));

				while (raw.readableBytes() > 0) {
					if (response.position() % 512 == 0)
						response.writeByte((byte) 0xFF);
					response.writeByte(raw.readByte());
				}
				out.add(response);
			}
		}
	}

	@Override
	public RSStream encode(RSNetworkSession session, RSStream in) {
		return in;
	}

}
