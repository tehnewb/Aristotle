package versions.ver637.pane.side;

import versions.ver637.model.player.equipment.EquipItemScript;
import versions.ver637.model.player.inventory.SwapItemScript;
import versions.ver637.pane.ComponentClick;
import versions.ver637.pane.ComponentSettings;
import versions.ver637.pane.ComponentSwap;
import versions.ver637.pane.GameInterfaceAdapter;

public class EquipmentSideInterface extends GameInterfaceAdapter {

	public EquipmentSideInterface() {
		super(670, false);
	}

	@Override
	public void onOpen() {
		ComponentSettings settings = new ComponentSettings();
		for (int i = 0; i < 10; i++)
			settings.setSecondaryOption(i, true);
		settings.setUseOnSettings(true, true, true, true, false, true);
		settings.setDragDepth(1);
		settings.setCanDragOnto(true);
		settings.setIsUseOnTarget(true);
		this.getComponent(0).setSettings(settings, 0, 27);
	}

	@Override
	public void click(ComponentClick data) {
		if (data.componentID() == 0) {
			player.getScripts().queue(new EquipItemScript(data.itemID(), data.slot()));
		}
	}

	@Override
	public void swap(ComponentSwap data) {
		player.getScripts().queue(new SwapItemScript(data.fromSlot(), data.toSlot()));
	}

	@Override
	public int position(boolean resizable) {
		return resizable ? 84 : 197;
	}

}