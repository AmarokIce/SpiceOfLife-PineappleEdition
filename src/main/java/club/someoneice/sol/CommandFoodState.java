package club.someoneice.sol;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;

public class CommandFoodState extends CommandBase {

    @Override
    public String getCommandName() {
        return "sol_foodstate";
    }

    @Override
    public String getCommandUsage(ICommandSender p_71518_1_) {
        return "/sol_foodstate";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] tree) {
        EntityPlayer player = getCommandSenderAsPlayer(sender);

        NBTTagCompound nbt = player.getEntityData();

        String meal = "meal";
        String meat = "meat";
        String veritable = "veritable";

        int mealValue = nbt.getInteger(meal);
        int meatValue = nbt.getInteger(meat);
        int veritableValue = nbt.getInteger(veritable);

        player.addChatComponentMessage(new ChatComponentTranslation("sol.meat_value.message").appendText(": " + meatValue));
        player.addChatComponentMessage(new ChatComponentTranslation("sol.meal_value.message").appendText(": " + mealValue));
        player.addChatComponentMessage(new ChatComponentTranslation("sol.veritable_value.message").appendText(": " + veritableValue));
        player.addChatComponentMessage(getState(mealValue, veritableValue, meatValue));
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender p_71519_1_) {
        return true;
    }

    private static ChatComponentTranslation getState(int mealValue, int veritableValue, int meatValue) {
        ChatComponentTranslation state;

        boolean flag = mealValue > 10
                && veritableValue > 10
                && (meatValue > 10 && meatValue <= 35);

        if (meatValue > 50) state = new ChatComponentTranslation("sol.meat.message");
        else if (veritableValue > 60) state = new ChatComponentTranslation("sol.veritable.message");
        else if (mealValue > 50) state = new ChatComponentTranslation("sol.fat.message");
        else if (flag) state = new ChatComponentTranslation("sol.health.message");
        else state = new ChatComponentTranslation("sol.no_state.message");
        return state;
    }
}
