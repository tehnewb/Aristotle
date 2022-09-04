package versions.ver637.model.player.clan;

import java.nio.file.Files;
import java.nio.file.Paths;

import com.framework.resource.RSResource;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ClanSaveResource implements RSResource<Clan> {

	private static final String ClanFolder = "./resources/637/clans/";

	private final Clan clan;

	@Override
	public Clan load() throws Exception {
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting();

		Gson gson = builder.create();
		String json = gson.toJson(clan);

		String path = ClanFolder + clan.getName() + ".json";
		Files.writeString(Paths.get(path), json);
		return clan;
	}

	@Override
	public void finish(Clan resource) {
		log.debug("Clan {} created with {} members", resource.getMembers().size(), clan.getName());
	}

}
