package plugin;

import java.io.File;

import com.framework.RSFramework;

public class TestPluginApplication {

	public static void main(String[] args) throws Exception {
		System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "DEBUG");
		RSFramework.run(TestPluginApplication.class);

		File dir = new File(ClassLoader.getSystemResource("Test.jar").toURI());
		RSFramework.loadPlugin(dir);
	}

}
