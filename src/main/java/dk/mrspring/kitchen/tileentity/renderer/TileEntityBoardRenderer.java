package dk.mrspring.kitchen.tileentity.renderer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dk.mrspring.kitchen.item.render.SandwichRender;
import dk.mrspring.kitchen.tileentity.TileEntityBoard;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.nio.DoubleBuffer;
import java.util.List;

@SideOnly(Side.CLIENT)
public class TileEntityBoardRenderer extends TileEntitySpecialRenderer
{
    @Override
    public void renderTileEntityAt(TileEntity var1, double x, double y, double z, float var8)
    {
        GL11.glPushMatrix();

        TileEntityBoard tileEntity = (TileEntityBoard) var1;
        GL11.glTranslated(x, y + 1.56, z);
        int metadata = tileEntity.getBlockMetadata();
        double sandwichHeight = 0.0625;
        if (metadata == 0)
            GL11.glTranslated(.5, sandwichHeight, .5);
        else
        {
            GL11.glRotatef(90, 0, 1, 0);
            GL11.glTranslated(-.5, sandwichHeight, .5);
        }

        List<ItemStack> layers = tileEntity.getLayers();

        double pixel = 0.0418;

        DoubleBuffer buffer = BufferUtils.createDoubleBuffer(8).put(new double[]{1, 0, 0, 0.001 + (pixel * 3)});
        buffer.flip();
        GL11.glClipPlane(GL11.GL_CLIP_PLANE5, buffer);
        GL11.glEnable(GL11.GL_CLIP_PLANE5);

        SandwichRender.renderSandwich(layers, tileEntity.getSpecialInfo());
        GL11.glDisable(GL11.GL_CLIP_PLANE5);
        GL11.glPopMatrix();
    }
}
