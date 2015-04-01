package net.svideas.techtest.foodthreads.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sergiy Germash <s.germash@gmail.com> on 30.03.15.
 */
public class Fridge {

    private final static int INITIAL_PRODUCT_QUANTITY = 10;
    private volatile Map<ProductType, Integer> contents = new HashMap<ProductType, Integer>();
    private List<FridgeContentListener> contentListeners = new ArrayList<FridgeContentListener>();

    public Fridge() {
        for (ProductType productType : ProductType.values()) {
            contents.put(productType, INITIAL_PRODUCT_QUANTITY);
        }
    }

    public Map<ProductType, Integer> getContents()
    {
        return contents;
    }

    public synchronized void putProducts(Map<ProductType, Integer> productsToPutIn) {
        if (contents != null && !contents.isEmpty()) {
            for (ProductType productTypeToPut : productsToPutIn.keySet()) {
                Integer currentProductCount = contents.get(productTypeToPut);
                if (currentProductCount == null) {
                    currentProductCount = 0;
                }
                int totalProductCount =  currentProductCount + productsToPutIn.get(productTypeToPut);
                contents.put(productTypeToPut, totalProductCount);
            }
        }
        else {
            contents = new HashMap<ProductType, Integer>();
            contents.putAll(productsToPutIn);
        }
        pushContentNotification();
    }

    public synchronized boolean pickProducts(Map<ProductType, Integer> productsToPick) {
        if (contents != null && !contents.isEmpty() && isContainsEnoughProducts(productsToPick)) {
            for (ProductType productTypeToPick : productsToPick.keySet()) {
                Integer currentProductCount = contents.get(productTypeToPick);
                int totalProductCount = currentProductCount - productsToPick.get(productTypeToPick);
                contents.put(productTypeToPick, totalProductCount);
            }
            pushContentNotification();
            return true;
        }
        else {
            return false;
        }
    }

    private boolean isContainsEnoughProducts(Map<ProductType, Integer> productsToCheck) {
        for (ProductType productTypeToCheck : productsToCheck.keySet()) {
            Integer currentProductQuantity = contents.get(productTypeToCheck);
            if (currentProductQuantity == null || currentProductQuantity < productsToCheck.get(productTypeToCheck)) {
                return false;
            }
        }
        return true;
    }


    private void pushContentNotification() {
        for (FridgeContentListener listener : contentListeners) {
            listener.fridgeContentsUpdated(contents);
        }
    }

    public synchronized void addContentListener(FridgeContentListener listener) {
        contentListeners.add(listener);
    }

    public synchronized void removeContentListener(FridgeContentListener listener) {
        contentListeners.remove(listener);
    }

}
