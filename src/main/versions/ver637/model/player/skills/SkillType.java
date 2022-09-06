package versions.ver637.model.player.skills;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SkillType {
	Attack(0, "Attack", 1969, 2, 200, 1, 10),
	Defence(1, "Defence", 1973, 32, 28, 5, 40),
	Strength(2, "Strength", 1970, 4, 11, 2, 20),
	Hitpoints(3, "Hitpoints", 1974, 64, 193, 6, 50),
	Range(4, "Range", 1971, 8, 52, 3, 30),
	Prayer(5, "Prayer", 1975, 128, 76, 7, 60),
	Magic(6, "Magic", 1972, 16, 93, 4, 33),
	Cooking(7, "Cooking", 1984, 65536, 68, 16, 641),
	Woodcutting(8, "Woodcutting", 1986, 262144, 165, 18, 660),
	Fletching(9, "Fletching", 1987, 524288, 101, 19, 665),
	Fishing(10, "Fishing", 1983, 32768, 44, 15, 120),
	Firemaking(11, "Firemaking", 1985, 131072, 172, 17, 649),
	Crafting(12, "Crafting", 1979, 2048, 84, 11, 90),
	Smithing(13, "Smithing", 1982, 16384, 179, 14, 115),
	Mining(14, "Mining", 1981, 8192, 186, 13, 110),
	Herblore(15, "Herblore", 1977, 512, 36, 9, 75),
	Agility(16, "Agility", 1976, 256, 19, 8, 65),
	Thieving(17, "Thieving", 1978, 1024, 60, 10, 80),
	Slayer(18, "Slayer", 1988, 1048576, 118, 20, 673),
	Farming(19, "Farming", 1989, 2097152, 126, 21, 681),
	Runecrafting(20, "Runecrafting", 1980, 4096, 110, 12, 100),
	Construction(21, "Construction", 1990, 8388608, 142, 22, 689),
	Hunter(22, "Hunter", 1991, 4194304, 134, 23, 698),
	Summoning(23, "Summoning", 1992, 16777216, 150, 24, 705),
	Dungeoneering(24, "Dungeoneering", 1993, 33554432, 158, 25, 715);

	@Getter
	private final int ID;
	@Getter
	private final String name;
	@Getter
	private final int targetConfigID;
	@Getter
	private final int targetConfigValue;
	@Getter
	private final int buttonID;
	@Getter
	private final int skillGuideConfigValue;
	@Getter
	private final int levelUpConfigValue;

	public static SkillType forButtonID(int buttonID) {
		for (SkillType type : values()) {
			if (type.buttonID == buttonID) {
				return type;
			}
		}
		return null;
	}

	public static SkillType forID(int skillID) {
		for (SkillType type : values()) {
			if (type.ID == skillID) {
				return type;
			}
		}
		return null;
	}

}
