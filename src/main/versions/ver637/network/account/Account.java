package versions.ver637.network.account;

import java.time.LocalDate;

import lombok.Data;
import versions.ver637.model.item.ItemContainer;
import versions.ver637.model.player.AppearanceVariables;
import versions.ver637.model.player.ChatVariables;
import versions.ver637.model.player.FriendVariables;
import versions.ver637.model.player.LocationVariables;
import versions.ver637.model.player.MiscVariables;
import versions.ver637.model.player.NotesVariables;
import versions.ver637.model.player.TickVariables;
import versions.ver637.model.player.clan.ClanVariables;
import versions.ver637.model.player.equipment.EquipmentVariables;
import versions.ver637.model.player.music.MusicVariables;
import versions.ver637.model.player.prayer.PrayerVariables;
import versions.ver637.model.player.skills.SkillVariables;

@Data
public class Account {

	/**
	 * Holds all variables for the appearance update flag
	 */
	private AppearanceVariables appearanceVariables = new AppearanceVariables();

	/**
	 * Holds all variables regarding the player's location
	 */
	private LocationVariables locationVariables = new LocationVariables();

	/**
	 * Holds all variables regarding ticks
	 */
	private TickVariables tickVariables = new TickVariables();

	/**
	 * Holds all variables regarding chat
	 */
	private ChatVariables chatVariables = new ChatVariables();

	/**
	 * Holds all variables regarding friends
	 */
	private FriendVariables friendVariables = new FriendVariables();

	/**
	 * Holds all variables regarding clan
	 */
	private ClanVariables clanVariables = new ClanVariables();

	/**
	 * Holds all misc variables
	 */
	private MiscVariables miscVariables = new MiscVariables();

	/**
	 * Holds all music variables
	 */
	private MusicVariables musicVariables = new MusicVariables();

	/**
	 * Holds all note variables
	 */
	private NotesVariables notesVariables = new NotesVariables();

	/**
	 * Holds all skill variables
	 */
	private SkillVariables skillVariables = new SkillVariables();

	/**
	 * Holds all equipment variables
	 */
	private EquipmentVariables equipmentVariables = new EquipmentVariables();

	/**
	 * Holds all prayer variables
	 */
	private PrayerVariables prayerVariables = new PrayerVariables();

	/**
	 * The inventory for this account
	 */
	private ItemContainer inventory = new ItemContainer(28, false);

	private String username;
	private String password;
	private String lastKnownIP = "127.0.0.1";
	private short memberDays = 30;
	private byte rank = 2;
	private byte unreadMessageCount = 0;
	private byte recoveryQuestionState = 1;
	private byte emailRegistrationState = 3;
	private String lastLoginDate = LocalDate.now().toString();

}
