package mod.acats.vitaldeprivation.registry;

import mod.acats.fromanotherlibrary.spawning.SimpleSpawns;
import mod.acats.vitaldeprivation.config.SpawningConfig;
import mod.acats.vitaldeprivation.scaling.Scaling;
import mod.acats.vitaldeprivation.tags.VDBiomeTags;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;

public class SpawningRegistry {
    public static void register() {
        SimpleSpawns.get().addMonster(
                EntityRegistry.CREATURE,
                SpawningConfig.INSTANCE.maxSpawns.get(),
                1,
                1,
                SpawningRegistry::checkCreatureSpawnRules,
                VDBiomeTags.CREATURE_SPAWNING
        );
    }

    public static boolean checkCreatureSpawnRules(
            EntityType<? extends Monster> creature,
            ServerLevelAccessor serverLevelAccessor,
            MobSpawnType spawnType,
            BlockPos blockPos,
            RandomSource randomSource) {

        Scaling scaling = Scaling.of(serverLevelAccessor.getLevel());

        if (!serverLevelAccessor.getBiome(blockPos).is(VDBiomeTags.EXTREME_CREATURE_SPAWNING) && randomSource.nextFloat() > scaling.spawnPotential()) {
            return false;
        }

        if (!scaling.canSpawnInDaylight() && serverLevelAccessor.getBrightness(LightLayer.SKY, blockPos) != 0) {
            return false;
        }

        return serverLevelAccessor.getBrightness(LightLayer.BLOCK, blockPos) == 0 &&
                Monster.checkAnyLightMonsterSpawnRules(creature, serverLevelAccessor, spawnType, blockPos, randomSource);
    }
}
