package ihl.crop_harvestors;

import ic2.core.ContainerBase;
import ic2.core.slot.SlotInvSlot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;

public class BlowerContainer extends ContainerBase<BlowerTileEntity> {

    protected BlowerTileEntity tileEntity;
    public int lastStorage = -1;
    public int lastAirSpeed = -1;
    private final static int height=166;
    
    public BlowerContainer(EntityPlayer entityPlayer, BlowerTileEntity tileEntity1){
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
        
       this.addSlotToContainer(new SlotInvSlot(tileEntity1.dischargeSlot, 0, 26, 26));
       for(int row=0;row<=3;row++)
       {
            this.addSlotToContainer(new SlotInvSlot(tileEntity1.upgradeSlot, row, 152, 8+row*18));
       }
    }

    @Override
	public void detectAndSendChanges()
    {
        super.detectAndSendChanges();
        for (int i = 0; i < this.crafters.size(); ++i)
        {
            ICrafting icrafting = (ICrafting)this.crafters.get(i);

            if (this.tileEntity.getStored() != this.lastStorage)
            {
            	icrafting.sendProgressBarUpdate(this, 0, (this.tileEntity.getStored()>>15) & Short.MAX_VALUE);
            	icrafting.sendProgressBarUpdate(this, 1, (short)(this.tileEntity.getStored() & Short.MAX_VALUE));

            }
            
            if (this.tileEntity.airSpeedBase != this.lastAirSpeed)
            {
                icrafting.sendProgressBarUpdate(this, 2, this.tileEntity.airSpeedBase);
            }
        }

        this.lastStorage = this.tileEntity.getStored();
        this.lastAirSpeed = this.tileEntity.airSpeedBase;
    }
    
    @Override
	public void updateProgressBar(int index, int value)
    {
        super.updateProgressBar(index, value);

        switch (index)
        {
        case 0:
            this.tileEntity.setStored(value<<15);
            break;
        case 1:
            this.tileEntity.setStored(this.tileEntity.getStored()+value);
            break;
        case 2:
            this.tileEntity.airSpeedBase=value;
            break;
        }
    }
    
	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return tileEntity.isUseableByPlayer(var1);
	}
}
