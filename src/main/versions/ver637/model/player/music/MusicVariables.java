package versions.ver637.model.player.music;

import lombok.Getter;
import lombok.Setter;
import versions.ver637.cache.EnumResource;
import versions.ver637.model.player.Player;
import versions.ver637.network.coders.frames.MusicFrame;

public class MusicVariables {

	public static final int[] MusicVarps = { 20, 21, 22, 23, 24, 25, 298, 311, 346, 414, 464, 598, 662, 721, 906, 1009, 1104, 1136, 1180, 1202, 1381, 1394, 1434, 1596, 1618, 1619, 1620, 1864, 1865 };

	@Setter
	@Getter
	private transient int currentTrackPlaying;

	@Getter
	@Setter
	private transient boolean personallyPlaying;

	private boolean[] unlocked = new boolean[1000];

	public MusicVariables() {
		unlocked[62] = true;
		unlocked[400] = true;
		unlocked[321] = true;
		unlocked[547] = true;
		unlocked[16] = true;
		unlocked[466] = true;
		unlocked[621] = true;
		unlocked[207] = true;
		unlocked[444] = true;
		unlocked[443] = true;
		unlocked[445] = true;
		unlocked[441] = true;
		unlocked[401] = true;
		unlocked[552] = true;
		unlocked[457] = true;
		unlocked[679] = true;
		unlocked[859] = true;
	}

	public void setTrackUnlocked(int musicIndex) {
		this.unlocked[musicIndex] = true;
	}

	public boolean isTrackUnlocked(int musicIndex) {
		return this.unlocked[musicIndex];
	}

	public static void initializeUnlocks(Player player) {
		MusicVariables variables = player.getMusicVariables();
		for (int varp : MusicVarps)
			player.getPane().setVarp(varp, 0);

		for (int musicIndex = 0; musicIndex < 1000; musicIndex++) {
			if (variables.isTrackUnlocked(musicIndex))
				unlockMusic(player, musicIndex);
		}
	}

	public static void unlockMusic(Player player, int musicIndex) {
		MusicVariables variables = player.getMusicVariables();
		variables.setTrackUnlocked(musicIndex);

		int varpIndex = MusicVarps[musicIndex / 32];
		int varpValue = player.getPane().getVarp(varpIndex);
		int musicFlag = 1 << musicIndex % 32;

		player.getPane().setVarp(varpIndex, varpValue | musicFlag);
	}

	public static void playMusic(Player player, int musicIndex) {
		MusicVariables variables = player.getMusicVariables();
		if (variables.getCurrentTrackPlaying() == musicIndex)
			return;
		if (variables.isTrackUnlocked(musicIndex)) {
			int musicID = (int) EnumResource.getEnumData(1351).getMap().get(musicIndex);
			player.getSession().write(new MusicFrame(musicID));

			variables.setCurrentTrackPlaying(musicIndex);
		}
	}

}
