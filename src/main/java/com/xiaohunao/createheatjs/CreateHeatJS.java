package com.xiaohunao.createheatjs;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Axis;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.processing.recipe.HeatCondition;
import com.xiaohunao.createheatjs.event.registerHeatEvent;
import mezz.jei.api.gui.drawable.IDrawable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;

import java.util.Collection;
import java.util.function.Predicate;

import static com.xiaohunao.createheatjs.KubeJSCreatePlugin.REGISTRY_HEAT;

@Mod(CreateHeatJS.MOD_ID)
public class CreateHeatJS {
    public static final String MOD_ID = "create_heat_js";
    public static final HeatManager Manager = HeatManager.getInstance();
    public CreateHeatJS() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::onFMLCommonSetup);
        modEventBus.addListener(this::onRegister);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onRegister(RegisterEvent event) {
        REGISTRY_HEAT.post(new registerHeatEvent());
    }

    @SubscribeEvent
    public void onFMLCommonSetup(FMLCommonSetupEvent event) {
        HeatManager manager = CreateHeatJS.Manager;
        manager.registerHeatSource(BlazeBurnerBlock.HeatLevel.SEETHING.toString(),"create:blaze_burner");
        manager.registerHeatSource(BlazeBurnerBlock.HeatLevel.KINDLED.toString(),"create:blaze_burner");

    }
    public static boolean hetaSourceRender(GuiGraphics graphics, IDrawable drawable, HeatCondition requiredHeat) {
        HeatManager manager = CreateHeatJS.Manager;
        Collection<Pair<BlockState, Predicate<BlockState>>> heatSource = manager.getHeatSource(requiredHeat.toString());
        if (heatSource.isEmpty()) return false;


        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) {
            return false;
        }
        long dayTime = level.getDayTime();

        PoseStack pose = graphics.pose();
        pose.pushPose();
        pose.translate(drawable.getWidth() / 2 + 3, 55, 200);
        pose.mulPose(Axis.XP.rotationDegrees(-15.5f));
        pose.mulPose(Axis.YP.rotationDegrees(22.5f));
//        pose.mulPose(Axis.YP.rotationDegrees(180));
        int scale = 23;

        int itemIndexToShow = (int) ((dayTime / 25) % (heatSource.size()));
        Pair<BlockState, Predicate<BlockState>> pair = heatSource.toArray(new Pair[0])[itemIndexToShow];
        if (pair == null) {
            return false;
        }
        BlockState heatBlockState = pair.getFirst();
        if (heatBlockState == null || heatBlockState.getBlock() == AllBlocks.BLAZE_BURNER.get()){
            pose.popPose();
            return false;
        }
        AnimatedKinetics.defaultBlockElement(heatBlockState)
                .atLocal(1, 1.65, 1)
                .rotate(0, 180, 0)
                .scale(scale)
                .render(graphics);
        pose.popPose();
        return true;
    }
}
