package mod.acats.vitaldeprivation.registry;

import mod.acats.fromanotherlibrary.registry.FALRegister;
import mod.acats.fromanotherlibrary.registry.FALRegistryObject;
import mod.acats.vitaldeprivation.block.ErosionGasBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

public class BlockRegistry {
    public static final FALRegister<Block> BLOCK_REGISTER = new FALRegister<>();

    public static final FALRegistryObject<Block> EROSION_GAS = BLOCK_REGISTER.register("erosion_gas",
            () -> new ErosionGasBlock(BlockBehaviour.Properties.of().forceSolidOn().instabreak().randomTicks().sound(SoundType.EMPTY).mapColor(MapColor.COLOR_BLACK)));
}
