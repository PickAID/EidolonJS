package com.pickaid.eidolonjs.recipes;

import elucent.eidolon.Eidolon;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * @author skyraah
 */
public class EidolonJSRecipes {
    static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Eidolon.MODID);
    public static final RegistryObject<RecipeSerializer<DarkTouchRecipe>> DARK_TOUCH_RECIPE = RECIPE_SERIALIZERS.register("dark_touch", DarkTouchRecipe.Serializer::new);

    static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, Eidolon.MODID);
    public static final RegistryObject<RecipeType<DarkTouchRecipe>> DARK_TOUCH_TYPE = RECIPE_TYPES.register("dark_touch", () -> new RecipeType<DarkTouchRecipe>() {
        @Override
        public String toString() {
            return "eidolon:dark_touch";
        }
    });
}
