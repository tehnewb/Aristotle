<p align="center">
  <img src="Logo.png">
</p>


<p align="center">
Aristotle is an open-source rs framework meant to increase RSPS server productivity and efficiency. This project uses <a href="https://gradle.org/">gradle</a> for building, and for the dependencies, you can take a look at the build.gradle file in the root of this project to see what's being used.
</p>
<br>
<br>
<p align="center">Click on any of the dropdown arrows below to learn about RSFramework</p>

<details><summary><h2>Starting</h2></summary>
<p>

### Starting the framework from your server
```java
  RSFramework.run(Application.class); // The Application.class should be substituted with your main class from your lowest level package
```

### Specifying the log level
```java
  /**
   * Place this piece of code in the main method BEFORE anything else.
   * Replace the "DEBUG" with your preferred log level
   */
  System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "DEBUG");
```
</p>
</details>

<details><summary><h2>Network</h2></summary>
<p>

### Binding to a port

This is the only way to start the network from the framework
```java
  HandShakeCoder defaultCoder = ...
  RSFramework.bind(43594, defaultCoder);
```

The network requires some kind of default coder to handle the encoding and decoding of the network messages. From that default coder, you can assign new coders using ```session.setCoder(RSCoder)``` This is also shown in the example below...
```java
public class HandshakeCoder implements RSSessionCoder {
	private static final byte JS5 = 15;
	private static final byte LOGIN = 14;

	@Override
	public void decode(RSNetworkSession session, RSStream stream, List<Object> out) {
		int request = stream.readUnsignedByte();
		if (request == JS5) {
			... // handling js5 variables and responses

			session.setCoder(new CacheUpdateCoder());
		} else if (request == LOGIN) {
			... // handling login variables and responses
			session.setCoder(new LoginCoder());
		}
	}

	@Override
	public RSStream encode(RSNetworkSession session, RSStream in) {
		return in;
	}

}
```

### Implementing an RSSessionCoder

Coders will handle the encoding and decoding of messages or frames. If there is no encoding done, you can just return the ```in``` parameter of the ```encode(RSNetworkSession, RSStream)``` method, otherwise return the encoded message as an *RSStream*. If you are sending any outgoing messages, they are to be added to the ```out``` list in the ```decode(RSNetworkSession, RSStream, List<Object>)``` method

### RSFrame and RSStream

An *RSFrame* is just an extension of the *RSStream* class, but with an opcode and header. Below is shown an example of creating different RSFrames...
```java
RSFrame varShortFrame = RSFrame.varShort(0); // header type is var_short with opcode of 0
RSFrame varByteFrame = RSFrame.varByte(0); // header type is var_short with opcode of 0
RSFrame standardFrame = RSFrame.standard(0); // header type is var_short with opcode of 0
```

_By usual definition, what makes a frame is a message with a corresponding operation code, so that is why it is called RSFrame._ 

### RSNetworkSession

The *RSNetworkSession* holds the *Channel*, the *RSSessionCoder*, and other attributes for the network. As shown above, you can set the coder from the session variable passed through other coders, and this originates from the default coder that is usually set on application startup. The *Channel* of the session is just from the io.netty.channel package of netty 4.1.36 and will hold various methods to handle what happens with the channel. Attributes can be set to the session as well, which connects directly to the channel. These attributes are used for setting any values to the connection while the session is active. Below is an example of using the attributes and also writing to the session...
```java
public class LoginCoder implements RSSessionCoder {

	@Override
	public void decode(RSNetworkSession session, RSStream in, List<Object> out) {
		... // login variables and functionality
	
		Player player = new Player();
		session.set("Player", player);
	}
	
	... // other methods and functionality
}

/**
 * This may be a class you use for world request packet handling in the lobby
 */
public class WorldRequest {
	
	// This is an example class not part of the framework
	public void handleFrame(RSNetworkSession session, RSFrame frame) {
		Player player = session.get("Player", Player.class); // getting the player object from the session for some reason
		
		RSFrame worldResponse = RSFrame.varShort(80);
		worldResponse.writeByte(1);
		worldResponse.writeShort(2);
		worldResponse.writeInt(3);
		...
		
		session.write(worldResponse);
	}
	
}
```
</p>
</details>
<details><summary><h2>Resources</h2></summary>
<p>

