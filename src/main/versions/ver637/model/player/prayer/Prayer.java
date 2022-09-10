package versions.ver637.model.player.prayer;

import static versions.ver637.model.player.skills.SkillModifier.percentage;
import static versions.ver637.model.player.skills.SkillType.Attack;
import static versions.ver637.model.player.skills.SkillType.Defence;
import static versions.ver637.model.player.skills.SkillType.Magic;
import static versions.ver637.model.player.skills.SkillType.Range;
import static versions.ver637.model.player.skills.SkillType.Strength;

import java.util.stream.Stream;

import lombok.Getter;
import versions.ver637.model.player.skills.SkillModifier;

@Getter
public enum Prayer {
	//Normal Prayers
	ThickSkin(0, 1, 3, percentage(Defence, 5.0)),
	BurstOfStrength(1, 2, 3, percentage(Strength, 5.0)),
	ClarityOfThought(2, 4, 3, percentage(Attack, 5.0)),
	SharpEye(3, 262144, 3, percentage(Range, 5.0)),
	MysticWill(4, 524288, 3, percentage(Magic, 5.0)),
	RockSkin(5, 8, 6, percentage(Defence, 10.0)),
	SuperhumanStrength(6, 16, 6, percentage(Strength, 10.0)),
	ImprovedReflexes(7, 32, 6, percentage(Attack, 10.0)),
	RapidRestore(8, 64, 1),
	RapidHeal(9, 128, 2),
	ProtectItem(10, 256, 2),
	HawkEye(11, 1048576, 6, percentage(Range, 10.0)),
	MysticLore(12, 2097152, 6, percentage(Magic, 10.0)),
	SteelSkin(13, 512, 12, percentage(Defence, 15.0)),
	UltimateStrength(14, 1024, 12, percentage(Strength, 15.0)),
	IncredibleReflexes(15, 2048, 12, percentage(Attack, 15.0)),
	ProtectFromSummoning(16, 16777216, 12),
	ProtectFromMagic(17, 4096, 12),
	ProtectFromRange(18, 8192, 12),
	ProtectFromMelee(19, 16384, 12),
	EagleEye(20, 4194304, 12, percentage(Range, 15.0)),
	MysticMight(21, 8388608, 12, percentage(Magic, 15.0)),
	Retribution(22, 32768, 3),
	Redemption(23, 65536, 6),
	Smite(24, 131072, 18),
	Chivalry(25, 33554432, 24, percentage(Attack, 15.0), percentage(Strength, 18.0), percentage(Defence, 20.0)),
	RapidRenewal(26, 134217728, 2),
	Piety(27, 67108864, 24, percentage(Attack, 20.0), percentage(Strength, 23.0), percentage(Defence, 25.0)),
	Rigour(28, 536870912, 24, percentage(Range, 20.0), percentage(Defence, 25.0)),
	Augury(29, 268435456, 24, percentage(Magic, 20.0), percentage(Defence, 25.0)),

	//Curses
	CurseProtectItem(0, 1, 4),
	SapWarrior(1, 2, 12),
	SapRanger(2, 4, 12),
	SapMage(3, 8, 12),
	SapSpirit(4, 16, 12),
	Berserker(5, 32, 12),
	DeflectSummoning(6, 64, 12),
	DeflectMagic(7, 128, 12),
	DeflectRange(8, 256, 12),
	DeflectMelee(9, 512, 12),
	LeechAttack(10, 1024, 18, percentage(Attack, 5.0)),
	LeechRange(11, 2048, 18, percentage(Range, 5.0)),
	LeechMagic(12, 4096, 18, percentage(Magic, 5.0)),
	LeechDefence(13, 8192, 18, percentage(Defence, 5.0)),
	LeechStrength(14, 16384, 18, percentage(Strength, 5.0)),
	LeechEnergy(15, 32768, 18),
	LeechSpecialAttack(16, 65536, 18),
	Wrath(17, 131072, 24),
	SoulSplit(18, 262144, 24),
	Turmoil(19, 524288, 24, percentage(Attack, 15.0), percentage(Strength, 23.0), percentage(Defence, 15.0));

	private final int slot;
	private final int varpValue;
	private final int drainEffect;
	private SkillModifier[] modifiers;

	private Prayer(int slot, int varpValue, int drainEffect, SkillModifier... modifiers) {
		this.slot = slot;
		this.varpValue = varpValue;
		this.drainEffect = drainEffect;
		this.modifiers = modifiers;
	}

	public boolean hasSimilarModifier(Prayer prayer) {
		return Stream.of(modifiers).anyMatch(mod -> Stream.of(prayer.modifiers).anyMatch(m -> m.type() == mod.type()));
	}

}
