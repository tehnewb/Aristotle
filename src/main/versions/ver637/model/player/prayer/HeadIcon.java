package versions.ver637.model.player.prayer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Stream;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum HeadIcon {
	ProtectSummoningIcon(Prayer.ProtectFromSummoning, 7),
	ProtectMagicIcon(Prayer.ProtectFromMagic, 2),
	ProtectRangeIcon(Prayer.ProtectFromRange, 1),
	ProtectMeleeIcon(Prayer.ProtectFromMelee, 0),
	DeflectSummoningIcon(Prayer.DeflectSummoning, 15),
	DeflectMagicIcon(Prayer.DeflectMagic, 13),
	DeflectRangeIcon(Prayer.DeflectRange, 14),
	DeflectMeleeIcon(Prayer.DeflectMelee, 12),
	WrathIcon(Prayer.Wrath, 19),
	SoulSplitIcon(Prayer.SoulSplit, 20),
	RetributionIcon(Prayer.Retribution, 3),
	RedemptionIcon(Prayer.Redemption, 5),
	SmiteIcon(Prayer.Smite, 4);

	private static ArrayList<Prayer> OverheadPrayers = new ArrayList<>();

	static {
		Stream.of(values()).forEach(i -> OverheadPrayers.add(i.getPrayer()));
	}

	@Getter
	private final Prayer prayer;
	@Getter
	private final int headIcon;

	public static HeadIcon getForPrayer(Prayer prayer) {
		return Stream.of(values()).filter(i -> i.getPrayer().equals(prayer)).findFirst().orElse(null);
	}

	public static Collection<Prayer> getOverheadPrayers() {
		return Collections.unmodifiableCollection(OverheadPrayers);
	}
}
