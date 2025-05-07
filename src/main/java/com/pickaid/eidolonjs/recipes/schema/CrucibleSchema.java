package com.pickaid.eidolonjs.recipes.schema;

import com.pickaid.eidolonjs.recipes.component.ItemComponent;
import com.pickaid.eidolonjs.recipes.component.StepsComponent;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.ItemComponents;
import dev.latvian.mods.kubejs.recipe.component.NumberComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentBuilder;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentBuilderMap;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import elucent.eidolon.recipe.CrucibleRecipe;
import net.minecraft.world.item.Item;

import java.util.List;

/**
 * @author skyraah
 */
public interface CrucibleSchema {
    RecipeKey<OutputItem[]> OUTPUT = ItemComponents.OUTPUT_ARRAY.key("result");
//    RecipeKey<List<CrucibleRecipe.Step>> STEPS = StepsComponent.STEP.key("steps");

    RecipeComponentBuilder STEP_BUILDER = new RecipeComponentBuilder(2)
            .add(NumberComponent.INT.key("stirs").defaultOptional())
            .add(ItemComponents.INPUT_ARRAY.key("items"));
    RecipeKey<RecipeComponentBuilderMap[]> STEPS = STEP_BUILDER.inputRole().asArray().key("steps");
    RecipeSchema SCHEMA = new RecipeSchema(OUTPUT, STEPS);

}
