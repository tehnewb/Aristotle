package versions.ver637;

import com.framework.RSFramework;

import versions.ver637.cache.CacheResource;
import versions.ver637.cache.XTEAResource;
import versions.ver637.network.coders.HandshakeCoder;
import versions.ver637.plugin.PluginResource;

public class Application {

	public static void main(String[] args) throws Exception {
		RSFramework.run(Application.class);
		RSFramework.queueResource(new CacheResource());
		RSFramework.queueResource(new XTEAResource());
		RSFramework.queueResource(new PluginResource());
		RSFramework.bind(43594, new HandshakeCoder());
	}

}
