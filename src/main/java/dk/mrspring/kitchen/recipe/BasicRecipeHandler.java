package dk.mrspring.kitchen.recipe;

import dk.mrspring.kitchen.config.wrapper.JsonBasicRecipe;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrSpring on 15-12-2014 for TheKitchenMod.
 */
public class BasicRecipeHandler
{
    List<BasicRecipe> recipes = new ArrayList<BasicRecipe>();

    public void load()
    {

    }

    protected void addAll(JsonBasicRecipe[] array)
    {
        if (array != null)
        {
            BasicRecipe[] recipes = new BasicRecipe[array.length];
            for (int i = 0; i < array.length; i++)
            {
                JsonBasicRecipe recipe = array[i];
                recipes[i] = recipe.toBasicRecipe();
            }
            addAll(recipes);
        }
    }

    protected void addAll(BasicRecipe[] array)
    {
//        Collections.addAll(recipes, array);
        for (BasicRecipe recipe : array)
        {
            if (recipe.isValid())
                this.recipes.add(recipe);
        }
    }

    public List<BasicRecipe> getRecipes()
    {
        return recipes;
    }

    /**
     * @param input1 The ItemStack to get result of.
     * @return Returns the result of the item stack when cooked in the Oven. Returns null when nothing was found
     */
    public ItemStack getOutputFor(ItemStack input1)
    {
        for (BasicRecipe recipe : recipes)
        {
            ItemStack input2 = recipe.getInput();
            if (input1.isItemEqual(input2))
                return recipe.getOutput().copy();
        }

        return null;
    }

    public void addRecipe(ItemStack input, ItemStack output)
    {
        if (input != null && output != null)
        {
            recipes.add(new BasicRecipe(input, output));
        }
    }

    public boolean hasOutput(ItemStack toAdd)
    {
        return getOutputFor(toAdd) != null;
    }
}
