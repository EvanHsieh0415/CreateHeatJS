package com.xiaohunao.createheatjs.mixin;

import com.simibubi.create.content.fluids.tank.BoilerHeaters;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = BoilerHeaters.class, remap = false)
public class BoilerHeatersMixin {
    @Inject(method = "registerDefaults", at = @At("HEAD"), cancellable = true)
    private static void registerDefaultsMixin(CallbackInfo ci) {
//        HeatManager manager = CreateHeatJS.Manager;
//        Multimap<String, Pair<BlockState, Predicate<BlockState>>> heatSource = manager.getHeatSource();
//        heatSource.forEach((heatLevel, pair) -> {
//            BlockState first = pair.getFirst();
//            registerHeater(first.getBlock(), (level, pos, state) -> {
//                if (state.getBlock() == first.getBlock() && pair.getSecond().test(state)) {
//                    return manager.getHeatLevelPrior(heatLevel);
//                }
//                return -1;
//            });
//        });
//        ci.cancel();
    }
    @Shadow public static void registerHeater(Block block, BoilerHeaters.Heater heater) {}

}
