package net.svideas.techtest.foodthreads.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sergiy Germash <s.germash@gmail.com> on 31.03.15.
 */
public class RecipesBook {

    private List<Recipe> recipes;

    public RecipesBook() {
        recipes = new ArrayList<Recipe>();
        recipes.add(getVegetarian());
        recipes.add(getMexican());
        recipes.add(getHavanian());
        recipes.add(getItalian());
    }

    public List<Recipe> getReceips() {
        return recipes;
    }

    private Recipe getVegetarian() {
        Map<ProductType, Integer> ingredients = new HashMap<ProductType, Integer>();
        ingredients.put(ProductType.CORN, 1);
        ingredients.put(ProductType.BELL_PEPPER, 2);
        ingredients.put(ProductType.MUSHROOMS, 1);
        ingredients.put(ProductType.TOMATO, 1);
        ingredients.put(ProductType.ONION, 1);
        Recipe recipe = new Recipe("Vegetarian pizza", ingredients, 3000);
        return recipe;
    }

    private Recipe getMexican() {
        Map<ProductType, Integer> ingredients = new HashMap<ProductType, Integer>();
        ingredients.put(ProductType.CHEESE, 1);
        ingredients.put(ProductType.BACON, 1);
        ingredients.put(ProductType.PEPPERONI, 1);
        ingredients.put(ProductType.OLIVE, 1);
        ingredients.put(ProductType.ONION, 1);
        Recipe recipe = new Recipe("Mexican pizza", ingredients, 4000);
        return recipe;
    }

    private Recipe getHavanian() {
        Map<ProductType, Integer> ingredients = new HashMap<ProductType, Integer>();
        ingredients.put(ProductType.CHEESE, 2);
        ingredients.put(ProductType.HAM, 1);
        ingredients.put(ProductType.TOMATO, 1);
        ingredients.put(ProductType.OLIVE, 1);
        ingredients.put(ProductType.BELL_PEPPER, 1);
        Recipe recipe = new Recipe("Havanian pizza", ingredients, 4500);
        return recipe;
    }

    private Recipe getItalian() {
        Map<ProductType, Integer> ingredients = new HashMap<ProductType, Integer>();
        ingredients.put(ProductType.CHEESE, 1);
        ingredients.put(ProductType.SALAMI, 1);
        ingredients.put(ProductType.CORN, 1);
        ingredients.put(ProductType.OLIVE, 2);
        ingredients.put(ProductType.PEPPERONI, 1);
        Recipe recipe = new Recipe("Italian pizza", ingredients, 5000);
        return recipe;
    }

}