package versions.ver637.pane.chat;

public abstract class LongStringRequest extends RequestInterface {

	public LongStringRequest(String title) {
		super(title, RequestType.LongString);
	}

	public abstract void handleRequest(String value);

}
