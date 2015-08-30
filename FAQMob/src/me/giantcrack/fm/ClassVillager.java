package me.giantcrack.fm;



import net.minecraft.server.v1_8_R2.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftVillager;
import org.bukkit.entity.Villager;
import org.bukkit.event.entity.CreatureSpawnEvent;

/**
 * Created by shoot_000 on 5/31/2015.
 */
public class ClassVillager extends EntityVillager {

    public ClassVillager(net.minecraft.server.v1_8_R2.World world) {
        super(world);
    }

    @Override
    public EntityAgeable createChild(EntityAgeable entityAgeable) {
        return null;
    }

    @Override
    public void g(double d0, double d1, double d2) {
        return;
    }

    @Override
    public void move(double d0, double d1, double d2) {
        return;
    }


    @Override
    public boolean damageEntity(DamageSource damagesource, float f) {
        return false;
    }

    public static Villager spawn(Location location, String name) {
        World mcWorld = (World) ((CraftWorld) location.getWorld()).getHandle();
        final ClassVillager customEntity = new ClassVillager(
                mcWorld);
        customEntity.setLocation(location.getX(), location.getY(),
                location.getZ(), location.getYaw(), location.getPitch());
        ((CraftLivingEntity) customEntity.getBukkitEntity())
                .setRemoveWhenFarAway(false);
        ((CraftLivingEntity) customEntity.getBukkitEntity())
                .setCustomNameVisible(true);
        ((CraftLivingEntity) customEntity.getBukkitEntity())
                .setCustomName(name);
        mcWorld.addEntity(customEntity, CreatureSpawnEvent.SpawnReason.CUSTOM);
        return (CraftVillager) customEntity.getBukkitEntity();
    }
}
