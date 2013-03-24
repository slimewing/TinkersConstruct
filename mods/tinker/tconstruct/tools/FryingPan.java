package mods.tinker.tconstruct.tools;

import mods.tinker.tconstruct.TContent;
import mods.tinker.tconstruct.library.AbilityHelper;
import mods.tinker.tconstruct.library.Weapon;
import mods.tinker.tconstruct.logic.EquipLogic;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class FryingPan extends Weapon
{
	public FryingPan(int itemID)
	{
		super(itemID, 2);
		this.setUnlocalizedName("InfiTool.FryingPan");
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLiving mob, EntityLiving player)
	{
		//AbilityHelper.hitEntity(stack, mob, player, damageVsEntity);
		AbilityHelper.knockbackEntity(mob, 1.7f);
		mob.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 100, 0)); //5 seconds of stun
		//Play "thunk" sfx
		return true;
	}
	
	public String getToolName()
	{
		return "Frying Pan";
	}
	
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float clickX, float clickY, float clickZ)
    {
        if (side == 0 || !player.isSneaking())
        {
            return false;
        }
        else if (!world.getBlockMaterial(x, y, z).isSolid())
        {
            return false;
        }
        else
        {
            if (side == 1)
            {
                ++y;
            }

            if (side == 2)
            {
                --z;
            }

            if (side == 3)
            {
                ++z;
            }

            if (side == 4)
            {
                --x;
            }

            if (side == 5)
            {
                ++x;
            }

            if (!player.canPlayerEdit(x, y, z, side, stack))
            {
                return false;
            }
            else if (!TContent.heldItemBlock.canPlaceBlockAt(world, x, y, z))
            {
                return false;
            }
            else
            {
                world.setBlock(x, y, z, TContent.heldItemBlock.blockID, 0, 3);

                EquipLogic logic = (EquipLogic) world.getBlockTileEntity(x, y, z);
    			logic.setEquipmentItem(stack);
                --stack.stackSize;

                return true;
            }
        }
    }

	@Override
	protected Item getHeadItem ()
	{
		return TContent.frypanHead;
	}

	@Override
	protected Item getAccessoryItem ()
	{
		return null;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public int getRenderPasses (int metadata)
	{
		return 5;
	}
	
	@Override
	public int getPartAmount()
	{
		return 2;
	}
	
	@Override
	public void registerPartPaths (int index, String[] location)
	{
		headStrings.put(index, location[0]);
		brokenHeadStrings.put(index, location[1]);
		handleStrings.put(index, location[2]);
	}
	
	@Override
	public String getIconSuffix (int partType)
	{
		switch (partType)
		{
		case 0:
			return "_frypan_head";
		case 1:
			return "_frypan_head_broken";
		case 2:
			return "_frypan_handle";
		default:
			return "";
		}
	}

	@Override
	public String getEffectSuffix ()
	{
		return "_frypan_effect";
	}

	@Override
	public String getDefaultFolder ()
	{
		return "frypan";
	}
}
