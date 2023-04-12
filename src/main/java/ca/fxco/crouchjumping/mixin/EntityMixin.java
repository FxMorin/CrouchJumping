package ca.fxco.crouchjumping.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow public abstract Pose getPose();

    @Shadow public abstract void setPos(double d, double e, double f);

    @Shadow public abstract double getX();

    @Shadow public abstract double getY();

    @Shadow public abstract double getZ();

    @Shadow public abstract void setBoundingBox(AABB aABB);

    @Shadow public abstract AABB getBoundingBox();

    @Shadow public abstract boolean isOnGround();

    @Shadow public abstract Level getLevel();

    @Inject(
            method = "setPose",
            at = @At("HEAD")
    )
    protected void crouchJumping$adjustShiftJumping(Pose pose, CallbackInfo ci) {}
}
