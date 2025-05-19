package com.pickaid.eidolonjs.recipes.schema;

import dev.latvian.mods.kubejs.item.InputItem;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.ItemComponents;
import dev.latvian.mods.kubejs.recipe.component.MapRecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.StringComponent;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import dev.latvian.mods.kubejs.util.TinyMap;

/**
 * @author skyraah
 */
public interface WorktableSchema {
    RecipeKey<OutputItem> RESULT = ItemComponents.OUTPUT.key("result");
    RecipeKey<String[]> PATTERN = StringComponent.NON_EMPTY.asArray().key("pattern");
    RecipeKey<String> REAGENTS = StringComponent.ANY.key("reagents");
    RecipeKey<TinyMap<Character, InputItem>> KEY = MapRecipeComponent.ITEM_PATTERN_KEY.key("key");

    RecipeSchema SCHEMA = new RecipeSchema()
            .constructor(RESULT, PATTERN, REAGENTS, KEY)
            .constructor(REAGENTS, PATTERN, KEY);
}
