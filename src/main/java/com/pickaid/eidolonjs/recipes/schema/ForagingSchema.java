package com.pickaid.eidolonjs.recipes.schema;

import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.BlockComponent;
import dev.latvian.mods.kubejs.recipe.component.ItemComponents;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

public interface ForagingSchema {
    RecipeKey<OutputItem> RESULT = ItemComponents.OUTPUT.key("result");
//    RecipeKey<InputItem> INPUT = BlockComponent.INPUT
    RecipeKey<InputItem[]> INPUT = ItemComponents.INPUT.asArray().key("block");

    RecipeSchema SCHEMA = new RecipeSchema(RESULT, INPUT);
}

