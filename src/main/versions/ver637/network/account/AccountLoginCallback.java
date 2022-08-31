package versions.ver637.network.account;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@RequiredArgsConstructor
@Getter
@Accessors(fluent = true)
public class AccountLoginCallback {

	private final Account account;
	private final AccountLoginResponse response;

}
