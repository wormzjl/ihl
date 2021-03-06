package ihl.processing.chemistry;

import cpw.mods.fml.relauncher.Side;

import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import ihl.utils.IHLRenderUtils;

@SideOnly(Side.CLIENT)
public class PaperMachineGui extends GuiContainer {
	private static final ResourceLocation background = new ResourceLocation("ihl", "textures/gui/GUIPaperMachine.png");
	private PaperMachineContainer container;
	private int mixerFrame=0;

    public PaperMachineGui (PaperMachineContainer container1) {
            //the container is instanciated and passed to the superclass for handling
            super(container1);
            this.container=container1;
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        int xOffset = (this.width - xSize) / 2;
        int yOffset = (this.height - ySize) / 2;
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.mc.renderEngine.bindTexture(background);
            int i1;
            if (this.container.tileEntity.getEnergy() > 0D)
            {
            	GL11.glEnable(GL11.GL_BLEND);
            	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                i1 = Math.min(this.container.tileEntity.getGUIEnergy(12),12);
                this.drawTexturedModalRect(12, 16 + 12 - i1, 179, 12 - i1, 14, i1 + 2);
            }
            if (this.container.tileEntity.progress > 0)
            {	    	
            	GL11.glEnable(GL11.GL_BLEND);
            	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            	i1 = Math.min(this.container.tileEntity.gaugeProgressScaled(27),27);
            	this.drawTexturedModalRect(38, 29, getFrameX(i1), getFrameY(i1),24,24);
            	if(mixerFrame++>4)
            	{
            		mixerFrame=0;
            	}
            }
            else
            {
            	mixerFrame=0;
            }
            this.drawTexturedModalRect(103, 52, 246, 226+6*mixerFrame,10,6);
            if (this.container.tileEntity.getTankAmount() > 0)
            {
            	IHLRenderUtils.instance.renderIHLFluidTank(this.container.tileEntity.getFluidTank(), 102, 28, 114, 59, zLevel, par1, par2, xOffset, yOffset);
            }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2,
                    int par3) {
            //draw your Gui here, only thing you need to change is the path
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.mc.renderEngine.bindTexture(background);
            int x = (width - xSize) / 2;
            int y = (height - ySize) / 2;
            this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
    }
    
    private int getFrameY(int number)
    {
    	return (number % 10) * 24 + 14;
    }
    
    private int getFrameX(int number)
    {
    	return (number / 10) * 24 + 176;
    }
}