package com.builtbroken.mc.framework.multiblock;

import com.builtbroken.mc.api.tile.multiblock.IMultiTile;
import com.builtbroken.mc.imp.transform.region.Cube;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by Dark on 7/4/2015.
 */
public class BlockMultiblock extends BlockContainer
{

    public BlockMultiblock()
    {
        super(Material.ROCK);
        this.setUnlocalizedName("veMultiBlock");
        this.setHardness(2f);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess world, BlockPos pos)
    {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileMulti)
        {
            Cube cube = ((TileMulti) tile).collisionBounds;
            if (cube != null)
            {
                cube.add(pos.getX(), pos.getY(), pos.getZ());
                return cube.toAABB();
            }
        }
        return super.getCollisionBoundingBox(blockState, world, pos);
    }

    @Override
    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor)
    {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileMulti)
        {
            ((TileMulti) tile).updateConnections();
        }
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        IMultiTile tile = getTile(world, pos);
        if (tile != null && tile.getHost() != null)
        {
            IBlockState block = ((TileEntity) tile.getHost()).getWorld().getBlockState(((TileEntity) tile.getHost()).getPos());
            return block.getBlock().getPickBlock(block, target, ((TileEntity) tile.getHost()).getWorld(), ((TileEntity) tile.getHost()).getPos(), player);
        }
        return null;
    }

    @Override
    public int quantityDropped(Random p_149745_1_)
    {
        return 0;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return null;
    }


    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
       //Nothing
    }

    @Override
    protected boolean canSilkHarvest()
    {
        return false;
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state)
    {
        IMultiTile tile = getTile(world, pos);
        if (tile != null && tile.getHost() != null)
        {
            tile.getHost().onMultiTileAdded(tile);
        }
        super.onBlockAdded(world, pos, state);
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        IMultiTile tile = getTile(world, pos);
        if (tile != null && tile.getHost() != null)
        {
            tile.getHost().onMultiTileBroken(tile, null, true);
        }
        super.breakBlock(world, pos, state);
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest)
    {
        IMultiTile tile = getTile(world, pos);
        if (tile != null && tile.getHost() != null)
        {
            tile.getHost().onMultiTileBroken(tile, player, willHarvest);
        }
        return removedByPlayer(state, world, pos, player, willHarvest);
    }

    @Override
    public void onBlockDestroyedByExplosion(World world, BlockPos pos, Explosion ex)
    {
        IMultiTile tile = getTile(world, pos);
        if (tile != null && tile.getHost() != null)
        {
            tile.getHost().onMultiTileBroken(tile, ex, true);
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        IMultiTile tile = getTile(world, pos);
        return tile != null && tile.getHost() != null && tile.getHost().onMultiTileActivated(tile, player, hand, side, hitX, hitY, hitZ);
    }

    @Override
    public void onBlockClicked(World world, BlockPos pos, EntityPlayer player)
    {
        IMultiTile tile = getTile(world, pos);
        if (tile != null && tile.getHost() != null)
        {
            tile.getHost().onMultiTileClicked(tile, player);
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return EnumMultiblock.provideTile(world, meta);
    }

    protected IMultiTile getTile(World world, BlockPos pos)
    {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof IMultiTile)
        {
            return (IMultiTile) tile;
        }
        return null;
    }
}
