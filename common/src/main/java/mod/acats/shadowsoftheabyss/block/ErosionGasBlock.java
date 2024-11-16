package mod.acats.vitaldeprivation.block;

import mod.acats.vitaldeprivation.config.ErosionGasConfig;
import mod.acats.vitaldeprivation.registry.BlockRegistry;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.ParametersAreNonnullByDefault;

@SuppressWarnings("deprecation")
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ErosionGasBlock extends HalfTransparentBlock {
    public ErosionGasBlock(Properties properties) {
        super(properties);
    }

    public static final int SETTLE_HEIGHT = 96;

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource randomSource) {

        if (!ErosionGasConfig.INSTANCE.enabled.get()) {
            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
            return;
        }

        BlockPos newPos = pos.getY() > SETTLE_HEIGHT ? pos.below() : pos.above();
        if (isReplaceable(level.getBlockState(newPos))) {
            moveTo(level, pos, newPos);
            return;
        }

        Direction moveDirection = Direction.Plane.HORIZONTAL.getRandomDirection(randomSource);
        if (isReplaceable(level.getBlockState(pos.relative(moveDirection)))) {
            moveTo(level, pos, pos.relative(moveDirection));
            pos = pos.relative(moveDirection);
        }

        if (randomSource.nextInt(16) != 0) {
            return;
        }

        BlockPos targetPos = pos.relative(Direction.getRandom(randomSource));
        BlockState targetState = level.getBlockState(targetPos);
        if (isReplaceable(targetState) ||
                (targetState.getDestroySpeed(level, targetPos) >= 0 &&
                        randomSource.nextInt((int) Math.max(targetState.getDestroySpeed(level, targetPos), 1) * 4) == 0)) {
            level.setBlockAndUpdate(targetPos, BlockRegistry.EROSION_GAS.get().defaultBlockState());
        }
    }

    private void moveTo(Level level, BlockPos oldPos, BlockPos newPos) {

        level.setBlockAndUpdate(oldPos, Blocks.AIR.defaultBlockState());
        level.setBlockAndUpdate(newPos, BlockRegistry.EROSION_GAS.get().defaultBlockState());
    }

    private boolean isReplaceable(BlockState blockState) {
        return blockState.isAir() || blockState.canBeReplaced();
    }

    @Override
    public VoxelShape getVisualShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
        return Shapes.empty();
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
        return Shapes.empty();
    }

    @Override
    public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
        return true;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
        return Shapes.empty();
    }
}
