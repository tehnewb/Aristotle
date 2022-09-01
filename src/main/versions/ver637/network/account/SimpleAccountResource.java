package versions.ver637.network.account;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.framework.resource.RSResource;
import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;
import versions.ver637.cache.CacheResource;

@RequiredArgsConstructor
public class SimpleAccountResource implements RSResource<Account> {

	private final String username;

	@Override
	public Account load() throws Exception {
		Gson gson = new Gson();
		String path = "./resources/" + CacheResource.Revision + "/accounts/" + username + ".json";
		File file = new File(path);
		if (file.exists()) {
			String jsonString = Files.readString(Paths.get(path));
			return gson.fromJson(jsonString, Account.class);
		}
		return null;
	}

	@Override
	public void finish(Account resource) {

	}

}
