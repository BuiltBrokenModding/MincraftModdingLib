package com.builtbroken.mc.core.content.tool;

import com.builtbroken.mc.core.Engine;
import com.builtbroken.mc.core.References;
import com.builtbroken.mc.core.content.resources.items.ItemSheetMetal;
import com.builtbroken.mc.core.registry.implement.IPostInit;
import com.builtbroken.mc.core.registry.implement.IRegistryInit;
import com.builtbroken.mc.lib.helper.recipe.UniversalRecipe;
import com.builtbroken.mc.prefab.recipe.item.sheetmetal.RecipeSheetMetal;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;

import java.util.List;

/**
 * Basic tool used in hand crafting recipes for sheet metal
 * Created by Dark on 8/25/2015.
 */
public class ItemSheetMetalTools extends Item implements IPostInit, IRegistryInit
{
    public static boolean ENABLE_TOOL_DAMAGE = true;
    public static int MAX_SHEARS_DAMAGE = 600;
    public static int MAX_HAMMER_DAMAGE = 800;

    @SideOnly(Side.CLIENT)
    IIcon hammer;

    @SideOnly(Side.CLIENT)
    IIcon shears;

    public ItemSheetMetalTools()
    {
        this.setMaxStackSize(1);
        this.setUnlocalizedName(References.PREFIX + "sheetMetalTools");
    }

    @Override
    public void onPostInit()
    {

        if (Engine.itemSheetMetal != null)
        {
            //Plate creation
            GameRegistry.addRecipe(new RecipeSheetMetal(ItemSheetMetal.SheetMetal.FULL.stack(), "IH", 'I', UniversalRecipe.PRIMARY_METAL.get(), 'H', getHammer()));
            GameRegistry.addRecipe(new RecipeSheetMetal(ItemSheetMetal.SheetMetal.RIVETS.stack(16), "H","I", 'I', UniversalRecipe.PRIMARY_METAL.get(), 'H', getHammer()));

            //Sheet metal reduction recipes
            GameRegistry.addRecipe(new RecipeSheetMetal(ItemSheetMetal.SheetMetal.HALF.stack(2), "IC", 'I', ItemSheetMetal.SheetMetal.FULL.stack(), 'C', getShears()));
            GameRegistry.addRecipe(new RecipeSheetMetal(ItemSheetMetal.SheetMetal.QUARTER.stack(2), "IC", 'I', ItemSheetMetal.SheetMetal.HALF.stack(), 'C', getShears()));
            GameRegistry.addRecipe(new RecipeSheetMetal(ItemSheetMetal.SheetMetal.EIGHTH.stack(2), "IC", 'I', ItemSheetMetal.SheetMetal.QUARTER.stack(), 'C', getShears()));
            GameRegistry.addRecipe(new RecipeSheetMetal(ItemSheetMetal.SheetMetal.THIRD.stack(3), "I", "C", 'I', ItemSheetMetal.SheetMetal.FULL.stack(), 'C', getShears()));

            //Cone creations
            GameRegistry.addRecipe(new RecipeSheetMetal(ItemSheetMetal.SheetMetal.TRIANGLE.stack(2), "I ", " C", 'I', ItemSheetMetal.SheetMetal.FULL.stack(), 'C', getShears()));
            GameRegistry.addRecipe(new RecipeSheetMetal(ItemSheetMetal.SheetMetal.CONE.stack(), "I ", " H", 'I', ItemSheetMetal.SheetMetal.CONE.stack(), 'H', getHammer()));

            //Curved plates
            GameRegistry.addRecipe(new RecipeSheetMetal(ItemSheetMetal.SheetMetal.CURVED_1.stack(), "IH", 'I', ItemSheetMetal.SheetMetal.FULL.stack(), 'H', getHammer()));
            GameRegistry.addRecipe(new RecipeSheetMetal(ItemSheetMetal.SheetMetal.CURVED_2.stack(), "IH", 'I', ItemSheetMetal.SheetMetal.CURVED_1.stack(), 'H', getHammer()));
            GameRegistry.addRecipe(new RecipeSheetMetal(ItemSheetMetal.SheetMetal.CURVED_3.stack(), "IH", 'I', ItemSheetMetal.SheetMetal.CURVED_2.stack(), 'H', getHammer()));
            GameRegistry.addRecipe(new RecipeSheetMetal(ItemSheetMetal.SheetMetal.CURVED_4.stack(), "IH", 'I', ItemSheetMetal.SheetMetal.CURVED_3.stack(), 'H', getHammer()));

            //Cylinders
            GameRegistry.addRecipe(new RecipeSheetMetal(ItemSheetMetal.SheetMetal.SMALL_CYLINDER.stack(), "IRH", 'I', ItemSheetMetal.SheetMetal.CURVED_4.stack(), 'H', getHammer(), 'R', ItemSheetMetal.SheetMetal.RIVETS.stack()));
            GameRegistry.addRecipe(new RecipeSheetMetal(ItemSheetMetal.SheetMetal.HALF_CYLINDER.stack(), "IRI", 'I', ItemSheetMetal.SheetMetal.CURVED_4.stack(), 'R', ItemSheetMetal.SheetMetal.RIVETS.stack()));
            GameRegistry.addRecipe(new RecipeSheetMetal(ItemSheetMetal.SheetMetal.CYLINDER.stack(), "IRI", 'I', ItemSheetMetal.SheetMetal.HALF_CYLINDER.stack(), 'R', ItemSheetMetal.SheetMetal.RIVETS.stack()));
        }

        GameRegistry.addRecipe(new RecipeSheetMetal(getHammer(), "III", " I ", " S ", 'I', UniversalRecipe.PRIMARY_METAL.get(), 'S', Items.stick));
        GameRegistry.addRecipe(new RecipeSheetMetal(getShears(), "I I", " I ", "S S", 'I', UniversalRecipe.PRIMARY_METAL.get(), 'S', Items.stick));
    }

