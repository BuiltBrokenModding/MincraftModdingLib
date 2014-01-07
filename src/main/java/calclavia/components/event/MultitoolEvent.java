package calclavia.components.event;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.Cancelable;
import net.minecraftforge.event.Event.HasResult;
import net.minecraftforge.event.world.BlockEvent;

/**
 * @author Calclavia
 * 
 */
@Cancelable
@HasResult
public class MultitoolEvent extends BlockEvent
{
	public final int side;
	public final float hitX, hitY, hitZ;
	public final ItemStack toolStack;
	public final EntityPlayer player;

	public MultitoolEvent(World world, ItemStack wrench, EntityPlayer player, int x, int y, int z, int side, float hitX, float hitY, float hitZ, Block block, int blockMetadata)
	{
		super(x, y, z, world, block, blockMetadata);
		this.toolStack = wrench;
		this.player = player;
		this.side = side;
		this.hitX = hitX;
		this.hitY = hitY;
		this.hitZ = hitZ;
	}

}
