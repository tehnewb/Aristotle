package versions.ver637.cache;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.framework.io.RSStream;
import com.framework.resource.RSResource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StructResource implements RSResource<Integer> {

	private static final StructData[] Data = new StructData[2000];
	private static final byte StructArchiveID = 26;

	@Override
	public Integer load() throws Exception {
		log.info("Loading structs...");
		int count = 0;
		for (int structID = 0; structID < Data.length; structID++) {
			byte[] archiveData = CacheResource.getLibrary().data(CacheResource.CONFIG, StructArchiveID, structID);
			if (archiveData == null)
				continue;
			RSStream stream = new RSStream(archiveData);
			StructData data = new StructData(structID, new HashMap<>());
			Data[structID] = data;
			for (;;) {
				int opcode = stream.readUnsignedByte();
				if (opcode == 0) {
					break;
				} else if (opcode == 249) {
					int size = stream.readUnsignedByte();
					for (int j = 0; j < size; j++) {
						int type = stream.readUnsignedByte();
						int key = stream.readMedium();
						if (type == 1) {
							data.map.put(key, stream.readRSString());
						} else {
							data.map.put(key, stream.readInt());
						}
					}
				}
			}
			count++;
		}
		return count;
	}

	@Override
	public void finish(Integer resource) {
		log.info("Finished loading {} structs", resource);
	}

	public static StructData getStructData(int ID) {
		if (ID < 0 || ID > Data.length)
			return new StructData(ID, Collections.emptyMap());
		return Data[ID];
	}

	public static record StructData(int ID, Map<Integer, Object> map) {
		public Integer getInteger(int key) {
			return (Integer) map.get(key);
		}

		public String getString(int key) {
			return (String) map.get(key);
		}
	}

}
