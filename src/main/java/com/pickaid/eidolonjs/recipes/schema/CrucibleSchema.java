package com.pickaid.eidolonjs.recipes.schema;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.latvian.mods.kubejs.item.OutputItem;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.*;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author skyraah
 */
public interface CrucibleSchema {
    RecipeKey<OutputItem> OUTPUT = ItemComponents.OUTPUT.key("result");

    /*RecipeComponentBuilder STEP_BUILDER = new RecipeComponentBuilder(2)
            .add(NumberComponent.INT.key("stirs").defaultOptional())
            .add(ItemComponents.INPUT_ARRAY.key("items").defaultOptional());
    RecipeKey<RecipeComponentBuilderMap[]> STEPS = STEP_BUILDER.inputRole().asArray().key("steps");*/


    /**
     * Fluent builder exposed to JS: allows stirs(), item(), items(), and build().
     */
    interface StepBuilderJS {
        /**
         * Set the 'stirs' count for this step (default 1 if no args).
         */
        StepBuilderJS stirs(int count);

        /**
         * Alias for stirs(1).
         */
        default StepBuilderJS stirs() {
            return stirs(1);
        }

        /**
         * Add a single item to this step.
         */
        StepBuilderJS item(String item);

        /**
         * Add multiple items to this step.
         */
        StepBuilderJS items(String[] items);

        /**
         * Finalize this step and emit it to the recipe's steps list.
         */
        void build();
    }

    /**
     * Internal implementation for StepBuilderJS.
     */
    class StepBuilderJSImpl implements StepBuilderJS {
        private final List<JsonObject> steps;
        private JsonObject current = new JsonObject();

        public StepBuilderJSImpl(List<JsonObject> steps) {
            this.steps = steps;
        }

        @Override
        public StepBuilderJS stirs(int count) {
            current.addProperty("stirs", count);
            return this;
        }

        @Override
        public StepBuilderJS item(String item) {
            if (!current.has("items")) {
                current.add("items", new JsonArray());
            }
            current.getAsJsonArray("items").add(item);
            return this;
        }

        @Override
        public StepBuilderJS items(String[] items) {
            JsonArray arr = current.has("items") ? current.getAsJsonArray("items") : new JsonArray();
            for (String s : items) {
                arr.add(s);
            }
            current.add("items", arr);
            return this;
        }

        @Override
        public void build() {
            steps.add(current);
            current = new JsonObject();
        }
    }

    RecipeComponent<Consumer<StepBuilderJS>> STEP_CALLBACK_COMPONENT = new RecipeComponent<>() {
        @Override
        public Class<?> componentClass() {
            return Consumer.class;
        }

        @Override
        public JsonElement write(dev.latvian.mods.kubejs.recipe.RecipeJS recipe, Consumer<StepBuilderJS> callback) {
            List<JsonObject> list = new ArrayList<>();
            StepBuilderJSImpl impl = new StepBuilderJSImpl(list);
            callback.accept(impl);
            JsonArray array = new JsonArray();
            list.forEach(array::add);
            return array;
        }

        @Override
        public Consumer<StepBuilderJS> read(dev.latvian.mods.kubejs.recipe.RecipeJS recipe, Object from) {
            return stepBuilder -> {
                JsonArray stepsArray;
                if (from instanceof JsonArray) {
                    stepsArray = (JsonArray) from;
                } else if (from instanceof String) {
                    stepsArray = JsonParser.parseString((String) from).getAsJsonArray();
                } else {
                    throw new IllegalArgumentException("Invalid input type for steps: " + from.getClass());
                }

                for (JsonElement stepElement : stepsArray) {
                    JsonObject stepObj = stepElement.getAsJsonObject();

                    if (stepObj.has("stirs")) {
                        stepBuilder.stirs(stepObj.get("stirs").getAsInt());
                    }

                    if (stepObj.has("items")) {
                        JsonArray itemsArray = stepObj.getAsJsonArray("items");
                        String[] items = new String[itemsArray.size()];
                        for (int i = 0; i < itemsArray.size(); i++) {
                            items[i] = itemsArray.get(i).getAsString();
                        }
                        stepBuilder.items(items);
                    }

                    stepBuilder.build();
                }
            };
        }
    };

    /**
     * Schema key for steps: accepts a callback that receives a StepBuilderJS.
     */
    RecipeKey<Consumer<StepBuilderJS>> STEPS = STEP_CALLBACK_COMPONENT.key("steps");

    RecipeSchema SCHEMA = new RecipeSchema(OUTPUT, STEPS);

}
