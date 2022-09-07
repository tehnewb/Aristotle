package versions.ver637.model.player;

import versions.ver637.model.item.Item;

public record PlayerUseItemEvent(Item item, int slot, String option) {}
