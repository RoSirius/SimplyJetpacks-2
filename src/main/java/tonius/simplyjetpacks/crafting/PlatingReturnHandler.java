package tonius.simplyjetpacks.crafting;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import tonius.simplyjetpacks.item.ItemFluxpack;
import tonius.simplyjetpacks.item.ItemJetpack;
import tonius.simplyjetpacks.item.Packs;
import tonius.simplyjetpacks.setup.ModItems;

public class PlatingReturnHandler {
	@SubscribeEvent
	public void onItemCrafted(ItemCraftedEvent evt) {
		if (evt.player.world.isRemote || !(evt.crafting.getItem() instanceof ItemJetpack)) {
			if (!(evt.crafting.getItem() instanceof ItemFluxpack)) {
				return;
			}
		}

		if (evt.crafting.getItem() instanceof ItemJetpack) {
			Packs outputPack = Packs.getTypeFromMeta(evt.crafting.getItem().getMetadata(evt.crafting));
			if (outputPack.getIsArmored()) {
				return;
			}
			for (int i = 0; i < evt.craftMatrix.getSizeInventory(); i++) {
				ItemStack input = evt.craftMatrix.getStackInSlot(i);
				if (input == null || !(input.getItem() instanceof ItemJetpack)) {
					continue;
				}
				Packs inputPack = Packs.getTypeFromMeta(evt.crafting.getItem().getMetadata(input));
				if (inputPack != null && inputPack.isArmored) {

					EntityItem item = evt.player.entityDropItem(new ItemStack(ModItems.metaItemMods, 1, inputPack.getPlatingMeta()), 0.0F);
					item.setNoPickupDelay();
					break;
				}
			}
		}
	}
}