package com.pickaid.eidolonjs.kubejs;

import com.pickaid.eidolonjs.EidolonJS;
import com.pickaid.eidolonjs.recipes.schema.CrucibleSchema;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.recipe.schema.RecipeComponentFactoryRegistryEvent;
import dev.latvian.mods.kubejs.recipe.schema.RegisterRecipeSchemasEvent;
import dev.latvian.mods.kubejs.script.BindingsEvent;

/**
 * @author skyraah
 */
public class EidolonKubeJSPlugin extends KubeJSPlugin {
    /* Basic example of a KubeJS Plugin.
       To register your own plugins, add this class and package name to "kubejs.plugins.txt" in your Resources directory.
    */

    @Override
    public void init() {
        EidolonJS.LOGGER.info("This is my KubeJS Plugin!");
        /** If you don't know how to add content, use Kube's built-in Plugin for reference.
        @see dev.latvian.mods.kubejs.BuiltinKubeJSPlugin
         */
    }

    @Override
    public void registerBindings(BindingsEvent event) {
        event.add("CrucibleStepBuilder", CrucibleSchema.StepBuilderJS.class);
    }

    @Override
    public void registerRecipeSchemas(RegisterRecipeSchemasEvent event) {
        var nameSpace = event.namespace("eidolon");
        nameSpace.register("crucible", CrucibleSchema.SCHEMA);
    }

    @Override
    public void registerRecipeComponents(RecipeComponentFactoryRegistryEvent event) {
//        event.register("steps", CrucibleSchema.STEP_BUILDER);
        event.register("steps", CrucibleSchema.STEP_CALLBACK_COMPONENT);
    }
}

