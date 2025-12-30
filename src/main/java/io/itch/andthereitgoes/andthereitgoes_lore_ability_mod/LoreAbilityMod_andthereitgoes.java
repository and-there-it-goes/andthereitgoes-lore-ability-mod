package io.itch.andthereitgoes.andthereitgoes_lore_ability_mod;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;


// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(LoreAbilityMod_andthereitgoes.MODID)
public class LoreAbilityMod_andthereitgoes {

  // Define mod id in a common place for everything to reference
  public static final String MODID = "andthereitgoes_lore_ability_mod";

  // Directly reference a slf4j logger
  private static final Logger LOGGER = LogUtils.getLogger();

  // Create a Deferred Register to hold Blocks which will all be registered under the "andthereitgoes_lore_ability_mod" namespace
//  public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
  // Create a Deferred Register to hold Items which will all be registered under the "andthereitgoes_lore_ability_mod" namespace
//  public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
  // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "andthereitgoes_lore_ability_mod" namespace
//  public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

  // Creates a new Block with the id "andthereitgoes_lore_ability_mod:example_block", combining the namespace and path
//  public static final DeferredBlock<Block> EXAMPLE_BLOCK = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));
  // Creates a new BlockItem with the id "andthereitgoes_lore_ability_mod:example_block", combining the namespace and path
//  public static final DeferredItem<BlockItem> EXAMPLE_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("example_block", EXAMPLE_BLOCK);

  // Creates a new food item with the id "andthereitgoes_lore_ability_mod:example_id", nutrition 1 and saturation 2
//  public static final DeferredItem<Item> EXAMPLE_ITEM = ITEMS.registerSimpleItem("example_item", new Item.Properties().food(new FoodProperties.Builder().alwaysEdible().nutrition(1).saturationModifier(2f).build()));

  // Creates a creative tab with the id "andthereitgoes_lore_ability_mod:example_tab" for the example item, that is placed after the combat tab
//  public static final DeferredHolder<CreativeModeTab,CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup.andthereitgoes_lore_ability_mod")).withTabsBefore(CreativeModeTabs.COMBAT).icon(() -> EXAMPLE_ITEM.get().getDefaultInstance()).displayItems((parameters, output) -> {
//    output.accept(EXAMPLE_ITEM.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
//  }).build());

