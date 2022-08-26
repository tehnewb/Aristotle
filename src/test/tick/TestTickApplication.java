package tick;

import com.framework.RSFramework;
import com.framework.tick.RSTick;

public class TestTickApplication {

	public static void main(String[] args) throws Exception {
		System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "DEBUG");
		RSFramework.run(TestTickApplication.class);

		/**
		 * Fancy lambda. The tick has a delay of 1000ms and stops if the tick occurs 10
		 * times
		 */
		RSTick.of(t -> System.out.println("Hello, World: " + t.occurrences())).delay(1000).stopIf(RSTick.occurrs(10)).start();

		/**
		 * This is an example of the above functionality without all the fancy lambda
		 */
		new RSTick() {

			@Override
			protected void tick() {
				if (this.occurrences() == 10) {
					stop();
				}
				System.out.println("Hello, World: " + this.occurrences());
			}

		}.delay(1000).start();
	}

}
