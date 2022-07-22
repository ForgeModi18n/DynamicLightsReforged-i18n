/*
 * Copyright © 2020 LambdAurora <aurora42lambda@gmail.com>
 *
 * This file is part of LambDynamicLights.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package com.textrue.rb_dynlights.mixin;

import com.textrue.rb_dynlights.DynamicLightSource;
import com.textrue.rb_dynlights.RbDynamicLights;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.HangingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HangingEntity.class)
public abstract class AbstractDecorationEntityMixin extends Entity implements DynamicLightSource
{
    public AbstractDecorationEntityMixin(EntityType<?> type, World world)
    {
        super(type, world);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void onTick(CallbackInfo ci)
    {
        // We do not want to update the entity on the server.
        if (this.getCommandSenderWorld().isClientSide()) {
            if (this.removed) {
                this.setDynamicLightEnabled(false);
            } else {
                this.dynamicLightTick();
                RbDynamicLights.updateTracking(this);
            }
        }
    }
}
