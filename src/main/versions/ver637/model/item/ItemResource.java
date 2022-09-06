package versions.ver637.model.item;

import java.util.HashMap;
import java.util.Map;

import com.framework.io.RSStream;
import com.framework.resource.RSResource;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import versions.ver637.cache.CacheResource;

@Slf4j
public class ItemResource implements RSResource<Integer> {

	private static final ItemData[] Data = new ItemData[20430];

	@Override
	public Integer load() throws Exception {
		log.info("Loading items...");
		int count = 0;
		for (int itemID = 0; itemID < Data.length; itemID++) {
			byte[] archiveData = CacheResource.getLibrary().data(CacheResource.ITEMS, itemID >>> 8, itemID & 0xff);

			ItemData data = Data[itemID] = new ItemData();

			if (archiveData == null)
				continue;

			RSStream buffer = new RSStream(archiveData);
			for (;;) {
				int opcode = buffer.readUnsignedByte();
				if (opcode == 0)
					break;
				if (opcode == 4) {
					buffer.readUnsignedShort();
				} else if (opcode == 5) {
					buffer.readUnsignedShort();
				} else if (opcode == 6) {
					buffer.readUnsignedShort();
				} else if (opcode == 11) {
					data.stackable = true;
				} else if (opcode == 12) {
					data.value = buffer.readInt();
				} else if (opcode == 23) {
					buffer.readUnsignedShort();
				} else if (opcode == 25) {
					buffer.readUnsignedShort();
				} else if (opcode == 26) {
					buffer.readUnsignedShort();
					break;
				} else if (opcode >= 35 && opcode <= 39) {
					data.inventoryOptions[opcode - 35] = buffer.readRSString();
				} else if (opcode == 40) {
					int length = buffer.readUnsignedByte();
					for (int index = 0; index < length; index++) {
						buffer.readUnsignedShort();
						buffer.readUnsignedShort();
					}
				} else if (opcode == 41) {
					int length = buffer.readUnsignedByte();
					for (int index = 0; index < length; index++) {
						buffer.readUnsignedShort();
						buffer.readUnsignedShort();
					}
				} else if (opcode == 42) {
					int length = buffer.readUnsignedByte();
					for (int index = 0; index < length; index++)
						buffer.readByte();
				} else if (opcode == 65) {
					data.unnoted = true;
				} else if (opcode == 78) {
					buffer.readUnsignedShort();
				} else if (opcode == 79) {
					buffer.readUnsignedShort();
				} else if (opcode == 91) {
					buffer.readShort();
				} else if (opcode == 98) {
					buffer.readUnsignedShort();
				} else if (opcode == 101) {
					buffer.readShort();
				} else if (opcode == 111) {
					buffer.readShort();
				} else if (opcode == 115) {
					buffer.readUnsignedByte();
				} else if (opcode == 122) {
					data.lendTemplate = buffer.readUnsignedShort();
				} else if (opcode == 130) {
					buffer.readByte();
					buffer.readShort();
				} else if (opcode == 139) {
					buffer.readShort();
				} else if (opcode == 249) {
					int length = buffer.readUnsignedByte();
					data.attributes = new HashMap<Integer, Object>();
					for (int index = 0; index < length; index++) {
						boolean stringInstance = buffer.readByte() == 1;
						int key = buffer.readMedium();
						Object value = stringInstance ? buffer.readRSString() : buffer.readInt();
						data.attributes.put(key, value);
					}
				} else if (opcode == 140) {
					buffer.readShort();
				} else if (opcode == 134) {
					buffer.readByte();
				} else if (opcode == 132) {
					int length = buffer.readUnsignedByte();
					for (int index = 0; index < length; index++)
						buffer.readUnsignedShort();
				} else if (opcode == 129 || opcode == 128 || opcode == 127) {
					buffer.readByte();
					buffer.readShort();
				} else if (opcode == 125 || opcode == 126) {
					buffer.readByte();
					buffer.readByte();
					buffer.readByte();
				} else if (opcode == 121) {
					data.lendID = buffer.readUnsignedShort();
				} else if (opcode == 114) {
					buffer.readByte();
				} else if (opcode == 113) {
					buffer.readByte();
				} else if (opcode == 112) {
					buffer.readShort();
				} else if (opcode >= 100 && opcode <= 109) {
					buffer.readUnsignedShort();
					buffer.readUnsignedShort();
				} else if (opcode == 96) {
					buffer.readByte();
				} else if (opcode == 97) {
					buffer.readShort();
				} else if (opcode == 90 || opcode == 92 || opcode == 93 || opcode == 95) {
					buffer.readShort();
				} else if (opcode >= 30 && opcode <= 34) {
					data.groundOptions[opcode - 30] = buffer.readRSString();
				} else if (opcode == 24) {
					buffer.readShort();
				} else if (opcode == 18) {
					buffer.readShort();
				} else if (opcode == 16) {
					data.members = true;
				} else if (opcode == 8) {
					buffer.readUnsignedShort();
				} else if (opcode == 7) {
					buffer.readUnsignedShort();
				} else if (opcode == 2) {
					data.name = buffer.readRSString();
				} else if (opcode == 1) {
					buffer.readUnsignedShort();
				}
			}
			count++;

		}
		return count;

	}

	@Override
	public void finish(Integer resource) {
		log.info("Loaded {} items", resource);
	}

	public static ItemData getItemData(int itemID) {
		return Data[itemID];
	}

	@Data
	public class ItemData {
		private String name = "null";
		private int value = 1;
		private int lendID = -1;
		private int lendTemplate = -1;
		private boolean stackable;
		private boolean members;
		public boolean unnoted;
		private String[] groundOptions = { null, null, "take", null, null };
		private String[] inventoryOptions = { null, null, null, null, "drop" };
		private Map<Integer, Object> attributes;
	}
}
