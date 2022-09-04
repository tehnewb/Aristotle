package versions.ver637.model.player.clan;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.framework.resource.RSResource;
import com.google.gson.Gson;

public class ClanLoadResource implements RSResource<Void> {

	private static final String ClanFolder = "./resources/637/clans/";

	@Override
	public Void load() throws Exception {
		File[] folder = new File(ClanFolder).listFiles();
		Gson gson = new Gson();
		for (File file : folder) {
			String jsonString = Files.readString(Paths.get(file.getPath()));
			Clan clan = gson.fromJson(jsonString, Clan.class);
			Clan.addClan(clan);
		}
		return null;
	}

	@Override
	public void finish(Void resource) {

	}

}
