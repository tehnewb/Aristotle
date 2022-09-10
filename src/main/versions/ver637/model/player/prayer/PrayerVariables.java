package versions.ver637.model.player.prayer;

import java.util.BitSet;
import java.util.stream.Stream;

import lombok.Getter;
import lombok.Setter;
import versions.ver637.cache.EnumResource;
import versions.ver637.cache.EnumResource.EnumData;
import versions.ver637.cache.StructResource;
import versions.ver637.cache.StructResource.StructData;
import versions.ver637.model.player.AppearanceVariables;
import versions.ver637.model.player.Player;
import versions.ver637.model.player.skills.SkillType;

public class PrayerVariables {

	public static final int PrayerEnumID = 2279;

	private transient BitSet activated = new BitSet(30);
	private BitSet quickPrayers = new BitSet(30);

	@Getter
	private PrayerBook book = PrayerBook.Regular;

	@Getter
	@Setter
	private int prayerPoints = 99;

	@Getter
	@Setter
	private transient boolean selectingQuickPrayers;

	@Getter
	@Setter
	private transient boolean quickPrayersActivated;

	public static void switchPrayerActivation(Player player, int slot) {
		PrayerVariables variables = player.getPrayerVariables();
		Prayer prayer = variables.getBook().getForSlot(slot);

		EnumData prayerData = EnumResource.getEnumData(PrayerEnumID);
		int structID = (int) prayerData.getMap().getOrDefault(slot, -1);
		if (structID == -1)
			return;
		StructData struct = StructResource.getStructData(structID);
		int requiredPrayerLevel = struct.getInteger(737);
		String message = struct.getString(738);

		if (player.getSkillVariables().getLevel(SkillType.Prayer) < requiredPrayerLevel) {
			player.sendMessage(message);
			return;
		}

		BitSet prayerSet = variables.selectingQuickPrayers ? variables.quickPrayers : variables.activated;
		boolean activated = prayerSet.get(slot);
		if (activated) {
			prayerSet.clear(slot);
			refreshPrayers(player);
			return;
		}

		if (variables.getPrayerPoints() == 0) {
			player.sendMessage("You have run out of prayer points.");
			return;
		}

		HeadIcon headIcon = HeadIcon.getForPrayer(prayer);
		if (headIcon != null) {
			if (headIcon != HeadIcon.ProtectSummoningIcon && headIcon != HeadIcon.DeflectSummoningIcon) {
				HeadIcon.getOverheadPrayers().stream().filter(p -> variables.getBook().isOfBook(p) && p != Prayer.DeflectSummoning && p != Prayer.ProtectFromSummoning).forEach(p -> prayerSet.clear(p.getSlot()));
				if (headIcon == HeadIcon.WrathIcon || headIcon == HeadIcon.SoulSplitIcon)
					prayerSet.clear(Prayer.DeflectSummoning.getSlot());
				else if (headIcon == HeadIcon.RedemptionIcon || headIcon == HeadIcon.RetributionIcon || headIcon == HeadIcon.SmiteIcon)
					prayerSet.clear(Prayer.ProtectFromSummoning.getSlot());
			} else {
				if (variables.getBook().equals(PrayerBook.Curses)) {
					prayerSet.clear(Prayer.SoulSplit.getSlot());
					prayerSet.clear(Prayer.Wrath.getSlot());
				} else {
					prayerSet.clear(Prayer.Smite.getSlot());
					prayerSet.clear(Prayer.Redemption.getSlot());
					prayerSet.clear(Prayer.Retribution.getSlot());
				}
			}
		}
		Stream.of(variables.getBook().getPrayers()).filter(p -> p.hasSimilarModifier(prayer)).forEach(p -> prayerSet.clear(p.getSlot()));

		if (prayer == Prayer.RapidHeal || prayer == Prayer.RapidRenewal) {
			prayerSet.clear(Prayer.RapidHeal.getSlot());
			prayerSet.clear(Prayer.RapidRenewal.getSlot());
		}
		prayerSet.set(prayer.getSlot());
		refreshPrayers(player);
	}

