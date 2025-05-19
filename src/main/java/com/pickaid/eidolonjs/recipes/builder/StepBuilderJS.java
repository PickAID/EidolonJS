package com.pickaid.eidolonjs.recipes.builder;

import dev.latvian.mods.rhino.util.HideFromJS;
import elucent.eidolon.recipe.CrucibleRecipe;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author skyraah
 */
@SuppressWarnings("unused")
public class StepBuilderJS {
    List<CrucibleRecipe.Step> stepList = new ArrayList<>();
    List<Ingredient> item = new ArrayList<>();
    int stirs = 0;

    public StepBuilderJS stirs(int count) {
        stirs = count;
        return this;
    }

    public StepBuilderJS stirs() {
        return stirs(1);
    }

    public StepBuilderJS items(Ingredient... items) {
        item.addAll(List.of(items));
        return this;
    }

    public void step(int count, Ingredient... items) {
        var step = new CrucibleRecipe.Step(count, item);
        stepList.add(step);
    }

    public void build(){
        var step = new CrucibleRecipe.Step(stirs, item);
        stepList.add(step);
        stirs(0);
        item.clear();
    }

    @HideFromJS
    public List<CrucibleRecipe.Step> getStepList() {
        return stepList;
    }

/*public StepBuilderJS item(String item) {
        if (!current.has("items")) {
            current.add("items", new JsonArray());
        }
        current.getAsJsonArray("items").add(item);
        return this;
    }*/

    /*public StepBuilderJS items(String[] items) {
        JsonArray arr = current.has("items") ? current.getAsJsonArray("items") : new JsonArray();
        for (String s : items) {
            arr.add(s);
        }
        current.add("items", arr);
        return this;
    }*/

    /*public void build() {
        steps.add(current);
        current = new JsonObject();
    }*/
}