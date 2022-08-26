package versions.ver637.map;

import java.util.ArrayList;
import java.util.HashMap;

import com.framework.map.RSLocation;
import com.framework.map.RSMap;

import lombok.Getter;
import lombok.NonNull;

public class WorldMap extends RSMap {

	@Getter
	private static final WorldMap Map = new WorldMap();

	private final HashMap<Integer, ArrayList<MapLocale>> locales = new HashMap<>();

	public void addLocale(@NonNull MapLocale locale) {
		ArrayList<MapLocale> list = getLocalesAt(locale.getLocation());
		list.add(locale);
		locales.put(locale.getLocation().get30BitsHash(), list);

		locale.applyClip();
	}

	public void removeLocale(MapLocale locale) {
		ArrayList<MapLocale> list = getLocalesAt(locale.getLocation());
		list.remove(locale);

		locale.removeClip();
	}

	public MapLocale getLocale(RSLocation location, int objectID) {
		ArrayList<MapLocale> list = getLocalesAt(location);
		return list.stream().filter(l -> l.getID() == objectID).findFirst().orElse(null);
	}

	public ArrayList<MapLocale> getLocalesAt(@NonNull RSLocation location) {
		return locales.getOrDefault(location.get30BitsHash(), new ArrayList<>());
	}

}
