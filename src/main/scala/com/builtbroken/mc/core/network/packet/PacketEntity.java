package com.builtbroken.mc.core.network.packet;

import com.builtbroken.mc.core.network.IPacketIDReceiver;
import com.builtbroken.mc.core.network.IPacketReceiver;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

/**
 * @author tgame14
 * @since 26/05/14
 */
public class PacketEntity extends PacketType
{
    protected int entityId;

    public PacketEntity(Entity entity, Object... args)
    {
        super(args);
        this.entityId = entity.getEntityId();
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        buffer.writeInt(this.entityId);
        buffer.writeBytes(this.data());
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        this.entityId = buffer.readInt();
        this.data_$eq(buffer.slice());

    }

    @Override
    public void handleClientSide(EntityPlayer player)
    {
        handleServerSide(player);
    }

    @Override
    public void handleServerSide(EntityPlayer player)
    {
        Entity entity = player.getEntityWorld().getEntityByID(this.entityId);

        if (entity instanceof IPacketIDReceiver)
        {
            ((IPacketIDReceiver) entity).read(data(), data().readInt(), player, this);
        }
        else if (entity instanceof IPacketReceiver)
        {
            ((IPacketReceiver) entity).read(data(), player, this);
        }
    }
}
