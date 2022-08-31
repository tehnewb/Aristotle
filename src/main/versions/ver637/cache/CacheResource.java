package versions.ver637.cache;

import com.displee.cache.CacheLibrary;
import com.framework.resource.RSResource;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CacheResource implements RSResource<CacheResource> {

	public static final int Revision = 637;

	public static final byte ANIMS = 0;
	public static final byte BASES = 1;
	public static final byte CONFIG = 2;
	public static final byte INTERFACES = 3;
	public static final byte MAPS = 5;
	public static final byte MODELS = 7;
	public static final byte SPRITES = 8;
	public static final byte BINARY = 10;
	public static final byte CLIENTSCRIPTS = 12;
	public static final byte FONTMETRICS = 13;
	public static final byte VORBIS = 14;
	public static final byte LOCALES = 16;
	public static final byte CONFIG_ENUM = 17;
	public static final byte CONFIG_NPC = 18;
	public static final byte CONFIG_OBJ = 19;
	public static final byte CONFIG_SEQ = 20;
	public static final byte CONFIG_SPOT = 21;
	public static final byte CONFIG_STRUCT = 22;
	public static final byte WORLDMAPDATA = 23;
	public static final byte QUICKCHAT = 24;
	public static final byte QUICKCHAT_GLOBAL = 25;
	public static final byte MATERIALS = 26;
	public static final byte CONFIG_PARTICLE = 27;
	public static final byte DEFAULTS = 28;
	public static final byte CONFIG_BILLBOARD = 29;
	public static final byte DLLS = 30;
	public static final byte SHADERS = 31;
	public static final byte LOADING_SPRITES = 32;
	public static final byte LOADING_SCREENS = 33;
	public static final byte LOADING_SPRITES_RAW = 34;
	public static final byte CUTSCENES = 35;

	@Getter
	private static CacheLibrary library;

	@Override
	public CacheResource load() throws Exception {
		log.info("Loading cache...");
		library = CacheLibrary.create("./resources/" + Revision + "/cache/");
		return this;
	}

	@Override
	public void finish(CacheResource cache) {
		log.info("Finished loading cache");
	}

}
