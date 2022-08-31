package versions.ver637.map;

import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Stream;

import com.framework.map.RSLocation;
import com.framework.map.RSMap;

import lombok.Getter;
import lombok.NonNull;

public class WorldMap extends RSMap {

	@Getter
	private static final WorldMap Map = new WorldMap();

	private final HashMap<Integer, Locale> locales = new HashMap<>();

	/**
	 * Adds the given {@code locale} to this {@code WorldMap}.
	 * 
	 * @param locale the locale to add
	 */
	public void addLocale(@NonNull Locale locale) {
		if (Stream.of(locale.getData().getOptions()).anyMatch(Objects::nonNull)) {
			locales.put(locale.getLocation().packTo30Bits(), locale);
		}
		locale.applyClip();
	}

	/**
	 * Removes the locale at the given {@code locale}.
	 * 
	 * @param location the location of the locale
	 */
	public void removeLocale(RSLocation location) {
		Locale locale = locales.remove(location.packTo30Bits());

		locale.removeClip();
	}

	/**
	 * Returns the location at the given {@code location}. If the locale doesn't
	 * exist, null is returned;
	 * 
	 * @param location the location of the locale
	 * @return the locale; possibly null
	 */
	public Locale getLocale(RSLocation location) {
		return locales.get(location.packTo30Bits());
	}

}
