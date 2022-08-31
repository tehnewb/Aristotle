package versions.ver637.network.account;

import java.nio.file.Files;
import java.nio.file.Paths;

import com.framework.resource.RSResource;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.RequiredArgsConstructor;
import versions.ver637.cache.CacheResource;

@RequiredArgsConstructor
public class AccountSaveResource implements RSResource<Account> {

	private final Account account;

	@Override
	public Account load() throws Exception {
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting();
		Gson gson = builder.create();
		String json = gson.toJson(account);
		String path = "./resources/" + CacheResource.Revision + "/accounts/" + account.getUsername() + ".json";
		Files.writeString(Paths.get(path), json);
		return account;
	}

	@Override
	public void finish(Account account) {

	}

}
