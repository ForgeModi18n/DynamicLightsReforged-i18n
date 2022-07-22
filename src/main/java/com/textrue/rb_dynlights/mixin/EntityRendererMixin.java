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
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin<T extends Entity>
{
    @Inject(method = "getBlockLightLevel", at = @At("RETURN"), cancellable = true)
    private void onGetBlockLight(T entity, BlockPos pos, CallbackInfoReturnable<Integer> cir)
    {
        if (!RbDynamicLights.isEnabled())
            return; // Do not touch to the value.

        int vanilla = cir.getReturnValueI();
        int entityLuminance = ((DynamicLightSource) entity).getLuminance();
        if (entityLuminance >= 15)
            cir.setReturnValue(entityLuminance);

        int posLuminance = (int) RbDynamicLights.getDynamicLightLevel(pos);

        cir.setReturnValue(Math.max(Math.max(vanilla, entityLuminance), posLuminance));
    }
}
