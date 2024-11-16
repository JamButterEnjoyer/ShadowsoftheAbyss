package mod.acats.vitaldeprivation.registry;

import mod.acats.fromanotherlibrary.platform.ModLoaderSpecific;
import mod.acats.fromanotherlibrary.registry.FALRegister;
import mod.acats.fromanotherlibrary.registry.FALRegistryObject;
import mod.acats.fromanotherlibrary.registry.TabPopulator;
import mod.acats.vitaldeprivation.item.ProximitySensorItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class ItemRegistry {
    public static final FALRegister<Item> ITEM_REGISTER = new FALRegister<>();


    public static final FALRegistryObject<Item> CREATURE_SPAWN_EGG;
    public static final FALRegistryObject<Item> PROXIMITY_SENSOR;


    public static final TabPopulator TAB_POPULATOR = new TabPopulator();

    static {
        CREATURE_SPAWN_EGG = ITEM_REGISTER.register("creature_spawn_egg",
                () -> ModLoaderSpecific.INSTANCE.createSpawnEgg(EntityRegistry.CREATURE::get, 0x000000, 0x202020));

        PROXIMITY_SENSOR = ITEM_REGISTER.register("proximity_sensor", () -> new ProximitySensorItem(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)));

        CreativeModeTab spawnEggs = BuiltInRegistries.CREATIVE_MODE_TAB.get(new ResourceLocation("spawn_eggs"));
        if (spawnEggs != null) {
            ResourceKey<CreativeModeTab> spawnEggsTab = BuiltInRegistries.CREATIVE_MODE_TAB.getResourceKey(spawnEggs).orElseThrow();
            TAB_POPULATOR.setTabs(CREATURE_SPAWN_EGG, spawnEggsTab);
        }

        CreativeModeTab functionalBlocks = BuiltInRegistries.CREATIVE_MODE_TAB.get(new ResourceLocation("functional_blocks"));
        if (functionalBlocks != null) {
            ResourceKey<CreativeModeTab> functionalBlocksTab = BuiltInRegistries.CREATIVE_MODE_TAB.getResourceKey(functionalBlocks).orElseThrow();
            TAB_POPULATOR.setTabs(() -> ITEM_REGISTER.get("erosion_gas").orElseThrow(), functionalBlocksTab);
        }
    }
}
