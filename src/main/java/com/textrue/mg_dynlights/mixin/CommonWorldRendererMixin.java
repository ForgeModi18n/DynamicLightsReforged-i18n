/*
 * Copyright © 2020 LambdAurora <aurora42lambda@gmail.com>
 *
 * This file is part of LambDynamicLights.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package com.textrue.mg_dynlights.mixin;

import com.textrue.mg_dynlights.accessor.WorldRendererAccessor;
import com.textrue.mg_dynlights.MgDynamicLights;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockDisplayReader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = WorldRenderer.class, priority = 900)
public abstract class CommonWorldRendererMixin implements WorldRendererAccessor
{
    @Invoker("setSectionDirty")
    @Override
    public abstract void dynlights_setSectionDirty(int x, int y, int z, boolean important);



    @Inject(
            method = "getLightColor(Lnet/minecraft/world/IBlockDisplayReader;Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/BlockPos;)I",
            at = @At("TAIL"),
            cancellable = true
    )
    private static void onGetLightmapCoordinates(IBlockDisplayReader world, BlockState j, BlockPos pos, CallbackInfoReturnable<Integer> cir)
    {
        if (!world.getBlockState(pos).isSolidRender(world, pos) && MgDynamicLights.isEnabled())
        {
            int vanilla = cir.getReturnValue();
            int value = MgDynamicLights.getLightmapWithDynamicLight(pos, vanilla);

            cir.setReturnValue(value);
        }
    }
}
