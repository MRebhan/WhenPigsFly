package com.github.mrebhan.whenpigsfly;

import com.github.mrebhan.whenpigsfly.entity.EntityEnderPig;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;

public class PacketKeys implements IMessage {

	public boolean ascendPressed;
	public boolean descendPressed;

	public PacketKeys() {
		ascendPressed = false;
		descendPressed = false;
	}

	public PacketKeys(boolean par1, boolean par2) {
		this.ascendPressed = par1;
		this.descendPressed = par2;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		// TODO Auto-generated method stub
		this.ascendPressed = buf.readBoolean();
		this.descendPressed = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		// TODO Auto-generated method stub
		buf.writeBoolean(ascendPressed);
		buf.writeBoolean(descendPressed);
	}

	public static class Handler implements IMessageHandler<PacketKeys, PacketKeys> {

		@Override
		public PacketKeys onMessage(PacketKeys message, MessageContext ctx) {
			// TODO Auto-generated method stub
			if (ctx.side == Side.SERVER) {
//				System.out.printf("Recieved {%s,%s} from %s\n", message.ascendPressed, message.descendPressed, ctx.getServerHandler().playerEntity.getCommandSenderName());
				EntityPlayer player = ctx.getServerHandler().playerEntity;
				if (player.ridingEntity instanceof EntityEnderPig) {
					EntityEnderPig hoverBoat = (EntityEnderPig) player.ridingEntity;
					hoverBoat.playerascend = message.ascendPressed;
					hoverBoat.playerdescend = message.descendPressed;
				}
				return new PacketKeys(message.ascendPressed, message.descendPressed);
			}
			if (ctx.side == Side.CLIENT) {
//				System.out.printf("CLIENT recieved {%s,%s}\n", message.ascendPressed, message.descendPressed);
				EntityPlayer player = Minecraft.getMinecraft().thePlayer;
				if (player.ridingEntity instanceof EntityEnderPig) {
					EntityEnderPig hoverBoat = (EntityEnderPig) player.ridingEntity;
					hoverBoat.playerascend = message.ascendPressed;
					hoverBoat.playerdescend = message.descendPressed;
				}
			}
			return null;
		}

	}

}
