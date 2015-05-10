package dk.mrspring.kitchen.recipe;

import cpw.mods.fml.common.registry.GameRegistry;
import dk.mrspring.kitchen.config.wrapper.JsonBasicRecipe;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by MrSpring on 15-12-2014 for TheKitchenMod.
 */
public class BasicRecipe
{
    ItemStack input, output;

    public BasicRecipe(Item input, Item output)
    {
        this(new ItemStack(input), new ItemStack(output));
    }

    public BasicRecipe(Item input, ItemStack output)
    {
        this(new ItemStack(input), output);
    }

    public BasicRecipe(ItemStack input, Item output)
    {
        this(input, new ItemStack(output));
    }

    public BasicRecipe(ItemStack input, ItemStack output)
    {
        this.input = input;
        this.output = output;
    }

    public BasicRecipe(String input, String output)
    {
        if (input.contains(":"))
        {
            String modId = input.split(":")[0];
            String itemName = input.split(":")[1];
            this.input = GameRegistry.findItemStack(modId, itemName, 1);
        }

        if (output.contains(":"))
        {
            String modId = output.split(":")[0];
            String itemName = output.split(":")[1];
            this.output = GameRegistry.findItemStack(modId, itemName, 1);
        }
    }

    public BasicRecipe(JsonBasicRecipe jsonRecipe)
    {
        this(jsonRecipe.getInput().toItemStack(), jsonRecipe.getOutput().toItemStack());
    }

    public ItemStack getInput()
    {
        return input;
    }

    public BasicRecipe setInput(ItemStack input)
    {
        this.input = input;
        return this;
    }

    public ItemStack getOutput()
    {
        return output;
    }

    public BasicRecipe setOutput(ItemStack output)
    {
        this.output = output;
        return this;
    }

    public boolean isValid()
    {
        return this.input != null && this.output != null;
    }
}
