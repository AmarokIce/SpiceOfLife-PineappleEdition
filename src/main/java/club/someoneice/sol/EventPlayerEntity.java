package club.someoneice.sol;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.FoodStats;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerUseItemEvent;

import java.util.Random;

public class EventPlayerEntity {
    private final String meal = "meal";
    private final String veritable = "veritable";
    private final String meat = "meat";

    @SubscribeEvent
    public void onPlayerEaten(PlayerUseItemEvent.Finish event) {
        ItemStack item = event.item;
        if (!(item.getItem() instanceof ItemFood)) return;

        EntityPlayer player = event.entityPlayer;
        NBTTagCompound nbt = player.getEntityData();

        int mealValue = nbt.getInteger(meal);
        int meatValue = nbt.getInteger(meat);
        int veritableValue = nbt.getInteger(veritable);

        Item itemFood = item.getItem();

        boolean isMealFlag = ItemTag.MEAL.has(itemFood);
        boolean isMeatFlag = ItemTag.MEAT.has(itemFood);
        boolean isVeritableFlag = ItemTag.VERITABLE.has(itemFood);

        if (isMealFlag) {
            mealValue += 2;
            veritableValue = Math.max(veritableValue - 1, 0);
            meatValue = Math.max(meatValue - 1, 0);
        }

        if (isMeatFlag) {
            meatValue += 2;
            mealValue = Math.max(mealValue - 1, 0);
        }

        if (isVeritableFlag) {
            veritableValue += 2;
            meatValue = Math.max(meatValue - 1, 0);
        }

        FoodStats foodStats = player.getFoodStats();
        Random random = new Random();

        if (meatValue > 50) {
            if (isMeatFlag) {
                meatValue = Math.min(meatValue + 5, 100);

                player.removePotionEffect(Potion.hunger.getId());
                player.addPotionEffect(new PotionEffect(Potion.damageBoost.getId(), 20 * 60 * 2, 0));

                foodStats.addStats(2, 0.2f);
            } else if (isVeritableFlag) {
                player.addChatComponentMessage(new ChatComponentTranslation("sol.meat.eat_veritable.message"));

                player.addPotionEffect(new PotionEffect(Potion.hunger.getId(), 20 * 60, 0));
                foodStats.setFoodLevel(Math.max(foodStats.getFoodLevel() - 2, 0));
                foodStats.setFoodSaturationLevel(Math.max(foodStats.getSaturationLevel() - 0.2f, 0.0f));

                veritableValue += 1;
            }
        }
        else if (veritableValue > 30) {
            if (isVeritableFlag) {
                veritableValue = Math.min(veritableValue + 5, 100);

                player.removePotionEffect(Potion.hunger.getId());
                player.removePotionEffect(Potion.poison.getId());
                player.addPotionEffect(new PotionEffect(Potion.nightVision.getId(), 20 * 60 * 2, 0));
                player.addPotionEffect(new PotionEffect(Potion.damageBoost.getId(), 20 * 60 * 2, 0));
            }

            if (veritableValue > 60) {
                if (isMeatFlag && random.nextDouble() < 0.7) {
                    player.addChatComponentMessage(new ChatComponentTranslation("sol.veritable.eat_meat.message"));
                    player.clearActivePotions();
                } else if (isVeritableFlag) {
                    player.getFoodStats().addStats(2, 0.2f);
                }
            }
        }
        else if (mealValue > 50) {
            player.addChatComponentMessage(new ChatComponentTranslation("sol.meal.too_fat.message"));
            player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.getId(), 20 * 60 * 2, 0));
        }
        else if (mealValue > 10
                && veritableValue > 10
                && (meatValue > 10 && meatValue <= 35)
        ) {
            player.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), 20 * 60 * 3));
            player.addPotionEffect(new PotionEffect(Potion.digSpeed.getId(), 20 * 60 * 3));
            player.addPotionEffect(new PotionEffect(Potion.damageBoost.getId(), 20 * 60 * 3));
            player.addPotionEffect(new PotionEffect(Potion.resistance.getId(), 20 * 60 * 3));
        }

        nbt.setInteger(meal, mealValue);
        nbt.setInteger(meat, meatValue);
        nbt.setInteger(veritable, veritableValue);
    }

    @SubscribeEvent
    public void onPlayerDeath(LivingDeathEvent event) {
        if (!(event.entity instanceof EntityPlayer)) return;
        NBTTagCompound nbt = event.entity.getEntityData();

        int mealValue = nbt.getInteger(meal);
        int meatValue = nbt.getInteger(meat);
        int veritableValue = nbt.getInteger(veritable);

        nbt.setInteger(meal, Math.max(mealValue - 10, 0));
        nbt.setInteger(meat, Math.max(meatValue - 10, 0));
        nbt.setInteger(veritable, Math.max(veritableValue - 10, 0));
    }
}
