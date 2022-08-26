package versions.ver637.network.account;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AccountLoginResponse {
	NoAccountFound(0),
	InvalidCredentials(3),
	ServerMaintenance(4),
	StillLoggedIn(5),
	SuccessfulLogin(2);

	@Getter
	private final int code;
}