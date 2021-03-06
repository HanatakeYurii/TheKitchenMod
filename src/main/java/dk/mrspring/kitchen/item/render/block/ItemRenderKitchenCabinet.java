package dk.mrspring.kitchen.item.render.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dk.mrspring.kitchen.ClientProxy;
import dk.mrspring.kitchen.ModInfo;
import dk.mrspring.kitchen.model.ModelKitchenCabinet;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

/**
 * Created on 15-11-2015 for TheKitchenMod.
 */
@SideOnly(Side.CLIENT)
public class ItemRenderKitchenCabinet implements IItemRenderer
{
    ModelKitchenCabinet model = new ModelKitchenCabinet();
    ResourceLocation texture = new ResourceLocation(ModInfo.modid + ":textures/models/kitchen_cabinet.png");

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type)
    {
        return ClientProxy.clientConfig.kitchen_cabinet_rendering.handleType(type);
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
    {
        switch (helper)
        {
            case BLOCK_3D:
                return type != ItemRenderType.ENTITY;
            case INVENTORY_BLOCK:
                return type == ItemRenderType.INVENTORY;
            case ENTITY_BOBBING:
            case ENTITY_ROTATION:
                return type == ItemRenderType.ENTITY;
            default:
                return false;
        }
    }

    @Override
    public void renderItem(ItemRenderType renderType, ItemStack item, Object... data)
    {
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        GL11.glPushMatrix();
        switch (renderType)
        {
            case EQUIPPED_FIRST_PERSON:
                GL11.glRotatef(25, 0F, 0F, 1F);
                GL11.glTranslatef(.5F, .66F, .13F);
                GL11.glRotatef(40, 0, 1, 0);
                float scale = 0.8F;
                GL11.glScalef(scale, scale, scale);
                GL11.glTranslatef(.5F, -.2F, .5F);
                break;
            case EQUIPPED:
                GL11.glRotatef(25F, 0F, 0F, 1F);
                GL11.glRotatef(40, 0, 1, 0);
                GL11.glTranslatef(0.6F, 0.55F, 0.6F);
                scale = 0.7F;
                GL11.glScalef(scale, scale, scale);
                break;
            case INVENTORY:
                GL11.glRotatef(180, 0, 1, 0);
                scale = 1F;
                GL11.glScalef(scale, scale, scale);
                GL11.glTranslatef(0.0F, 1F, 0F);
                break;
            case ENTITY:
                scale = 0.5F;
                GL11.glScalef(scale, scale, scale);
                GL11.glTranslatef(0F, 1.3F, 0F);
                GL11.glTranslatef(0F, 0F, -0.1F);
                break;
        }

        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        this.model.render(null, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F, false);
        GL11.glPopMatrix();
    }
}
