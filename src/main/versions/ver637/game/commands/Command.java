package versions.ver637.game.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Stream;

import com.framework.util.ReflectUtil;

import lombok.NonNull;
import versions.ver637.model.player.Player;

/**
 * A {@code Command} is used by players when type two semi-colons behind a word
 * at the start of the chatbox or they type a word into the game console.
 * Commands generally have arguments and are used determine what the command
 * does.
 * 
 * @author Albert Beaupre
 */
public interface Command {

	static final HashMap<String, Command> commands = new HashMap<>();

	/**
	 * Returns the {@code Command} with the given name. If no command is found, then
	 * the {@link #DoesNotExist} is the default return value.
	 * 
	 * @param name the name of the command
	 * @return the commmand found; otherwise the DoesNotExist command
	 */
	public static Command getCommand(@NonNull String name) {
		if (commands.isEmpty()) {
			ArrayList<Class<?>> classes = ReflectUtil.getClassesInPackage("versions.ver637.game.commands.impl");
			for (Class<?> clazz : classes) {
				if (Command.class.isAssignableFrom(clazz)) {
					try {
						Command command = Command.class.cast(clazz.getDeclaredConstructor().newInstance());
						registerCommand(command);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return commands.getOrDefault(name.toLowerCase(), DoesNotExist);
	}

	/**
	 * Registers this {@code Command} so that is may be executed from chatbox or
	 * console.
	 * 
	 * @param command the command to register
	 */
	public static void registerCommand(@NonNull Command command) {
		Stream.of(command.names()).forEach(name -> {
			commands.put(name.toLowerCase(), command);
		});
	}

	/**
	 * The default command if no command can be found for a given name.
	 */
	public static Command DoesNotExist = new Command() {

		@Override
		public String[] names() {
			return new String[] { "N/A" };
		}

		@Override
		public String description() {
			return "This command does not exist";
		}

		@Override
		public void onExecute(Player player, String name, String... arguments) {}

		@Override
		public void onFail(Player player) {}

	};

	/**
	 * Returns the boolean value for the given {@code string}. If there is an error
	 * while parsing the boolean, the given {@code defaultValue} is returned
	 * instead.
	 * 
	 * @param string       the string to parse as integer
	 * @param defaultValue the value returned if an error occurs
	 * @return the parsed boolean; default value otherwise
	 */
	public default boolean getBoolean(String string, boolean defaultValue) {
		try {
			return Boolean.parseBoolean(string);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * Returns the double value for the given {@code string}. If there is an error
	 * while parsing the double, the given {@code defaultValue} is returned instead.
	 * 
	 * @param string       the string to parse as integer
	 * @param defaultValue the value returned if an error occurs
	 * @return the parsed double; default value otherwise
	 */
	public default double getDouble(String string, double defaultValue) {
		try {
			return Double.parseDouble(string);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * Returns the integer value for the given {@code string}. If there is an error
	 * while parsing the integer, the given {@code defaultValue} is returned
	 * instead.
	 * 
	 * @param string       the string to parse as integer
	 * @param defaultValue the value returned if an error occurs
	 * @return the parsed integer; default value otherwise
	 */
	public default int getInt(String string, int defaultValue) {
		try {
			return Integer.parseInt(string);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * The array of names for this {@code Command}. This is usually executed by the
	 * player using two semi-colons in the chatbox before the name of the command,
	 * or by typing it out first in a console.
	 * 
	 * @return the array of names
	 */
	String[] names();

	/**
	 * The description of what this {@code Command} does.
	 * 
	 * @return the description
	 */
	String description();

	/**
	 * This method executes the actions of the command.
	 */
	void onExecute(Player player, String name, String... arguments);

	/**
	 * This method is called if there is a failure executing the command.
	 */
	void onFail(Player player);

}
