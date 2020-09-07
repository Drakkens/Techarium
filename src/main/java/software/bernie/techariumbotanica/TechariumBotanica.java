package software.bernie.techariumbotanica;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.techariumbotanica.datagen.TechariumLootTables;
import software.bernie.techariumbotanica.registry.*;
import software.bernie.techariumbotanica.machine.screen.AutomaticContainerScreen;

import static software.bernie.techariumbotanica.registry.ContainerRegistry.AUTO_CONTAINER;

@Mod(TechariumBotanica.ModID)
public class TechariumBotanica
{
	public final static String ModID = "techarium-botanica";
	public static Logger LOGGER;

	public TechariumBotanica()
	{
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		LOGGER = LogManager.getLogger();
		ItemRegistry.register(bus);
		BlockTileRegistry.register(bus);
		ContainerRegistry.register(bus);
		bus.addListener(this::onClientSetup);
		bus.addListener(this::gatherData);
	}

	private void gatherData(GatherDataEvent event)
	{
		DataGenerator generator = event.getGenerator();
		generator.addProvider(new TechariumLootTables(generator));
	}

	public void onClientSetup(FMLClientSetupEvent event)
	{
		ScreenManager.registerFactory(AUTO_CONTAINER.get(), AutomaticContainerScreen::new);

	}
}
