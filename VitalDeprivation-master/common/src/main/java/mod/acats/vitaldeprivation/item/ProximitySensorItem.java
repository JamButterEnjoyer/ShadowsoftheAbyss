package mod.acats.vitaldeprivation.item;

import mod.acats.vitaldeprivation.entity.Creature;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.NonnullDefault;

import java.util.List;

@NonnullDefault
public class ProximitySensorItem extends Item {
    private static final int RANGE = 30;
    private static final int RANGE_SQ = RANGE * RANGE;
    public ProximitySensorItem(Properties properties) {
        super(properties);
        counter = 0;
    }

    private float counter;

    @Override
    public void inventoryTick(ItemStack itemStack, Level level, Entity entity, int $$3, boolean selected) {
        if (level.isClientSide() && entity instanceof Player p && itemStack == p.getMainHandItem()) {

            List<Entity> entities = level.getEntities(entity, entity.getBoundingBox().inflate(RANGE), entity1 -> entity1 instanceof Creature);

            double distSq = RANGE_SQ;
            for (Entity entity1 : entities) {
                double d2 = entity1.distanceToSqr(entity);
                if (d2 < distSq) {
                    distSq = d2;
                }
            }

            float d = distSq < RANGE_SQ ?
                    (float) (distSq / RANGE_SQ) :
                    1.0F;
            float inverse = 1.0F / d;

            counter += inverse;
            if (counter > 80) {
                level.playSound(entity, entity.blockPosition(), SoundEvents.UI_BUTTON_CLICK.value(), entity.getSoundSource(), 0.2F, 1.05F - d);
                counter = 0;
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack $$0, @Nullable Level $$1, List<Component> $$2, TooltipFlag $$3) {
        $$2.add(Component.translatable("item.vitaldeprivation.proximity_sensor.tooltip1"));
        $$2.add(Component.translatable("item.vitaldeprivation.proximity_sensor.tooltip2"));
        super.appendHoverText($$0, $$1, $$2, $$3);
    }
}
