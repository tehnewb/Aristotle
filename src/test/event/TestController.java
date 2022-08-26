package event;

import com.framework.RSController;
import com.framework.event.RSEventMethod;

@RSController
public class TestController {

	@RSEventMethod
	public void sayHello(TestHelloEvent event) {
		System.out.println(event.message());
	}

}
