package versions.ver637.model.player.prayer;

import static versions.ver637.model.player.prayer.Prayer.*;

import java.util.stream.Stream;

import lombok.Getter;

public enum PrayerBook {
	Regular(ThickSkin, BurstOfStrength, ClarityOfThought, SharpEye, MysticWill, RockSkin, SuperhumanStrength, ImprovedReflexes, RapidRestore, RapidHeal, ProtectItem, HawkEye, MysticLore, SteelSkin, UltimateStrength, IncredibleReflexes, ProtectFromSummoning, ProtectFromMagic, ProtectFromRange, ProtectFromMelee, EagleEye, MysticMight, Retribution, Redemption, Smite, Chivalry, RapidRenewal, Piety, Rigour, Augury),
	Curses(CurseProtectItem, SapWarrior, SapRanger, SapMage, SapSpirit, Berserker, DeflectSummoning, DeflectMagic, DeflectRange, DeflectMelee, LeechAttack, LeechRange, LeechMagic, LeechDefence, LeechStrength, LeechEnergy, LeechSpecialAttack, Wrath, SoulSplit, Turmoil);

	@Getter
	private Prayer[] prayers;

	private PrayerBook(Prayer... prayers) {
		this.prayers = prayers;
	}

	public Prayer getForSlot(int slot) {
		return Stream.of(prayers).filter(p -> p.getSlot() == slot).findFirst().orElse(null);
	}

	public boolean isOfBook(Prayer prayer) {
		return Stream.of(prayers).anyMatch(p -> p.equals(prayer));
	}

}
