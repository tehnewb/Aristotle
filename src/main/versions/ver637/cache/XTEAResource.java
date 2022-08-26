package versions.ver637.cache;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import com.framework.io.RSStream;
import com.framework.io.XTEAKey;
import com.framework.resource.RSResource;

import lombok.extern.slf4j.Slf4j;

/**
 * The {@code MapResource} loads all maps that have an xtea key assigned to
 * them, stores the clips and collision flags to their corresponding chunks.
 * This resource must be loaded only after the cache resource has been loaded
 * due to it relying on the cache's landscape/object data.
 * 
 * @author Albert Beaupre
 */
@Slf4j
public class XTEAResource implements RSResource<Integer> {

	private static final HashMap<Integer, XTEAKey> XTEAS = new HashMap<>();

	@Override
	public Integer load() throws Exception {
		log.info("Loading xteas...");
		RSStream stream = new RSStream(Files.readAllBytes(Paths.get("./resources/" + CacheResource.Revision + "/xteas.bin")));

		while (stream.readableBytes() > 0) {
			int ID = stream.readUnsignedShort();
			XTEAKey key = new XTEAKey(stream.readIntArray(4));
			XTEAS.put(ID, key);
		}
		return XTEAS.size();
	}

	@Override
	public void finish(Integer size) {
		log.info("Finished loading {} xteas", size);
	}

	public static XTEAKey getKey(int regionID) {
		return XTEAS.getOrDefault(regionID, XTEAKey.EmptyKey);
	}

}
