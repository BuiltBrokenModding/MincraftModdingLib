package calclavia.lib.content.module;

import net.minecraft.item.ItemStack;
import universalelectricity.api.vector.Vector3;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileRender
{
	/**
	 * Render the static, unmoving faces of this part into the world renderer.
	 * The Tessellator is already drawing.
	 * 
	 * @param olm An optional light matrix to be used for rendering things with perfect MC blended
	 * lighting (eg microblocks). Only use this if you have to.
	 * @param pass The render pass, 1 or 0
	 * @return True if render was used.
	 */
	public boolean renderStatic(Vector3 position)
	{
		return false;
	}

	/**
	 * Render the dynamic, changing faces of this part and other gfx as in a TESR.
	 * The Tessellator will need to be started if it is to be used.
	 * 
	 * @param pos The position of this block space relative to the renderer, same as x, y, z passed
	 * to TESR.
	 * @param frame The partial interpolation frame value for animations between ticks
	 * @param pass The render pass, 1 or 0
	 */
	public void renderDynamic(Vector3 position, float frame)
	{
	}

	public void renderItem(ItemStack itemStack)
	{
	}
}
