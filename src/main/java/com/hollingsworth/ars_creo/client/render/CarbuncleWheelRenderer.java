package com.hollingsworth.ars_creo.client.render;

import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class CarbuncleWheelRenderer extends GeoBlockRenderer {
    public CarbuncleWheelRenderer(TileEntityRendererDispatcher rendererDispatcherIn, AnimatedGeoModel modelProvider) {
        super(rendererDispatcherIn, modelProvider);
    }

}
