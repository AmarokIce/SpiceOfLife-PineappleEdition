package club.someoneice.sol;

import club.someoneice.sol.client.ClientEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = SoLPineappleMain.MODID)
public class SoLPineappleMain {
    public static final String MODID = "sol_pineapple";

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        new ItemTag();
    }

    @Mod.EventHandler
    public void commonInit(FMLInitializationEvent event) {
        registryEvent(new EventPlayerEntity());
        registryEvent(new ClientEvent());
    }

    @Mod.EventHandler
    public void serverInit(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandFoodState());
    }

    private void registryEvent(Object eventObj) {
        MinecraftForge.EVENT_BUS.register(eventObj);
        FMLCommonHandler.instance().bus().register(eventObj);
    }
}