### RSResource and RSResourceWorker

The *Resource* class is meant to handle any file loading/saving or large calculations you might want done off the main thread. This can include loading account files, caches, xteas, or maybe creating dynamic maps/instances for your server. The ```queue()``` method in the *Resource* class is used to queue the resource to the *RSResourceWorker* class for loading. This is equivalent to calling ```RSFramework.queueResource(RSResource)``` Below is show an example of the *RSResource* in action...
```java
@RequiredArgsConstructor
public class AccountResource implements RSResource<Account> {

	private final String username;

	@Override
	public Account load() throws Exception {
		Account account;
		
		File file = new File("./accounts/" + username + ".json");
		if (file.exists()) {
			account = loadAccount();
		} else {
			account = createAccount();
		}
		
		return account;
	}

	@Override
	public void finish(Account account) {
		loginAccount();
	}

}

AccountResource resource = new AccountResource("CowK1ll3r");
resource.queue(); // queue the resource for loading, and once it is finished, the ```finish(T)``` method will be called and you can handle the object from there
```
</p>
</details>

<details><summary><h2>Tick</h2></summary>
<p>

## Ticks

The *RSTick* class is easy to use and can be added to the *RSTickWorker* in two different ways. You can call ```RSFramework.addTick(RSTick)``` or you can create a new *RSTick* and call the ```start()``` method of that tick. Below is an example of different tick usages...
```java
/**
 * Fancy lambda. The tick has a delay of 1000ms and stops if the tick occurs 10
 * times
 */
RSTick.of(t -> System.out.println("Hello, World: " + t.occurrences())).
	.delay(1000)
	.stopIf(RSTick.occurrs(10))
	.start();

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
```
The stopPredicate of the tick is just a condition, that if passes as true, will stop the tick. So, let's say you want the tick to stop when a player reaches a certain location, you could use ```tick.stopIf(t -> p.getLocation().equals(new location(3222, 3222)))``` the ```delay(long)``` method can also be called from within the tick to change its delay even while it is running. The ```restart()``` method will just reset the occurences and the stop predicate.

</p>
</details>


<details><summary><h2>Events</h2></summary>
<p>

*The RSController annotation is expected for more uses in the future, but as of now, it's only for events*

### RSController and RSEventMethod

The *RSController* is an annotation meant for any class expected to handle events. The framework will search for any classes that have ```@RSController``` within the lowest level package from where you called ```RSFramework.run(Class<?>)``` If there is a method that has the ```@RSEventMethod```, then that method will be registered as an 'invoker' that will also correspond to the event type within the parameters of that method. For example, if you were to have a class with this method: ```onUseItem(UseItemEvent event)``` then that method will be an invoker for the UseItemEvent class.

To post an event of any kind, you use the ```RSFramework.post(Object)``` method. No events have to be registered to the framework, but there does have to be a method with that event as a parameter for anything to be invoked.

Below is an example of using the event system...

The controller
```java
@RSController
public class TestController {

	@RSEventMethod
	public void sayHello(TestHelloEvent event) {
		System.out.println(event.message());
	}

}
```

The event class
```java
public record TestHelloEvent(String message) {}
```

Posting the event
```java
TestHelloEvent event = new TestHelloEvent("Hello World");
RSFramework.post(event);
```

</p>
</details>


<details><summary><h2>Plugin</h2></summary>
<p>

### Loading Plugins

*Plugins allow for a modular-type project where you can keep content separated from the core project.*

Jar files are the only supported way for plugins and can be loaded as such...
```java
	File dir = new File(ClassLoader.getSystemResource("MyPlugin.jar").toURI());
	RSFramework.loadPlugin(dir);
```

</p>
</details>

