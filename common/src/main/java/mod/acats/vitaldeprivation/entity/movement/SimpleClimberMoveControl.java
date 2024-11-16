package mod.acats.vitaldeprivation.entity.movement;

import mod.acats.vitaldeprivation.interfaces.Climber;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.phys.Vec3;

public class SimpleClimberMoveControl extends MoveControl {
    private final Climber climber;
    public SimpleClimberMoveControl(Climber mob) {
        super((Mob) mob);
        this.climber = mob;
    }

    @Override
    public void tick() {
        climber.clampToSurfaceOrDrop();
        if (this.operation == MoveControl.Operation.MOVE_TO) {
            this.operation = MoveControl.Operation.WAIT;

            double offsetX = this.wantedX - this.mob.getX();
            double offsetY = this.wantedY - this.mob.getY();
            double offsetZ = this.wantedZ - this.mob.getZ();

            double speed = this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED);

            Vec3 desiredVelocity = new Vec3(offsetX, offsetY, offsetZ).normalize().scale(speed);

            mob.setDeltaMovement(mob.getDeltaMovement().scale(0.5D).add(desiredVelocity.scale(0.5D)));

            return;
        }

        super.tick();
    }
}