	public static void refreshPrayers(Player player) {
		PrayerVariables variables = player.getPrayerVariables();

		int headIcon = -1;

		for (Prayer prayer : variables.getBook().getPrayers()) {
			if (variables.isActivated(prayer)) {
				HeadIcon icon = HeadIcon.getForPrayer(prayer);
				if (icon == null)
					continue;
				if (icon == HeadIcon.ProtectSummoningIcon || icon == HeadIcon.DeflectSummoningIcon)
					continue;
				headIcon = icon.getHeadIcon();
			}
		}

		if (variables.isActivated(Prayer.ProtectFromSummoning)) {
			headIcon += 8;
		} else if (variables.isActivated(Prayer.DeflectSummoning)) {
			if (variables.isActivated(Prayer.DeflectRange)) {
				headIcon = 17;
			} else if (variables.isActivated(Prayer.DeflectMagic)) {
				headIcon = 18;
			} else if (variables.isActivated(Prayer.DeflectMelee)) {
				headIcon = 16;
			}
		}

		int activeBits = 0;
		int statOffset = 30;
		int statBits = (statOffset + 0) | ((statOffset + 0) << 6) | ((statOffset + 0) << 12) | ((statOffset + 0) << 18) | ((statOffset + 0) << 24);

		boolean selectingQuickPrayers = variables.selectingQuickPrayers;
		BitSet prayerBitSet = selectingQuickPrayers ? variables.quickPrayers : variables.activated;
		for (int i = 0; i < 30; i++) {
			if (prayerBitSet.get(i)) {
				activeBits |= variables.getBook().getForSlot(i).getVarpValue();
			}
		}

		if (variables.activated.isEmpty())
			variables.quickPrayersActivated = false;

		boolean curses = variables.getBook().equals(PrayerBook.Curses);
		player.getPane().setVarp(curses ? (selectingQuickPrayers ? 1587 : 1582) : (selectingQuickPrayers ? 1397 : 1395), activeBits);//Currently active prayers bitset
		player.getPane().setVarp(1584, curses ? 1 : 0); //1 for ancient curses, 0 for normal prayers
		player.getPane().setVarp(1583, statBits);
		player.getPane().setVarc(182, variables.quickPrayersActivated ? 1 : 0);

		player.getAppearanceVariables().setPrayerIcon((byte) headIcon);
		AppearanceVariables.updateAppearance(player);
	}

	public void deactivate(Prayer prayer) {
		BitSet prayerSet = this.selectingQuickPrayers ? this.quickPrayers : this.activated;
		prayerSet.clear(prayer.getSlot());
	}

	public void activate(Prayer prayer) {
		BitSet prayerSet = this.selectingQuickPrayers ? this.quickPrayers : this.activated;
		prayerSet.set(prayer.getSlot());
	}

	public Prayer[] getQuickPrayers() {
		Prayer[] prayers = new Prayer[this.quickPrayers.cardinality()];
		int index = 0;
		for (int i = 0; i < 30; i++) {
			if (this.quickPrayers.get(i)) {
				prayers[index++] = book.getForSlot(i);
			}
		}
		return prayers;
	}

	public Prayer[] getActivatedPrayers() {
		Prayer[] prayers = new Prayer[this.activated.cardinality()];
		int index = 0;
		for (int i = 0; i < 30; i++) {
			if (this.activated.get(i)) {
				prayers[index++] = book.getForSlot(i);
			}
		}
		return prayers;
	}

	/**
	 * Returns true if the given {@code prayer} is activated.
	 * 
	 * @param prayer the prayer to check
	 * @return true if activated; false otherwise
	 */
	public boolean isActivated(Prayer prayer) {
		if (book.isOfBook(prayer))
			return activated.get(prayer.getSlot());
		return false;
	}

}
