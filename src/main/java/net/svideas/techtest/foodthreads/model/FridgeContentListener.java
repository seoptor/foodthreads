package net.svideas.techtest.foodthreads.model;

import net.svideas.techtest.foodthreads.model.ProductType;

import java.util.Map;

/**
 * Created by Sergiy Germash <s.germash@gmail.com> on 31.03.15.
 */
public interface FridgeContentListener {

    public void fridgeContentsUpdated(Map<ProductType, Integer> contents);

}
