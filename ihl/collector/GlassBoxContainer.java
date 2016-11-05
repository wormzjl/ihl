package ihl.collector;

import ic2.core.ContainerBase;
import ic2.core.slot.SlotInvSlot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

public class GlassBoxContainer extends ContainerBase {

    protected GlassBoxTileEntity tileEntity;
    public int lastStorage = -1;
    private final static int height=166;
    
    public GlassBoxContainer(EntityPlayer entityPlayer, GlassBoxTileEntity tileEntity1){
        super(tileEntity1);
        this.tileEntity = tileEntity1;
        int col;

        for (col = 0; col < 3; ++col)
        {
            for (int col1 = 0; col1 < 9; ++col1)
            {
                this.addSlotToContainer(new Slot(entityPlayer.inventory, col1 + col * 9 + 9, 8 + col1 * 18, height + -82 + col * 18));
            }
        }

        for (col = 0; col < 9; ++col)
        {
            this.addSlotToContainer(new Slot(entityPlayer.inventory, col, 8 + col * 18, height + -24));
        }
        
            for(col=0;col<=3;col++)
            {
                for(int row=0;row<=3;row++)
                {
                    this.addSlotToContainer(new SlotInvSlot(tileEntity1.invSlot, col+row*4, 55+17*col, 7+17*row));
                }
            }
    }

	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return tileEntity.isUseableByPlayer(var1);
	}
}