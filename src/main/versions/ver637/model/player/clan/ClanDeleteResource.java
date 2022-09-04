package versions.ver637.model.player.clan;

import java.nio.file.Files;
import java.nio.file.Paths;

import com.framework.resource.RSResource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ClanDeleteResource implements RSResource<String> {

	private static final String ClanFolder = "./resources/637/clans/";

	private final String clanName;

	@Override
	public String load() throws Exception {
		String path = ClanFolder + clanName + ".json";
		Files.deleteIfExists(Paths.get(path));
		return clanName;
	}

	@Override
	public void finish(String resource) {
		log.debug("Clan Deleted: {}", resource);
	}

}
