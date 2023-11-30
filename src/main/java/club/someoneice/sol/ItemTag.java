package club.someoneice.sol;

import club.someoneice.togocup.tags.Tag;
import club.someoneice.togocup.tags.TagsManager;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class ItemTag {
    public static final TagsManager TAGS = TagsManager.INSTANCE;

    public static final Tag<Item> MEAT = TAGS.registerTag("item_food_meat", Items.cooked_beef, Items.cooked_chicken, Items.cooked_fished, Items.cooked_porkchop, Items.beef, Items.chicken, Items.fish, Items.porkchop, Items.spider_eye, Items.rotten_flesh);
    public static final Tag<Item> VERITABLE = TAGS.registerTag("item_food_veritable", Items.carrot, Items.apple, Items.mushroom_stew, Items.golden_carrot, Items.melon, Items.potato, Items.baked_potato, Items.poisonous_potato, Items.pumpkin_pie);
    public static final Tag<Item> MEAL = TAGS.registerTag("item_food_meal", Items.bread, Items.potato, Items.baked_potato, Items.poisonous_potato, Items.pumpkin_pie);
}
