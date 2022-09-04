package versions.ver637.cache;

import java.util.HashMap;

import com.framework.io.RSStream;
import com.framework.resource.RSResource;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EnumResource implements RSResource<Integer> {

	private static final EnumData[] Enums = new EnumData[10000];

	@Override
	public Integer load() throws Exception {
		log.info("Loading enums...");
		int count = 0;
		for (int id = 0; id < Enums.length; id++) {
			byte[] archiveData = CacheResource.getLibrary().data(CacheResource.ENUMS, id >>> 8, id & 0xff);
			EnumData data = new EnumData();

			Enums[id] = data;
			if (archiveData == null)
				continue;

			RSStream buffer = new RSStream(archiveData);
			for (;;) {
				int opcode = buffer.readUnsignedByte();
				if (opcode == 0) {
					break;
				} else if (opcode == 1) {
					data.opcode1 = buffer.readByte();
				} else if (opcode == 2) {
					data.opcode2 = buffer.readByte();
				} else if (opcode == 3) {
					data.stringValue = buffer.readRSString();
				} else if (opcode == 4) {
					data.intValue = buffer.readInt();
				} else if (opcode == 5 || opcode == 6) {
					int size = buffer.readUnsignedShort();
					data.map = new HashMap<>();
					for (int i = 0; i < size; ++i) {
						int key = buffer.readInt();
						Object value = null;
						if (opcode == 5) {
							value = buffer.readRSString();
						} else {
							value = buffer.readInt();
						}
						data.map.put(key, value);
					}
				}
			}
			Enums[id] = data;
			count++;
		}
		return count;
	}

	@Override
	public void finish(Integer resource) {
		log.info("Loaded {} enums", resource);
	}

	public static EnumData getEnumData(int ID) {
		return Enums[ID];
	}

	@Data
	public class EnumData {
		private int intValue;
		private String stringValue;
		private int opcode1;
		private int opcode2;
		private HashMap<Integer, Object> map;
	}

}
