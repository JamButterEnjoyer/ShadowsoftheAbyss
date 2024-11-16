package mod.acats.vitaldeprivation.entity.goal;

import mod.acats.vitaldeprivation.entity.Creature;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import org.jetbrains.annotations.NotNull;

public class CreatureMeleeAttackGoal extends MeleeAttackGoal {
    public CreatureMeleeAttackGoal(Creature creature, double speedModifier) {
        super(creature, speedModifier, true);
    }

    @Override
    protected double getAttackReachSqr(@NotNull LivingEntity entity) {
        return this.mob.getBbWidth() * 2.0F * this.mob.getBbWidth() * 2.0F + 4.0F + entity.getBbWidth();
    }
}
