package versions.ver637;

import com.framework.RSFramework;

import versions.ver637.cache.CacheResource;
import versions.ver637.cache.EnumResource;
import versions.ver637.cache.XTEAResource;
import versions.ver637.map.GameObjectResource;
import versions.ver637.model.GameProcessTick;
import versions.ver637.model.player.clan.ClanLoadResource;
import versions.ver637.model.player.music.MusicResource;
import versions.ver637.network.coders.HandshakeCoder;
import versions.ver637.pane.InterfaceResource;
import versions.ver637.plugin.PluginResource;

/**
 * Initializes the network, resources, and other things necessary for the 637
 * 
 * @author Albert Beaupre
 */
public final class Initializer637 {

	private Initializer637() {
		// Inaccessible
	}

	public static void main(String[] args) throws Exception {
		RSFramework.run(Initializer637.class);
		RSFramework.bind(43594, new HandshakeCoder());
		RSFramework.queueResource(new CacheResource());
		RSFramework.queueResource(new XTEAResource());
		RSFramework.queueResource(new GameObjectResource());
		RSFramework.queueResource(new InterfaceResource());
		RSFramework.queueResource(new EnumResource());
		RSFramework.queueResource(new ClanLoadResource());
		RSFramework.queueResource(new PluginResource());
		RSFramework.queueResource(new MusicResource());
		RSFramework.addTick(new GameProcessTick());
	}
}
