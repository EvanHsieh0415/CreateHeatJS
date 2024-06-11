package com.xiaohunao.createheatjs.mixin;

import com.google.common.collect.Multimap;
import com.mojang.datafixers.util.Pair;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.xiaohunao.createheatjs.HeatManager;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

@Mixin(BasinBlockEntity.class)
public class BasinBlockEntityMixin {
    @Inject(method = "getHeatLevelOf", at = @At("RETURN"),remap = false, cancellable = true)
    private static void getHeatLevelOf(BlockState state, CallbackInfoReturnable<BlazeBurnerBlock.HeatLevel> cir) {
        HeatManager manager = HeatManager.getInstance();
        Multimap<String, Pair<BlockState, Predicate<BlockState>>> heatSource = manager.getHeatSource();
        heatSource.forEach((heat, pair) -> {
            if (pair.getFirst().getBlock() == AllBlocks.BLAZE_BURNER.get()){
                return;
            }
            BlazeBurnerBlock.HeatLevel heatLevel = manager.getHeatLevel(heat);
            if (state.getBlock() == pair.getFirst().getBlock() && pair.getSecond().test(state)) {
                cir.setReturnValue(heatLevel);
            }
        });
    }
}
