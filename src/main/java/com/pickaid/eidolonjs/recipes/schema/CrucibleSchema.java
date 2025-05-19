package com.pickaid.eidolonjs.recipes.schema;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.pickaid.eidolonjs.recipes.builder.StepBuilderJS;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeJS;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.*;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import elucent.eidolon.recipe.CrucibleRecipe;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

/**
 * @author skyraah
 */
@SuppressWarnings("unused")
public interface CrucibleSchema {
    @FunctionalInterface
    interface StepBuilderCallback {
        void apply(StepBuilderJS builder);
    }
    class CrucibleRecipeJS extends RecipeJS {
        public CrucibleRecipeJS steps(StepBuilderCallback callback) {
            var builder = new StepBuilderJS();
            callback.apply(builder);
            setValue(STEPS, builder.getStepList().toArray(CrucibleRecipe.Step[]::new));
            return this;
        }


    }

    RecipeKey<CrucibleRecipe.Step[]> STEPS = new RecipeComponent<CrucibleRecipe.Step[]>() {
        @Override
        public Class<?> componentClass() {
            return CrucibleRecipe.Step[].class;
        }

        @Override
        public JsonElement write(RecipeJS recipe, CrucibleRecipe.Step[] value) {
            if (value == null) {
                return null;
            }

            JsonArray stepsArray = new JsonArray();
            for (CrucibleRecipe.Step step : value) {
                JsonObject stepObj = new JsonObject();

                // Add stirs
                stepObj.addProperty("stirs", step.stirs);

                // Add items
                if (!step.matches.isEmpty()) {
                    JsonArray itemsArray = new JsonArray();
                    for (Ingredient ingredient : step.matches) {
                        itemsArray.add(ingredient.toJson());
                    }
                    stepObj.add("items", itemsArray);
                }

                stepsArray.add(stepObj);
            }

            return stepsArray;
        }

        @Override
        public CrucibleRecipe.Step[] read(RecipeJS recipe, Object from) {
            if (from instanceof JsonElement) {
                JsonArray stepsArray = ((JsonElement) from).getAsJsonArray();
                List<CrucibleRecipe.Step> steps = new ArrayList<>();

                for (JsonElement element : stepsArray) {
                    if (!element.isJsonObject()) {
                        throw new JsonSyntaxException("Each step must be a JSON object");
                    }

                    JsonObject stepObj = element.getAsJsonObject();
                    int stirs = stepObj.has("stirs") ? stepObj.get("stirs").getAsInt() : 0;

                    List<Ingredient> ingredients = new ArrayList<>();
                    if (stepObj.has("items")) {
                        JsonArray itemsArray = stepObj.getAsJsonArray("items");
                        for (JsonElement item : itemsArray) {
                            ingredients.add(Ingredient.fromJson(item));
                        }
                    }

                    steps.add(new CrucibleRecipe.Step(stirs, ingredients));
                }

                return steps.toArray(CrucibleRecipe.Step[]::new);
            } else {
                throw new JsonSyntaxException("Each step must be a JSON object");
            }
        }
    }.key("steps").noBuilders();

    RecipeKey<OutputItem> OUTPUT = ItemComponents.OUTPUT.key("result").noBuilders();

    RecipeSchema SCHEMA = new RecipeSchema(CrucibleRecipeJS.class, CrucibleRecipeJS::new, OUTPUT, STEPS).constructor(((recipe, schemaType, keys, from) -> {
        recipe.setValue(OUTPUT, from.getValue(recipe, OUTPUT));
        recipe.setValue(STEPS, new CrucibleRecipe.Step[0]);
    }), OUTPUT);

}
