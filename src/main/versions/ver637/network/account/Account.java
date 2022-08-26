package versions.ver637.network.account;

import lombok.Data;

@Data
public class Account {

	private String username;
	private String password;
	private int rank = 2;

}
