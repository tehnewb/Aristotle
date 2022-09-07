package versions.ver637.pane.tabs;

import versions.ver637.model.item.Item;
import versions.ver637.model.item.ItemContainer;
import versions.ver637.model.item.ItemContainerChangeHandler;
import versions.ver637.model.player.equipment.EquipmentVariables;
import versions.ver637.model.player.equipment.UnequipItemScript;
import versions.ver637.network.coders.frames.ContainerFrame;
import versions.ver637.pane.ComponentClick;
import versions.ver637.pane.GameInterfaceAdapter;

public class EquipmentTab extends GameInterfaceAdapter implements ItemContainerChangeHandler {

	public static final int EquipmentID = 387;
	public static final int ContainerComponent = 0;
	public static final int EquipmentContainerID = 94;

	public EquipmentTab() {
		super(EquipmentID, true);
	}

	@Override
	public void onOpen() {
		refreshEquipment();

		player.getEquipmentVariables().getEquipment().setChangeHandler(this);
	}

	@Override
	public void click(ComponentClick data) {
		if (data.componentID() >= 8 && data.componentID() <= 38) {
			int equipmentSlot = switch (data.componentID()) {
				case 8 -> EquipmentVariables.HeadSlot;
				case 11 -> EquipmentVariables.CapeSlot;
				case 14 -> EquipmentVariables.AmuletSlot;
				case 17 -> EquipmentVariables.WeaponSlot;
				case 20 -> EquipmentVariables.TorsoSlot;
				case 23 -> EquipmentVariables.ShieldSlot;
				case 26 -> EquipmentVariables.LegSlot;
				case 29 -> EquipmentVariables.HandSlot;
				case 32 -> EquipmentVariables.FeetSlot;
				case 35 -> EquipmentVariables.RingSlot;
				case 38 -> EquipmentVariables.ArrowSlot;
				default -> -1;
			};

			Item item = player.getEquipmentVariables().getEquipment().get(equipmentSlot);
			if (data.option() == 9) {
				player.sendMessage("{0}", item.getData().getExamine());
				return;
			}
			String option = item.getData().getEquipmentOptions()[data.option()];

			if (equipmentSlot == -1)
				return;

			if (option.equalsIgnoreCase("remove")) {
				player.getQueue().queue(new UnequipItemScript(equipmentSlot));
			}
		}
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
		return resizable ? 92 : 207;
	}

	@Override
	public void onChange(ItemContainer container) {
		refreshEquipment();
	}

	private void refreshEquipment() {
		EquipmentVariables.calculateWeight(player);
		player.getSession().write(new ContainerFrame(94, false, player.getEquipmentVariables().getEquipment()));
	}

}
