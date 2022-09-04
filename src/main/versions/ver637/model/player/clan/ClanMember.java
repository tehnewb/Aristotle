package versions.ver637.model.player.clan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@Accessors(fluent = true)
public class ClanMember {

	private String username;
	private int rank;
	private long banTime;

}
