package com.pickaid.eidolonjs.recipes.component;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.*;
import dev.latvian.mods.kubejs.recipe.schema.DynamicRecipeComponent;
import elucent.eidolon.recipe.CrucibleRecipe;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

/**
 * @author skyraah
 */
public interface StepsComponent {
    RecipeComponentBuilder TEST = new RecipeComponentBuilder(2)
            .add(NumberComponent.INT.key("stirs").optional(1))
            .add(ItemComponents.INPUT_ARRAY.key("items"));
    RecipeKey<RecipeComponentBuilderMap[]> TEST_KEY = TEST.inputRole().asArray().key("steps");
}
