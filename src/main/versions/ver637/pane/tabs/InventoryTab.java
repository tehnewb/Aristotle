package versions.ver637.pane.tabs;

import versions.ver637.model.item.Item;
import versions.ver637.model.item.ItemContainer;
import versions.ver637.model.item.ItemContainerChangeHandler;
import versions.ver637.model.player.EquipmentVariables;
import versions.ver637.network.coders.frames.ContainerFrame;
import versions.ver637.pane.ComponentClick;
import versions.ver637.pane.ComponentSettings;
import versions.ver637.pane.ComponentSwap;
import versions.ver637.pane.GameInterface;

public class InventoryTab extends GameInterface implements ItemContainerChangeHandler {

	public static final int InventoryID = 149;
	public static final int ContainerComponent = 0;
	public static final int InventoryContainerID = 93;

	public InventoryTab() {
		super(InventoryID, true);
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

		this.getComponent(ContainerComponent).setSettings(settings, 0, 55);

		player.getInventory().setChangeHandler(this);
		refreshInventory();
	}

	@Override
	public void click(ComponentClick data) {
		if (data.componentID() == 0) {
			Item item = player.getInventory().get(data.slot());

			if (item == null)
				return;

			if (data.option() == 9) {
				player.sendMessage("{0}", item.getData().getExamine());
				return;
			}

			String option = item.getData().getInventoryOptions()[data.option()];
			if (option != null && (option.equalsIgnoreCase("wear") || option.equalsIgnoreCase("wield"))) {
				EquipmentVariables.equip(player, item.getID(), data.slot());
			}
		}

	}

	@Override
	public void swap(ComponentSwap data) {
		player.getInventory().swap(data.fromSlot(), data.toSlot() - 28);

		player.getQueue().interrupt();
	}

	@Override
	public void onClose() {

	}

	@Override
	public boolean clickThrough() {
		return true;
	}

	@Override
	public int position(boolean resizable) {
		return resizable ? 91 : 206;
	}

	@Override
	public void onChange(ItemContainer container) {
		refreshInventory();
	}

	private void refreshInventory() {
		player.getSession().write(new ContainerFrame(InventoryContainerID, false, player.getInventory()));
		EquipmentVariables.calculateWeight(player);
	}
}
