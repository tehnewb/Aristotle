package versions.ver637.plugin;

import java.io.File;
import java.util.stream.Stream;

import com.framework.RSFramework;
import com.framework.resource.RSResource;

import lombok.extern.slf4j.Slf4j;
import versions.ver637.cache.CacheResource;

@Slf4j
public class PluginResource implements RSResource<Boolean> {

	@Override
	public Boolean load() throws Exception {
		log.info("Loading plugins...");
		File[] folder = new File("./resources/" + CacheResource.Revision + "/plugins/").listFiles();
		Stream.of(folder).filter(f -> f.getName().endsWith(".jar")).forEach(file -> {
			if (RSFramework.loadPlugin(file)) {
				log.info("\tLoaded plugin from file {}", file.getName());
			} else {
				log.error("\tError loading plugin {}", file.getName());
			}
		});
		return true;
	}

	@Override
	public void finish(Boolean b) {
		log.info("Finished loading plugins.");
	}

}
