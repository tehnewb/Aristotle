package versions.ver637.network.coders.handlers;

import com.framework.network.RSFrame;

public class CarriedWeightFrame extends RSFrame {

	public static final int CarriedWeightOpcode = 14;

	public CarriedWeightFrame(int weight) {
		super(CarriedWeightOpcode, StandardType);

		writeShort(weight);
	}
}
