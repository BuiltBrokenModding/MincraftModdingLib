package com.builtbroken.mc.client.json;

import com.builtbroken.mc.client.json.audio.AudioData;
import com.builtbroken.mc.client.json.imp.IEffectData;
import com.builtbroken.mc.client.json.models.ModelCustomData;
import com.builtbroken.mc.client.json.render.RenderData;
import com.builtbroken.mc.client.json.models.cube.BlockModelData;
import com.builtbroken.mc.client.json.texture.TextureData;
import com.builtbroken.mc.core.Engine;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;

import java.awt.*;
import java.util.HashMap;

/**
 * Handles all data for client side JSON
 *
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 11/22/2016.
 */
public class ClientDataHandler
{
    /** Model key to model data */
    public HashMap<String, ModelCustomData> models = new HashMap();
    /** Model key to block model data (block models are just a series of cubes) */
    public HashMap<String, BlockModelData> blockModels = new HashMap();
    /** Texture key to texture data */
    public HashMap<String, TextureData> textures = new HashMap();
    /** Render key to render data */
    public HashMap<String, RenderData> renderData = new HashMap();
    /** Audio key to audio data */
    public HashMap<String, AudioData> audioData = new HashMap();
    /** Effect key to effect data */
    public HashMap<String, IEffectData> effectData = new HashMap();
    /** Block renders to be attached to blocks */
    public HashMap<String, ISimpleBlockRenderingHandler> blockRenders = new HashMap();

    /** Global client data handler for Voltz Engine */
    public static final ClientDataHandler INSTANCE = new ClientDataHandler();

    public void addTexture(String key, TextureData texture)
    {
        if (textures.containsKey(key.toLowerCase()))
        {
            Engine.logger().error("Overriding " + textures.get(key) + " with " + texture);
        }
        textures.put(key.toLowerCase(), texture);
    }

    public void addModel(String key, ModelCustomData model)
    {
        if (models.containsKey(key.toLowerCase()))
        {
            Engine.logger().error("Overriding " + models.get(key) + " with " + model);
        }
        models.put(key.toLowerCase(), model);
    }

    public void addBlockModel(String key, BlockModelData model)
    {
        if (blockModels.containsKey(key.toLowerCase()))
        {
            Engine.logger().error("Overriding " + blockModels.get(key) + " with " + model);
        }
        blockModels.put(key.toLowerCase(), model);
    }

    public void addRenderData(String key, RenderData data)
    {
        if (renderData.containsKey(key.toLowerCase()))
        {
            Engine.logger().error("Overriding " + renderData.get(key) + " with " + data);
        }
        renderData.put(key.toLowerCase(), data);
    }

    public void addAudio(String key, AudioData data)
    {
        if (audioData.containsKey(key.toLowerCase()))
        {
            Engine.logger().error("Overriding " + audioData.get(key) + " with " + data);
        }
        audioData.put(key.toLowerCase(), data);
    }


    public void addEffect(String key, IEffectData data)
    {
        if (effectData.containsKey(key.toLowerCase()))
        {
            Engine.logger().error("Overriding " + effectData.get(key) + " with " + data);
        }
        effectData.put(key.toLowerCase(), data);
    }

    public void addBlockRenderer(String key, ISimpleBlockRenderingHandler renderer)
    {
        if (blockRenders.containsKey(key.toLowerCase()))
        {
            Engine.logger().error("Overriding " + blockRenders.get(key) + " with " + renderer);
        }
        blockRenders.put(key.toLowerCase(), renderer);
    }

    public RenderData getRenderData(String key)
    {
        if (key == null || key.isEmpty())
        {
            return null;
        }
        return renderData.get(key.toLowerCase());
    }

    public ModelCustomData getModel(String key)
    {
        if (key == null || key.isEmpty())
        {
            return null;
        }
        return models.get(key.toLowerCase());
    }

    public BlockModelData getBlockModel(String key)
    {
        if (key == null || key.isEmpty())
        {
            return null;
        }
        return blockModels.get(key.toLowerCase());
    }

    public TextureData getTexture(String key)
    {
        if (key == null || key.isEmpty())
        {
            return null;
        }
        return textures.get(key.toLowerCase());
    }

    public AudioData getAudio(String key)
    {
        if (key == null || key.isEmpty())
        {
            return null;
        }
        return audioData.get(key.toLowerCase());
    }

    public IEffectData getEffect(String key)
    {
        if (key == null || key.isEmpty())
        {
            return null;
        }
        return effectData.get(key.toLowerCase());
    }

    public ISimpleBlockRenderingHandler getBlockRender(String key)
    {
        if (key == null || key.isEmpty())
        {
            return null;
        }
        return blockRenders.get(key.toLowerCase());
    }

    @SubscribeEvent
    public void textureEvent(TextureStitchEvent.Pre event)
    {
        /** 0 = terrain.png, 1 = items.png */
        final int textureType = event.map.getTextureType();
        for (TextureData data : textures.values())
        {
            if (textureType == 0 && data.type == TextureData.Type.BLOCK)
            {
                data.register(event.map);
            }
            else if (textureType == 1 && data.type == TextureData.Type.ITEM)
            {
                data.register(event.map);
            }
        }
    }

    public boolean canSupportColor(String colorKey)
    {
        return false;
    }

    public int getColorAsInt(String colorKey)
    {
        return 16777215;
    }

    public Color getColor(String colorKey)
    {
        return null;
    }
}
