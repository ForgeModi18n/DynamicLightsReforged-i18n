/*
 * Copyright © 2020 LambdAurora <aurora42lambda@gmail.com>
 *
 * This file is part of LambDynamicLights.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package com.textrue.mg_dynlights.mixin;

import com.textrue.mg_dynlights.DynamicLightSource;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientWorld.class)
public class ClientWorldMixin
{
    @Inject(method = "onEntityRemoved", at = @At("RETURN"))
    private void onFinishRemovingEntity(Entity entity, CallbackInfo ci)
    {
        DynamicLightSource dls = (DynamicLightSource) entity;
        dls.setDynamicLightEnabled(false);
    }
}
