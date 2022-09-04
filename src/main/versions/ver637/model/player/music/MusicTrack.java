package versions.ver637.model.player.music;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
public class MusicTrack {
	private String name;
	private int index;
	private int[] regionIDs;

}