  public static String[] ITEM_ID_LIST_CONTAINS_MEAT = {
      "minecraft:beef",
      "minecraft:cooked_beef",
      "minecraft:porkchop",
      "minecraft:cooked_porkchop",
      "minecraft:mutton",
      "minecraft:cooked_mutton",
      "minecraft:chicken",
      "minecraft:cooked_chicken",
      "minecraft:rabbit",
      "minecraft:cooked_rabbit",
      "minecraft:rabbit_stew",
      "farmersdelight:minced_beef",
      "farmersdelight:beef_patty",
      "farmersdelight:chicken_cuts",
      "farmersdelight:cooked_chicken_cuts",
      "farmersdelight:bacon",
      "farmersdelight:cooked_bacon",
      "farmersdelight:mutton_chops",
      "farmersdelight:cooked_mutton_chops",
      "farmersdelight:ham",
      "farmersdelight:cooked_ham",
      "farmersdelight:barbecue_stick",
      "farmersdelight:chicken_sandwich",
      "farmersdelight:hamburger",
      "farmersdelight:bacon_sandwich",
      "farmersdelight:mutton_wrap",
      "farmersdelight:beef_stew",
      "farmersdelight:chicken_soup",
      "farmersdelight:bacon_and_eggs",
      "farmersdelight:pasta_with_meatballs",
      "farmersdelight:pasta_with_mutton_chop",
      "farmersdelight:roasted_mutton_chops",
      "farmersdelight:steak_and_potatoes",
      "farmersdelight:ratatouille",
      "farmersdelight:roast_chicken_block",
      "farmersdelight:roast_chicken",
      "farmersdelight:honey_glazed_ham_block",
      "farmersdelight:honey_glazed_ham",
      "farmersdelight:shepherds_pie_block",
      "farmersdelight:shepherds_pie",
      "croptopia:bacon",
      "croptopia:cooked_bacon",
      "croptopia:lemon_chicken",
      "croptopia:fried_chicken",
      "croptopia:beef_jerky",
      "croptopia:pork_jerky",
      "croptopia:pork_and_beans",
      "croptopia:ham_sandwich",
      "croptopia:blt",
      "croptopia:tuna_sandwich",
      "croptopia:cheeseburger",
      "croptopia:hamburger",
      "croptopia:pepperoni",
      "croptopia:supreme_pizza",
      "croptopia:pineapple_pepperoni_pizza",
      "croptopia:chicken_and_noodles",
      "croptopia:chicken_and_dumplings",
      "croptopia:chicken_and_rice",
      "croptopia:taco",
      "croptopia:egg_roll",
      "croptopia:cashew_chicken",
      "croptopia:carnitas",
      "croptopia:fajitas",
      "croptopia:enchilada",
      "croptopia:tamales",
      "croptopia:stuffed_poblanos",
      "croptopia:chimichanga",
      "croptopia:quesadilla",
      "croptopia:shepherds_pie",
      "croptopia:beef_wellington",
      "croptopia:cornish_pasty",
      "croptopia:beef_stir_fry",
      "croptopia:beef_stew",
      "croptopia:potato_soup",
      "croptopia:ratatouille",
      "croptopia:cabbage_roll",
      "croptopia:borscht",
      "croptopia:goulash",
      "croptopia:croque_madame",
      "croptopia:croque_monsieur",
      "croptopia:fried_frog_legs",
      "croptopia:frog_legs",
      "croptopia:ground_pork",
      "croptopia:sausage",
      "croptopia:the_big_breakfast",
      "croptopia:transcendental_breakfast",
      "croptopia:raw_ravager_meat",
      "croptopia:cooked_ravager_meat",
  };

  // The constructor for the mod class is the first code that is run when your mod is loaded.
  // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
  public LoreAbilityMod_andthereitgoes(IEventBus modEventBus, ModContainer modContainer) {
    // Register the commonSetup method for modloading
//    modEventBus.addListener(this::commonSetup);

    // Register the Deferred Register to the mod event bus so blocks get registered
//    BLOCKS.register(modEventBus);
    // Register the Deferred Register to the mod event bus so items get registered
//    ITEMS.register(modEventBus);
    // Register the Deferred Register to the mod event bus so tabs get registered
//    CREATIVE_MODE_TABS.register(modEventBus);

    // Register ourselves for server and other game events we are interested in.
    // Note that this is necessary if and only if we want *this* class (LoreAbilityMod_andthereitgoes) to respond directly to events.
    // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
//    NeoForge.EVENT_BUS.register(this);

    // Register the item to a creative tab
    modEventBus.addListener(this::addCreative);

    // Register our mod's ModConfigSpec so that FML can create and load the config file for us
    modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
  }

//  private void commonSetup(final FMLCommonSetupEvent event) {
    // Some common setup code
//    LOGGER.info("HELLO FROM COMMON SETUP");
//
//    if (Config.logDirtBlock) LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
//
//    LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);
//
//    Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
//  }

  // Add the example block item to the building blocks tab
  private void addCreative(BuildCreativeModeTabContentsEvent event) {
//    if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) event.accept(EXAMPLE_BLOCK_ITEM);
  }

  // You can use SubscribeEvent and let the Event Bus discover methods to call
//  @SubscribeEvent
//  public void onServerStarting(ServerStartingEvent event) {
    // Do something when the server starts
//    LOGGER.info("HELLO from server starting");
//  }

  // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
//  @EventBusSubscriber(modid=MODID, value=Dist.CLIENT)
//  public static class ClientModEvents {
//    @SubscribeEvent
//    public static void onClientSetup(FMLClientSetupEvent event) {
      // Some client setup code
//      LOGGER.info("HELLO FROM CLIENT SETUP");
//      LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
//    }
//  }
}
