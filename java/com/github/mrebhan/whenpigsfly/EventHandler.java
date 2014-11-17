package com.github.mrebhan.whenpigsfly;

import net.minecraft.client.Minecraft;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;

public class EventHandler {
	
//	@SubscribeEvent
//	public void onRenderTick(TickEvent.RenderTickEvent e) {
//		//		if (e.phase == Phase.END) {
//		Minecraft mc = Minecraft.getMinecraft();
//		if (mc.currentScreen == null) {                
//			GuiIngame gig = new GuiIngame(mc);
//			if (mc.thePlayer.ridingEntity != null && mc.thePlayer.ridingEntity instanceof EntityHoverBoat) {
//				EntityHoverBoat hoverBoat = (EntityHoverBoat) mc.thePlayer.ridingEntity;
//				mc.fontRenderer.drawStringWithShadow(((Boolean) hoverBoat.playerascend).toString(), 1, 1, -1);
//				mc.fontRenderer.drawStringWithShadow(((Boolean) hoverBoat.playerdescend).toString(), 1, 10, -1);
//			}
//		}
//	}
	
	@SubscribeEvent
	public void onKeyPress(KeyInputEvent e) {
		if (Minecraft.getMinecraft().theWorld != null) {
			WhenPigsFly.instance.wrapper.sendToServer(new PacketKeys(Keybindings.instance().ascend.getIsKeyPressed(), Keybindings.instance().descend.getIsKeyPressed()));
		}
	}
}
