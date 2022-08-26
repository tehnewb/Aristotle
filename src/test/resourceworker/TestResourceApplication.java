package resourceworker;

import java.net.URL;

import com.framework.RSFramework;

public class TestResourceApplication {

	public static void main(String[] args) throws Exception {
		System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "DEBUG");
		RSFramework.run(TestResourceApplication.class);

		URL url = Thread.currentThread().getContextClassLoader().getResource("TestFile.txt");
		TestResource test = new TestResource(url);
		test.queue();
	}

}
