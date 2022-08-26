package versions.ver637.window;

public class RSPane extends RSWindow {

	private final int[] varp = new int[3000];
	private final int[] varc = new int[2000];

	public RSPane(int interfaceID) {
		super(interfaceID, -1);
	}

	public int getVarp(int index) {
		return varp[index];
	}

	public void setVarp(int index, int value) {
		this.varp[index] = value;
	}

	public int getVarc(int index) {
		return varc[index];
	}

	public void setVarc(int index, int value) {
		this.varc[index] = value;
	}

}
