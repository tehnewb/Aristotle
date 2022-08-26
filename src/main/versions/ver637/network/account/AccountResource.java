package versions.ver637.network.account;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Consumer;

import com.framework.resource.RSResource;
import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;
import versions.ver637.cache.CacheResource;

@RequiredArgsConstructor
public class AccountResource implements RSResource<AccountLoginCallback> {

	private final String username;
	private final String password;
	private final Consumer<AccountLoginCallback> consumer;

	@Override
	public AccountLoginCallback load() throws Exception {
		Gson gson = new Gson();
		String path = "./resources/" + CacheResource.Revision + "/accounts/" + username + ".json";
		File file = new File(path);
		if (file.exists()) {
			String jsonString = Files.readString(Paths.get(path));
			Account account = gson.fromJson(jsonString, Account.class);
			if (!account.getPassword().equals(password))
				return new AccountLoginCallback(account, AccountLoginResponse.InvalidCredentials);

			return new AccountLoginCallback(account, AccountLoginResponse.SuccessfulLogin);
		} else {
			Account account = new Account();
			account.setUsername(username);
			account.setPassword(password);
			Files.writeString(Paths.get(path), gson.toJson(account));
			return new AccountLoginCallback(account, AccountLoginResponse.SuccessfulLogin);
		}
	}

	@Override
	public void finish(AccountLoginCallback callback) {
		consumer.accept(callback);
	}

}
