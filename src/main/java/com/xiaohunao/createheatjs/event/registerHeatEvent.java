package com.xiaohunao.createheatjs.event;

import com.xiaohunao.createheatjs.HeatManager;
import dev.latvian.mods.kubejs.event.EventJS;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Predicate;

public class registerHeatEvent extends EventJS {
    private HeatManager manager = HeatManager.getInstance();
    public void registerHeatLevel(String heatLevel,int prior,int color) {
        manager.registerHeatLevel(heatLevel,prior,color);
    }
    public void registerHeatSource(String heatLevel,String blockState,Predicate<BlockState> predicate) {
        manager.registerHeatSource(heatLevel,blockState,predicate);
    }
    public void registerHeatSource(String heatLevel,String blockState) {
        manager.registerHeatSource(heatLevel,blockState);
    }
}
