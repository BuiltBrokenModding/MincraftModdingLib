package com.builtbroken.mc.lib.json.conversion.data;

import com.builtbroken.mc.api.data.energy.IEnergyBufferData;
import com.builtbroken.mc.lib.json.conversion.JsonConverter;
import com.builtbroken.mc.lib.json.processors.JsonProcessor;
import com.builtbroken.mc.prefab.energy.EnergyBufferData;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * @see <a href="https://github.com/BuiltBrokenModding/VoltzEngine/blob/development/license.md">License</a> for what you can and can't do with the code.
 * Created by Dark(DarkGuardsman, Robert) on 8/7/2017.
 */
public class JsonConverterEnergyBufferData extends JsonConverter<IEnergyBufferData>
{
    public JsonConverterEnergyBufferData()
    {
        super("IEnergyBufferData");
    }

    @Override
    public IEnergyBufferData convert(JsonElement element)
    {
        return fromJson(element);
    }

    public static IEnergyBufferData fromJson(JsonElement element)
    {
        if (element instanceof JsonObject)
        {
            return fromJsonObject((JsonObject) element);
        }
        return null;
    }

    public static IEnergyBufferData fromJsonObject(JsonObject object)
    {
        JsonProcessor.ensureValuesExist(object, "power");
        int in = object.getAsJsonPrimitive("power").getAsInt();
        return new EnergyBufferData().setEnergyCapacity(in);
    }
}
