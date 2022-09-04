package versions.ver637.game.commands.impl;

import com.framework.util.ReflectUtil;

import versions.ver637.game.commands.Command;
import versions.ver637.model.player.Player;
import versions.ver637.network.coders.frames.InterfaceFrame;
import versions.ver637.pane.GamePane;
import versions.ver637.pane.InterfaceComponentData;
import versions.ver637.pane.InterfaceData;
import versions.ver637.pane.InterfaceResource;

public class InterfaceCommand implements Command {

	@Override
	public String[] names() {
		return new String[] { "inter", "interface" };
	}

	@Override
	public String description() {
		return "Opens an interface for viewing only";
	}

	@Override
	public void onExecute(Player player, String... arguments) {
		int interfaceID = Integer.parseInt(arguments[0]);

		if (arguments.length > 1 && arguments[1].equalsIgnoreCase("dump")) {
			InterfaceData data = InterfaceResource.getData(interfaceID);
			InterfaceComponentData[] components = data.components;
			int componentID = arguments.length > 2 ? Integer.parseInt(arguments[2]) : -1;
			for (InterfaceComponentData c : components) {
				if (componentID != -1 && c.componentID != componentID)
					continue;
				String[] str = ReflectUtil.describeFields(c);
				for (String s : str) {
					System.out.println(s);
				}
			}
		} else {
			GamePane pane = player.getPane();
			player.getSession().write(new InterfaceFrame(pane.getID(), interfaceID, pane.isResizable() ? 9 : 18, false));
		}
	}

	@Override
	public void onFail(Player player) {

	}

}
