package map;

import com.framework.map.RSChunk;
import com.framework.map.RSDirection;
import com.framework.map.RSMap;
import com.framework.map.RSRegion;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestMapApplication {

	public static void main(String[] args) {
		RSMap map = new RSMap();
		RSRegion lumbridge = new RSRegion(12850);
		lumbridge.setChunk(new RSChunk(402, 402, 0));
		map.setRegion(lumbridge);

		map.setNPCFlag(3222, 3222, 0, true);
		map.addFlags(3222, 3223, 0, 1000);
		map.addFlags(3222, 3223, 0, 2000);
		map.addFlags(3225, 3225, 0, 10);

		log.info("NPC UpdateFlag set to {} ", map.hasNPCFlag(3222, 3222, 0));
		log.info("Flags set to {} ", map.getFlags(3222, 3223, 0));
		for (int dx = -1; dx <= 1; dx++) {
			for (int dy = -1; dy <= 1; dy++) {
				log.info("Direction found for dx={} dy={} is {}", dx, dy, RSDirection.of(dx, dy));
			}
		}
	}
}
