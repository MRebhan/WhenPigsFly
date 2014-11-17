package com.github.mrebhan.whenpigsfly.client;

import com.github.mrebhan.whenpigsfly.CommonProxy;
import com.github.mrebhan.whenpigsfly.entity.EntityEnderPig;
import com.github.mrebhan.whenpigsfly.render.RenderEnderPig;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(EntityEnderPig.class, new RenderEnderPig());
	}
}
