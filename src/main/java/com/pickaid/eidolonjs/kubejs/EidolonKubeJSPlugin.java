package com.pickaid.eidolonjs.kubejs;

import com.pickaid.eidolonjs.EidolonJS;
import com.pickaid.eidolonjs.recipes.builder.StepBuilderJS;
import com.pickaid.eidolonjs.recipes.schema.CrucibleSchema;
import com.pickaid.eidolonjs.recipes.schema.ForagingSchema;
import com.pickaid.eidolonjs.recipes.schema.WorktableSchema;
import dev.latvian.mods.kubejs.KubeJSPlugin;
import dev.latvian.mods.kubejs.recipe.schema.RecipeComponentFactoryRegistryEvent;
import dev.latvian.mods.kubejs.recipe.schema.RegisterRecipeSchemasEvent;
import dev.latvian.mods.kubejs.script.BindingsEvent;
import elucent.eidolon.Eidolon;

/**
 * @author skyraah
 */
public class EidolonKubeJSPlugin extends KubeJSPlugin {

    @Override
    public void init() {
        EidolonJS.LOGGER.info("This is my KubeJS Plugin!");
        /** If you don't know how to add content, use Kube's built-in Plugin for reference.
        @see dev.latvian.mods.kubejs.BuiltinKubeJSPlugin
         */
    }

    @Override
    public void registerBindings(BindingsEvent event) {
        event.add("StepBuilderJS", StepBuilderJS.class);
        event.add("CrucibleRecipeJS", CrucibleSchema.CrucibleRecipeJS.class);
    }

    @Override
    public void registerRecipeSchemas(RegisterRecipeSchemasEvent event) {
        var nameSpace = event.namespace(Eidolon.MODID);
        nameSpace
                .register("crucible", CrucibleSchema.SCHEMA)
                .register("worktable", WorktableSchema.SCHEMA)
                .register("athame_foraging", ForagingSchema.SCHEMA);
    }

    @Override
    public void registerRecipeComponents(RecipeComponentFactoryRegistryEvent event) {
        event.register("steps", CrucibleSchema.STEPS.component);
    }
}

