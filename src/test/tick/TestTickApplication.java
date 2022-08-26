package tick;

import com.framework.RSFramework;
import com.framework.tick.RSTick;

public class TestTickApplication {

	public static void main(String[] args) throws Exception {
		System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "DEBUG");
		RSFramework.run(TestTickApplication.class);

		RSTick.of(t -> {
			System.out.println("Hello, World: " + t.occurrences());
		}).delay(1000).stopIf(RSTick.occurrs(10)).start();
	}

}
