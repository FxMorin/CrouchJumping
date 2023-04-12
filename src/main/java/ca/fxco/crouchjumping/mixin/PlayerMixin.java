package ca.fxco.crouchjumping.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin extends EntityMixin {

    private boolean isSneakJumping = false;

    @Override
    protected void crouchJumping$adjustShiftJumping(Pose pose, CallbackInfo ci) {
        if (pose == Pose.CROUCHING && getPose() != Pose.CROUCHING && !this.isOnGround()) {
            isSneakJumping = true;
            this.setPos(this.getX(), this.getY() + 0.4F, this.getZ());
            this.setBoundingBox(this.getBoundingBox().move(0, 0.4, 0));
        } else if (this.isSneakJumping && pose != Pose.CROUCHING && getPose() == Pose.CROUCHING) {
            isSneakJumping = false;
            this.setBoundingBox(this.getBoundingBox().move(0, -0.4, 0));
            if (!this.isOnGround()) {
                float adjust = alignPosToBlockBelow(-0.4F);
                if (adjust != 0) {
                    this.setPos(this.getX(), this.getY() + adjust, this.getZ());
                }
            }
        }
    }

    private float alignPosToBlockBelow(float drop) {
        final float step = 0.05F;
        float y = drop;
        while(!this.getLevel().noCollision((Entity)(Object)this, this.getBoundingBox().move(0, y, 0))) {
            if (y < -step) {
                return 0F;
            } else if (y < 0F) {
                y += step;
            }
        }
        return y;
    }
}
