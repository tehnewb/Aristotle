package versions.ver637.pane.chat;

public abstract class StringRequest extends RequestInterface {

	public StringRequest(String title) {
		super(title, RequestType.String);
	}

	public abstract void handleRequest(String value);

}
