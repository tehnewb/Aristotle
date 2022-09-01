package versions.ver637.pane;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * A class used to configure the bitwise struct for an interface. The struct
 * available include enabling/disabling the primary left click, or right click
 * options, using items/spells/interface components on ground
 * items/npcs/objects/players/yourself/interface components, configuring the
 * interface event height (how high up the hierarchy parents are notified of
 * clicks), and whether the interface components itself can be the target of a
 * 'use with' action
 * 
 * @author Mangis
 */
@AllArgsConstructor
@NoArgsConstructor
public class ComponentSettings {

	@Getter
	private int value;

	/**
	 * True if the struct have a left click entityOption
	 * 
	 * @return True if the struct have a left click entityOption
	 */
	public boolean hasPrimaryOption() {
		return (value & 0x1) != 0;
	}

	/**
	 * True if the struct have the right click entityOption for the given id.
	 * 
	 * @param optionId the entityOption id, value is 0-9
	 * @return True if the struct have the right click entityOption for the given
	 *         id.
	 */
	public boolean hasSecondaryOption(int optionId) {
		if (optionId < 0 || optionId > 9)
			throw new IllegalArgumentException("Bad entityOption requested: " + optionId);
		return (value & (0x1 << (optionId + 1))) != 0;
	}

	/**
	 * True if the struct allow use with items on the ground
	 * 
	 * @return True if the struct allow use with items on the ground
	 */
	public boolean canUseOnGroundItems() {
		return (value & (0x1 << 11)) != 0;
	}

	/**
	 * True if the struct allow use with npcs
	 * 
	 * @return True if the struct allow use with npcs
	 */
	public boolean canUseOnNPCs() {
		return (value & (0x1 << 12)) != 0;
	}

	/**
	 * True if the struct allow use with objects
	 * 
	 * @return True if the struct allow use with objects
	 */
	public boolean canUseOnObjects() {
		return (value & (0x1 << 13)) != 0;
	}

	/**
	 * True if the struct allow use with other players (not necessarily yourself)
	 * 
	 * @return True if the struct allow use with other players (not necessarily
	 *         yourself)
	 */
	public boolean canUseOnOtherPlayers() {
		return (value & (0x1 << 14)) != 0;
	}

	/**
	 * True if the struct allow use on themselves
	 * 
	 * @return True if the struct allow use on themselves
	 */
	public boolean canUseOnSelf() {
		return (value & (0x1 << 15)) != 0;
	}

	/**
	 * True if the struct allow interface components to be dragged
	 * 
	 * @return True if the struct allow interface components to be dragged
	 */
	public boolean canDrag() {
		return (value & (0x1 << 23)) != 0;
	}

	/**
	 * True if the struct allow items to be dragged onto interface components
	 * 
	 * @return True if the struct allow items to be dragged onto interface
	 *         components
	 */
	public boolean canDragOnto() {
		return (value & (0x1 << 21)) != 0;
	}

	/**
	 * True if the struct allow use on other interface components, eg, high alchemy
	 * is used on items.
	 * 
	 * @return True if the struct allow use on other interface components, eg, high
	 *         alchemy is used on items.
	 */
	public boolean canUseOnInterfaceComponent() {
		return (value & (0x1 << 16)) != 0;
	}

	/**
	 * 0-7, the height up the chain to notify parent containers when a button is
	 * clicked.
	 * 
	 * @return 0-7, the height up the chain to notify parent containers when a
	 *         button is clicked. The higher the height, the further back the
	 *         parent.
	 */
	public int getInterfaceDepth() {
		int bits = (value & (0x7 << 18));
		return bits >> 18;
	}

	/**
	 * True if components can be a catalyst in the 'Use With' functionality. For
	 * example, items should set this to true when in the inventory to allow for
	 * alchemy, while items in the bank should not.
	 * 
	 * @return true if the components can be a catalyst in teh 'Use With'
	 *         functionality
	 */
	public boolean isUseOnTarget() {
		return (value & (0x1 << 22)) != 0;
	}

	/**
	 * Set's canUseOnFlag. if it's true then other interface components can do use
	 * on this interface component. Example would be using High alchemy spell on the
	 * inventory item. If inventory component where items are stored doesn't allow
	 * the canUseOn , it would not be possible to use High Alchemy on that item.
	 */
	public ComponentSettings setIsUseOnTarget(boolean allow) {
		value &= ~(1 << 22);
		if (allow)
			value |= (1 << 22);
		return this;
	}

