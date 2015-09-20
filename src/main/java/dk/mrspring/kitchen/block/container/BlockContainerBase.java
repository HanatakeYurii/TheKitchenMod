package dk.mrspring.kitchen.block.container;

import dk.mrspring.kitchen.Kitchen;
import dk.mrspring.kitchen.KitchenItems;
import dk.mrspring.kitchen.ModInfo;
import dk.mrspring.kitchen.tileentity.TileEntityTimeable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by MrSpring on 29-09-2014 for TheKitchenMod.
 */
public class BlockContainerBase extends BlockContainer
{
    Class tileEntityClass;

    protected BlockContainerBase(Material material, String name, String textureName, boolean useCreativeTab, Class tileEntityClass)
    {
        super(material);

        this.setBlockName(name);
        this.setBlockTextureName(textureName);

        this.setHardness(4.0F);

        this.tileEntityClass = tileEntityClass;

        if (useCreativeTab)
            this.setCreativeTab(Kitchen.instance.tab);
    }

    protected BlockContainerBase(Material material, String name, boolean useCreativeTab, Class<? extends TileEntity> tileEntityClass)
    {
        this(material, name, ModInfo.modid + ":" + name, useCreativeTab, tileEntityClass);
    }

    protected BlockContainerBase(Material material, String name, Class<? extends TileEntity> tileEntityClass)
    {
        this(material, name, true, tileEntityClass);
    }

    protected BlockContainerBase(String name, String textureName, boolean useCreativeTab, Class<? extends TileEntity> tileEntityClass)
    {
        this(Material.iron, name, textureName, useCreativeTab, tileEntityClass);
    }

    protected BlockContainerBase(String name, boolean useCreativeTab, Class<? extends TileEntity> tileEntityClass)
    {
        this(name, ModInfo.modid + ":" + name, useCreativeTab, tileEntityClass);
    }

    protected BlockContainerBase(Material material, String name, String textureName, Class<? extends TileEntity> tileEntityClass)
    {
        this(material, name, textureName, true, tileEntityClass);
    }

    protected BlockContainerBase(String name, String textureName, Class<? extends TileEntity> tileEntityClass)
    {
        this(name, textureName, true, tileEntityClass);
    }

    protected BlockContainerBase(String name, Class<? extends TileEntity> tileEntityClass)
    {
        this(name, true, tileEntityClass);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer activator, int side, float clickX, float clickY, float clickZ)
    {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity instanceof TileEntityTimeable && !world.isRemote)
            if (!activator.isSneaking())
                if (activator.getCurrentEquippedItem() != null)
                    if (activator.getCurrentEquippedItem().getItem() == KitchenItems.timer && !((TileEntityTimeable) tileEntity).getHasTimer())
                    {
                        ((TileEntityTimeable) tileEntity).setHasTimer(true);
                        activator.getCurrentEquippedItem().stackSize--;
                        world.markBlockForUpdate(x, y, z);
                        return true;
                    }
        return onRightClicked(world, x, y, z, activator, side, clickX, clickY, clickZ);
    }

    public boolean onRightClicked(World world, int x, int y, int z, EntityPlayer clicker, int side, float clickX, float clickY, float clickZ)
    {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        try
        {
            return (TileEntity) tileEntityClass.newInstance();
        } catch (InstantiationException e)
        {
            e.printStackTrace();
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public Class getTileEntityClass()
    {
        return tileEntityClass;
    }

    // TODO: Add spawnBreakDrops(EntityPlayer, World, x, y, z), call from ModEventHandler#onBlockBreak

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int p_149749_6_)
    {
        TileEntity tileEntity = world.getTileEntity(x, y, z);

        if (tileEntity != null)
            if (tileEntity instanceof TileEntityTimeable)
                if (((TileEntityTimeable) tileEntity).getHasTimer())
                {
                    Random random = new Random();

                    float xRandPos = random.nextFloat() * 0.8F + 0.1F;
                    float zRandPos = random.nextFloat() * 0.8F + 0.1F;

                    EntityItem entityItem = new EntityItem(world, x + xRandPos, y + 1, z + zRandPos, new ItemStack(KitchenItems.timer));

                    entityItem.motionX = random.nextGaussian() * 0.005F;
                    entityItem.motionY = random.nextGaussian() * 0.005F + 0.2F;
                    entityItem.motionZ = random.nextGaussian() * 0.005F;

                    world.spawnEntityInWorld(entityItem);
                }

        super.breakBlock(world, x, y, z, block, p_149749_6_);
    }
}
