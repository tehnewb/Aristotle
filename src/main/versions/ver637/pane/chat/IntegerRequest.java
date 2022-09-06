package versions.ver637.pane.chat;

public abstract class IntegerRequest extends RequestInterface {

	public IntegerRequest(String title) {
		super(title, RequestType.Integer);
	}

	public abstract void handleRequest(int value);

}