	/**
	 * Set's canDragOnto. if it's true items can be dragged onto interface
	 * components. An example would be dragging an item in the bank onto a bank tab.
	 */
	public ComponentSettings setCanDragOnto(boolean allow) {
		value &= ~(1 << 21);
		if (allow)
			value |= (1 << 21);
		return this;
	}

	/**
	 * Set's canUseOnFlag. if it's true, then interface components can be dragged
	 */
	public ComponentSettings setCanDrag(boolean allow) {
		value &= ~(1 << 23);
		if (allow)
			value |= (1 << 23);
		return this;
	}

	/**
	 * Set's standard entityOption struct. Great example of standard click
	 * entityOption is the Continue button in dialog interface. If the entityOption
	 * is not allowed the packet won't be send to server.
	 * 
	 * @param allowed
	 */
	public ComponentSettings setPrimaryOption(boolean allowed) {
		value &= ~(0x1);
		if (allowed)
			value |= 0x1;
		return this;
	}

	/**
	 * Set's right click entityOption struct. Great example of right click
	 * entityOption is the Dismiss entityOption in summoning orb. If specified
	 * entityOption is not allowed , it will not appear in right click menu and the
	 * packet will not be send to server when clicked.
	 */
	public ComponentSettings setSecondaryOption(int optionID, boolean allowed) {
		if (optionID < 0 || optionID > 9)
			throw new IllegalArgumentException("optionID must be 0-9.");
		value &= ~(0x1 << (optionID + 1)); // disable
		if (allowed)
			value |= (0x1 << (optionID + 1));
		return this;
	}

	/**
	 * Set's interface events height. For example, we have inventory interface which
	 * is opened on gameframe interface (548 or 746). If height is 1 , then the
	 * clicks in inventory will also invoke click event handler scripts on gameframe
	 * interface.
	 */
	public ComponentSettings setInterfaceDepth(int depth) {
		if (depth < 0 || depth > 7)
			throw new IllegalArgumentException("height must be 0-7.");
		value &= ~(0x7 << 18);
		value |= (depth << 18);
		return this;
	}

	/**
	 * Sets use on struct. By use on , I mean the options such as Cast in spellbook
	 * or use in inventory. If nothing is allowed then 'use' entityOption will not
	 * appear in right click menu.
	 */
	public ComponentSettings setUseOnSettings(boolean canUseOnGroundItems, boolean canUseOnNpcs, boolean canUseOnObjects, boolean canUseOnNonselfPlayers, boolean canUseOnSelfPlayer, boolean canUseOnInterfaceComponent) {
		int useFlag = 0;
		if (canUseOnGroundItems)
			useFlag |= 0x1;
		if (canUseOnNpcs)
			useFlag |= 0x2;
		if (canUseOnObjects)
			useFlag |= 0x4;
		if (canUseOnNonselfPlayers)
			useFlag |= 0x8;
		if (canUseOnSelfPlayer)
			useFlag |= 0x10;
		if (canUseOnInterfaceComponent)
			useFlag |= 0x20;
		value &= ~(0x7F << 11); // disable
		value |= useFlag << 11;
		return this;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ComponentSettings[");
		builder.append("value=" + this.value + ", ");
		builder.append("hasPrimary=" + this.hasPrimaryOption() + ", ");
		for (int i = 0; i < 9; i++)
			builder.append("hasSecondary[" + i + "]=" + this.hasSecondaryOption(i) + ", ");
		builder.append("canDrag=" + this.canDrag() + ", ");
		builder.append("canDragOnto=" + this.canDragOnto() + ", ");
		builder.append("canUseOnGroundItems=" + this.canUseOnGroundItems() + ", ");
		builder.append("canUseOnInterfaceComponent=" + this.canUseOnInterfaceComponent() + ", ");
		builder.append("canUseOnNPCs=" + this.canUseOnNPCs() + ", ");
		builder.append("canUseOnObjects=" + this.canUseOnObjects() + ", ");
		builder.append("canUseOnOtherPlayers=" + this.canUseOnOtherPlayers() + ", ");
		builder.append("canUseOnSelf=" + this.canUseOnSelf() + ", ");
		builder.append("interfaceDepth=" + this.getInterfaceDepth() + ", ");
		builder.append("isUseOnTarget=" + this.isUseOnTarget());
		builder.append("]");
		return builder.toString();
	}

}