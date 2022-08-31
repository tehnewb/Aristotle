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

	private final HashMap<Integer, GameObject> locales = new HashMap<>();

	/**
	 * Adds the given {@code object} to this {@code WorldMap}.
	 * 
	 * @param object the object to add
	 */
	public void addGameObject(@NonNull GameObject object) {
		// Only add if there are options. 99% of the time there is only one object per tile with clickable options
		if (Stream.of(object.getData().getOptions()).anyMatch(Objects::nonNull)) {
			locales.put(object.getLocation().packTo30Bits(), object);
		}
		object.applyClip();
	}

	/**
	 * Removes the given {@code object} from this {@code WorldMap}.
	 * 
	 * @param object the object to remove
	 */
	public void removeGameObject(@NonNull GameObject object) {
		locales.remove(object.getLocation().packTo30Bits());

		object.removeClip();
	}

	/**
	 * Returns the {@code GameObject} at the given {@code location}. If the object
	 * doesn't exist, null is returned;
	 * 
	 * @param location the location of the object
	 * @return the object; possibly null
	 */
	public GameObject getGameObject(RSLocation location) {
		return locales.get(location.packTo30Bits());
	}

}
