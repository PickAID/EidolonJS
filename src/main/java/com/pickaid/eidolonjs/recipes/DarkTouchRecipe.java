package com.pickaid.eidolonjs.recipes;

import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

/**
 * @author skyraah
 */
public class DarkTouchRecipe implements Recipe<Container> {
    ResourceLocation id;
    Ingredient input;
    ItemStack result;

    public DarkTouchRecipe(Ingredient input, ItemStack result) {
        this.input = input;
        this.result = result;
    }

    @Override
    public boolean matches(@NotNull Container container, @NotNull Level level) {
        return false;
    }

    @Override
    public @NotNull ItemStack assemble(@NotNull Container container, @NotNull RegistryAccess registryAccess) {
        return getResultItem(registryAccess);
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return false;
    }

    @Override
    public @NotNull ItemStack getResultItem(@NotNull RegistryAccess registryAccess) {
        return getResultItem();
    }

    public @NotNull ItemStack getResultItem() {
        return result.copy();
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return id;
    }

    public Ingredient getInput() {
        return input;
    }

    public ItemStack getResult() {
        return result;
    }

    public void setId(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return EidolonJSRecipes.DARK_TOUCH_RECIPE.get();
    }

    @Override
    public @NotNull RecipeType<?> getType() {
        return EidolonJSRecipes.DARK_TOUCH_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<DarkTouchRecipe> {

        @Override
        public @NotNull DarkTouchRecipe fromJson(@NotNull ResourceLocation recipeId, JsonObject json) {
            Ingredient input = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "input"));
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));
            return new DarkTouchRecipe(input, output);
        }

        @Override
        public DarkTouchRecipe fromNetwork(@NotNull ResourceLocation recipeId, FriendlyByteBuf buffer) {
            Ingredient input = Ingredient.fromNetwork(buffer);
            ItemStack output = buffer.readItem();
            return new DarkTouchRecipe(input, output);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, DarkTouchRecipe recipe) {
            recipe.input.toNetwork(buffer);
            buffer.writeItem(recipe.result);
        }
    }
}
