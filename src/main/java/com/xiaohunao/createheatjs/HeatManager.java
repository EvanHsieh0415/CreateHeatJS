package com.xiaohunao.createheatjs;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.mojang.datafixers.util.Pair;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import dev.latvian.mods.kubejs.util.UtilsJS;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.compress.utils.Lists;

import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;

public class HeatManager {
    private static final Map<String,Pair<Integer,Integer>> heatLevels = Maps.newHashMap();
    private static final Map<String,Integer> heatLevelPrior = Maps.newHashMap();
    private static final Multimap<String, Pair<BlockState,Predicate<BlockState>>> HEAT_SOURCE = ArrayListMultimap.create();
    private static final Map<String,BlazeBurnerBlock.HeatLevel> HEAT_LEVELS = Maps.newHashMap();
    private static final Map<String,HeatCondition> HEAT_CONDITIONS = Maps.newHashMap();

    private static final HeatManager INSTANCE = new HeatManager();
    private HeatManager() {
    }
    public static HeatManager getInstance() {
        return INSTANCE;
    }

    public Map<String,Pair<Integer,Integer>> getHeatLevels() {
        return heatLevels;
    }
    public void registerHeatLevel(String heatLevel,int prior,int color) {
        heatLevels.put(heatLevel,new Pair<>(prior,color));
    }
    public void registerHeatSource(String heatLevel,String blockState,Predicate<BlockState> predicate) {
        HEAT_SOURCE.put(heatLevel,Pair.of(UtilsJS.parseBlockState(blockState),predicate));
    }
    public void registerHeatSource(String heatLevel,String blockState) {
        HEAT_SOURCE.put(heatLevel,Pair.of(UtilsJS.parseBlockState(blockState),blockStack -> true));
    }
    public Multimap<String, Pair<BlockState,Predicate<BlockState>>> getHeatSource() {
        return HEAT_SOURCE;
    }
    public Collection<Pair<BlockState,Predicate<BlockState>>> getHeatSource(String heatLevel){
        if (heatLevel.equals(HeatCondition.NONE.toString())){
            return HEAT_SOURCE.get(BlazeBurnerBlock.HeatLevel.NONE.toString());
        }
        if (heatLevel.equals(HeatCondition.HEATED.toString())){
            return HEAT_SOURCE.get(BlazeBurnerBlock.HeatLevel.KINDLED.toString());
        }
        if (heatLevel.equals(HeatCondition.SUPERHEATED.toString())){
            return HEAT_SOURCE.get(BlazeBurnerBlock.HeatLevel.SEETHING.toString());
        }
        Collection<Pair<BlockState, Predicate<BlockState>>> pairs = HEAT_SOURCE.get(heatLevel);
        if (pairs.isEmpty()){
            return Lists.newArrayList();
        }
        return pairs;
    }

    public void registerHeatLevel(String name,BlazeBurnerBlock.HeatLevel heatLevel) {
        HEAT_LEVELS.put(name,heatLevel);
    }
    public Map<String,BlazeBurnerBlock.HeatLevel> getHeatLevelsList() {
        return HEAT_LEVELS;
    }
    public BlazeBurnerBlock.HeatLevel getHeatLevel(String name) {
        return HEAT_LEVELS.get(name);
    }
    public void registerHeatCondition(String name,HeatCondition heatCondition) {
        HEAT_CONDITIONS.put(name,heatCondition);
    }
    public Map<String,HeatCondition> getHeatConditions() {
        return HEAT_CONDITIONS;
    }
    public HeatCondition getHeatCondition(String name) {
        return HEAT_CONDITIONS.get(name);
    }
    public void registerHeatLevelPrior(String heatLevel,int prior) {
        heatLevelPrior.put(heatLevel,prior);
    }
    public Integer getHeatLevelPrior(String heatLevel) {
        if (heatLevel.equals(HeatCondition.NONE.toString())){
            return heatLevelPrior.get(BlazeBurnerBlock.HeatLevel.NONE.toString());
        }
        if (heatLevel.equals(HeatCondition.HEATED.toString())){
            return heatLevelPrior.get(BlazeBurnerBlock.HeatLevel.KINDLED.toString());
        }
        if (heatLevel.equals(HeatCondition.SUPERHEATED.toString())){
            return heatLevelPrior.get(BlazeBurnerBlock.HeatLevel.SEETHING.toString());
        }
        return heatLevelPrior.getOrDefault(heatLevel,0);
    }
    public Map<String,Integer> getHeatLevelPriors() {
        return heatLevelPrior;
    }
}
