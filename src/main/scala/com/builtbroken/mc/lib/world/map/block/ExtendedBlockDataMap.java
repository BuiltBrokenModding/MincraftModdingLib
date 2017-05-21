package com.builtbroken.mc.lib.world.map.block;


import com.builtbroken.mc.lib.world.map.data.ChunkMap;
import com.builtbroken.mc.lib.world.map.data.s.ChunkDataShort;

/**
 * Tracks radiation values for the world
 *
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 5/20/2017.
 */
public class ExtendedBlockDataMap extends ChunkMap<ChunkDataShort>
{
    public ExtendedBlockDataMap(int dimID)
    {
        super(dimID);
    }

    /**
     * Gets the stored value at the location in the world
     *
     * @param x - location in the world
     * @param y - location in the world
     * @param z - location in the world
     * @return value shorted, do not assume 0 means a stored value
     */
    public short getValue(int x, int y, int z)
    {
        ChunkDataShort chunk = getChunkFromBlockCoords(x, z);
        if (chunk != null)
        {
            return chunk.getValue(x & 15, y, z & 15);
        }
        return 0;
    }

    /**
     * Sets the extended data value
     *
     * @param x     - location in the world
     * @param y     - location in the world
     * @param z     - location in the world
     * @param value - new data
     * @return old data
     */
    public short setValue(int x, int y, int z, short value)
    {
        ChunkDataShort chunk = getChunkFromBlockCoords(x, z);
        if (chunk != null)
        {
            short prev = chunk.getValue(x & 15, y, z & 15);
            chunk.setValue(x & 15, y, z & 15, value);
            return prev;
        }
        else
        {
            chunk = add(new ChunkDataShort(x >> 4, z >> 4));
            chunk.setValue(x & 15, y, z & 15, value);
        }
        return 0;
    }

    @Override
    public String toString()
    {
        return "ExtendedBlockDataMap[" + dimID + "]";
    }
}
