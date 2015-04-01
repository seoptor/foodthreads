package net.svideas.techtest.foodthreads.model;

import java.util.Map;

/**
 * Created by Sergiy Germash <s.germash@gmail.com> on 31.03.15.
 */
public class Recipe {

    private String dishName;
    private Map<ProductType, Integer> ingredients;
    private int cookingTime;

    public Recipe(String dishName, Map<ProductType, Integer> ingredients, int cookingTime) {
        this.dishName = dishName;
        this.ingredients = ingredients;
        this.cookingTime = cookingTime;
    }

    public String getDishName() {
        return dishName;
    }

    public Map<ProductType, Integer> getIngredients() {
        return ingredients;
    }

    public int getCookingTime() {
        return cookingTime;
    }

}
