package event;

import com.framework.RSFramework;

public class TestEventApplication {

	public static void main(String[] args) throws Exception {
		System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "DEBUG");
		RSFramework.run(TestEventApplication.class);

		TestHelloEvent event = new TestHelloEvent("Hello World");
		RSFramework.post(event);
	}

}
