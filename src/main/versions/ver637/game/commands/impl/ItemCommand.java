package versions.ver637.game.commands.impl;

import versions.ver637.game.commands.Command;
import versions.ver637.model.item.Item;
import versions.ver637.model.player.Player;

public class ItemCommand implements Command {

	@Override
	public String[] names() {
		return new String[] { "item", "itm" };
	}

	@Override
	public String description() {
		return "Spawns an item to your inventory";
	}

	@Override
	public void onExecute(Player player, String name, String... arguments) {
		int itemID = this.getInt(arguments[0], -1);
		int amount = 1;

		if (itemID == -1)
			return;

		if (arguments.length > 1)
			amount = this.getInt(arguments[1], 1);

		player.getInventory().addItem(new Item(itemID, amount));
	}

	@Override
	public void onFail(Player player) {
		player.sendMessage("Use item command like this ::item [itemID] [itemAmount]");
	}

}
