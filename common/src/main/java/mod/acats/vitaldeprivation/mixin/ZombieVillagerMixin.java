package mod.acats.vitaldeprivation.mixin;

import mod.acats.vitaldeprivation.entity.Invocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ZombieVillager.class)
public abstract class ZombieVillagerMixin extends Zombie {
    public ZombieVillagerMixin(EntityType<? extends Zombie> type, Level level) {
        super(type, level);
    }

    @Inject(method = "finishConversion", at = @At("HEAD"))
    private void vd$finishConversion(ServerLevel serverLevel, CallbackInfo ci) {
        // Imagine the universe as an ocean. Our bodies are rafts and our souls are people clinging on.
        // If the raft breaks, the person will sink.
        // You can try to pull them back up but all that movement is likely to attract attention.
        // Especially when something is already right below you, drawn here by violent motion nearby.
        Invocation.create(serverLevel, position());
        // These orbs, in this analogy they're just the fin of the shark.
    }
}
