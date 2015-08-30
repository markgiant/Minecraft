package me.giantcrack.bpvp;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.google.common.base.Objects;
import org.bukkit.entity.Player;
import org.spigotmc.SpigotConfig;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by shoot_000 on 4/29/2015.
 */
public class AbstractPacket {
    protected PacketContainer handle;

    protected AbstractPacket(PacketContainer handle, PacketType type)
    {
        if (handle == null) {
            throw new IllegalArgumentException("Packet handle cannot be NULL.");
        }
        if (!Objects.equal(handle.getType(), type)) {
            throw new IllegalArgumentException(
                    handle.getHandle() + " is not a packet of type " + type);
        }
        this.handle = handle;
    }

    public PacketContainer getHandle()
    {
        return this.handle;
    }

    public void sendPacket(Player receiver)
    {
        try
        {
            ProtocolLibrary.getProtocolManager().sendServerPacket(receiver, getHandle());
        }
        catch (InvocationTargetException e)
        {
            throw new RuntimeException("Cannot send packet.", e);
        }
    }

    public void recievePacket(Player sender)
    {
        try
        {
            ProtocolLibrary.getProtocolManager().recieveClientPacket(sender, getHandle());
        }
        catch (Exception e)
        {
            throw new RuntimeException("Cannot recieve packet.", e);
        }
    }

}
