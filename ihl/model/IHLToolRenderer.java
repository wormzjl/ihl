package ihl.model;

import ihl.items_blocks.IHLTool;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;

public class IHLToolRenderer implements IItemRenderer
{
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		switch (type) {
			case ENTITY:
				return true;
			case EQUIPPED:
				return true;
			case EQUIPPED_FIRST_PERSON:
				return true;
			case INVENTORY:
				return true;
			default:
				return false;
		}
	}

	@Override
	public void renderItem(ItemRenderType irt, ItemStack stack, Object... arg2) 
	{
		if(irt.equals(ItemRenderType.EQUIPPED_FIRST_PERSON))
		{
			if(stack.getItemDamage()==IHLTool.Type.Thermometer.itemDamage && stack.stackTagCompound!=null)
			{
				if(stack.stackTagCompound.getByte("showTemperature")>0)
				{
					RenderGameOverlayEventHandler.instance.temperature=stack.stackTagCompound.getLong("temperature");
					RenderGameOverlayEventHandler.instance.displayCurrentGUITimer=2000;
			    }
				else
				{
					RenderGameOverlayEventHandler.instance.displayCurrentGUITimer=500;
				}
			}
		}
		IIcon icon = stack.getItem().getIcon(stack, 0);
        Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.locationItemsTexture);
		if(irt.equals(ItemRenderType.INVENTORY))
		{
	        GL11.glEnable(GL11.GL_BLEND);
	        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	        Tessellator tessellator = Tessellator.instance;
	        tessellator.startDrawingQuads();
	        tessellator.setNormal(0f, 0f, -1f);
	        double xStart=0d,yStart=0d;
	        double xEnd=16d,yEnd=16d;
	        double z = 0.001d;
            tessellator.addVertexWithUV(xStart, yEnd, z, icon.getMinU(), icon.getMaxV());
            tessellator.addVertexWithUV(xEnd, yEnd, z, icon.getMaxU(), icon.getMaxV());
            tessellator.addVertexWithUV(xEnd, yStart, z, icon.getMaxU(), icon.getMinV());
            tessellator.addVertexWithUV(xStart, yStart, z, icon.getMinU(), icon.getMinV());
            tessellator.draw();
			GL11.glDisable(GL11.GL_BLEND);
		}
		else
		{
	        ItemRenderer.renderItemIn2D(Tessellator.instance, icon.getMaxU(), icon.getMinV(), icon.getMinU(), icon.getMaxV(), icon.getIconWidth(), icon.getIconHeight(), 0.0625F);
		}
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack arg1, ItemRendererHelper arg2) 
	{
        return type.equals(ItemRenderType.ENTITY);
	}

}