    @Override
    public void onRegistered()
    {
        this.ENABLE_TOOL_DAMAGE = Engine.instance.getConfig().getBoolean("EnableToolDamage", "SheetMetalContent", true, "Enables tools taking damage in crafting recipes");
        this.MAX_HAMMER_DAMAGE = Engine.instance.getConfig().getInt("MaxHammerDamage", "SheetMetalContent", MAX_HAMMER_DAMAGE, 10, 10000, "Max damage the sheet metal hammer can take before breaking");
        this.MAX_SHEARS_DAMAGE = Engine.instance.getConfig().getInt("MaxShearsDamage", "SheetMetalContent", MAX_SHEARS_DAMAGE, 10, 10000, "Max damage the sheet metal shears can take before breaking");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onClientRegistered()
    {
    }

    @Override
    public int getMaxDamage(ItemStack stack)
    {
        String type = getType(stack);
        if ("hammer".equals(type))
        {
            return MAX_HAMMER_DAMAGE;
        } else if ("shears".equals(type))
        {
            return MAX_SHEARS_DAMAGE;
        }
        return 100;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg)
    {
        this.hammer = reg.registerIcon(References.PREFIX + "sheetMetalHammer");
        this.shears = reg.registerIcon(References.PREFIX + "sheetMetalShears");
    }

    public String getUnlocalizedName(ItemStack stack)
    {
        String type = getType(stack);
        if (type != null && !type.isEmpty())
        {
            return super.getUnlocalizedName() + "." + type;
        }
        return super.getUnlocalizedName();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
        list.add(getHammer());
        list.add(getShears());
    }

    public static ItemStack getHammer()
    {
        return getTool("hammer");
    }

    public static ItemStack getShears()
    {
        return getTool("shears");
    }

    public static ItemStack getTool(String type)
    {
        ItemStack stack = new ItemStack(Engine.itemSheetMetalTools);
        ((ItemSheetMetalTools) Engine.itemSheetMetalTools).setType(stack, type);
        return stack;
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass)
    {
        String type = getType(stack);
        if (type != null && !type.isEmpty())
        {
            switch (type)
            {
                case "shears":
                    return shears;
                case "hammer":
                    return hammer;
            }
        }
        return Items.stone_hoe.getIcon(stack, pass);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }

    @Override
    public int getRenderPasses(int metadata)
    {
        return 1;
    }

    public String getType(ItemStack stack)
    {
        return stack.getTagCompound() != null && stack.getTagCompound().hasKey("toolType") ? stack.getTagCompound().getString("toolType") : null;
    }

    public void setType(ItemStack stack, String type)
    {
        if (stack.getTagCompound() == null)
            stack.setTagCompound(new NBTTagCompound());

        stack.getTagCompound().setString("toolType", type);
    }

    @Override
    public boolean doesContainerItemLeaveCraftingGrid(ItemStack stack)
    {
        return !hasContainerItem(stack);
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack)
    {
        if (itemStack.getItemDamage() <= getMaxDamage(itemStack))
        {
            ItemStack stack = itemStack.copy();
            if (ENABLE_TOOL_DAMAGE)
                stack.setItemDamage(stack.getItemDamage() + 1);
            return stack;
        }
        return null;
    }

    @Override
    public boolean hasContainerItem(ItemStack stack)
    {
        return stack.getItemDamage() <= getMaxDamage(stack);
    }
}
