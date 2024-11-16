package mod.acats.vitaldeprivation;

import mod.acats.fromanotherlibrary.config.v2.ModConfig;
import mod.acats.fromanotherlibrary.registry.CommonMod;
import mod.acats.fromanotherlibrary.registry.FALRegister;
import mod.acats.fromanotherlibrary.registry.TabPopulator;
import mod.acats.fromanotherlibrary.registry.client.ClientMod;
import mod.acats.fromanotherlibrary.starteritems.StarterItems;
import mod.acats.vitaldeprivation.config.ErosionGasConfig;
import mod.acats.vitaldeprivation.config.SpawningConfig;
import mod.acats.vitaldeprivation.registry.*;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Optional;
import java.util.function.Supplier;

public class VitalDeprivation implements CommonMod {

    public static final String MOD_ID = "vitaldeprivation";
    public static final Logger LOGGER = LoggerFactory.getLogger("Vital Deprivation");

    public static ResourceLocation resourceLocation(String s) {
        return new ResourceLocation(MOD_ID, s);
    }

    @Override
    public String getID() {
        return MOD_ID;
    }

    @Override
    public Optional<ClientMod> getClientMod() {
        return Optional.of(new VitalDeprivationClient());
    }

    @Override
    public Optional<FALRegister<EntityType<?>>> getEntityRegister() {
        return Optional.of(EntityRegistry.ENTITY_REGISTER);
    }

    @Override
    public Optional<HashMap<EntityType<? extends LivingEntity>, Supplier<AttributeSupplier.Builder>>> getEntityAttributeRegister() {
        return Optional.of(EntityRegistry.getAttributes());
    }

    @Override
    public Optional<FALRegister<Item>> getItemRegister() {
        return Optional.of(ItemRegistry.ITEM_REGISTER);
    }

    @Override
    public Optional<TabPopulator> getTabPopulator() {
        return Optional.of(ItemRegistry.TAB_POPULATOR);
    }

    @Override
    public Optional<FALRegister<Block>> getBlockRegister() {
        return Optional.of(BlockRegistry.BLOCK_REGISTER);
    }

    @Override
    public Optional<ModConfig[]> getConfigs() {
        return Optional.of(new ModConfig[] {
                ErosionGasConfig.INSTANCE,
                SpawningConfig.INSTANCE
        });
    }

    @Override
    public Optional<FALRegister<ParticleType<?>>> getParticleRegister() {
        return Optional.of(ParticleRegistry.PARTICLE_REGISTRY);
    }

    @Override
    public void postRegisterContent() {
        SpawningRegistry.register();
        StarterItems.add(() -> new ItemStack(ItemRegistry.PROXIMITY_SENSOR.get()), true);
    }
}
