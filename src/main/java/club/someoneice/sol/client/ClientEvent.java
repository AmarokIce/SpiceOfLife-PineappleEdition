package club.someoneice.sol.client;

import club.someoneice.sol.ItemTag;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

import java.util.Optional;

public class ClientEvent {
    @SubscribeEvent
    public void itemInfoEvent(ItemTooltipEvent event) {
        Optional.ofNullable(event.itemStack).ifPresent(it -> {
            if (ItemTag.MEAL.has(it.getItem())) {
                event.toolTip.add(new ChatComponentTranslation("sol.meal_type.message").getFormattedText());
            }

            if (ItemTag.VERITABLE.has(it.getItem())) {
                event.toolTip.add(new ChatComponentTranslation("sol.veritable_type.message").getFormattedText());
            }

            if (ItemTag.MEAT.has(it.getItem())) {
                event.toolTip.add(new ChatComponentTranslation("sol.meat_type.message").getFormattedText());
            }
        });
    }
}
