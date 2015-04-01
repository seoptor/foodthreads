package net.svideas.techtest.foodthreads.model;

import java.util.*;

/**
 * Created by Sergiy Germash <s.germash@gmail.com> on 31.03.15.
 */
public class Supply implements Runnable {

    private String name;
    private Fridge fridge;
    private int pauseTime = 4000;
    private Random random;
    private volatile List<ThreadMessageListener> messageListeners = new ArrayList<ThreadMessageListener>();

    public Supply(String name, Fridge fridge) {
        this.name = name;
        this.fridge = fridge;
        random = new Random(System.currentTimeMillis());
    }

    public String getName() {
        return name;
    }

    private synchronized void doPause() {
        doEmptyWaiting(pauseTime);
    }

    private synchronized void doDelivery() {
        Map<ProductType, Integer> productsToDeliver = getProductsToDeliver();
        fridge.putProducts(productsToDeliver);
        pushMessage(name + ": Delivered: " + productsToDeliver);
    }

    private void doEmptyWaiting(int timeToWait) {
        long startingTime = System.currentTimeMillis();
        long currentTime = System.currentTimeMillis();
        while (currentTime - startingTime <= timeToWait) {
            // Relax and wait till pause will end... Close your eyes. Open your mind...
            currentTime = System.currentTimeMillis();
        }
    }

    private Map<ProductType, Integer> getProductsToDeliver() {
        Map<ProductType, Integer> productsToDeliver = new HashMap<ProductType, Integer>();

        // Cycles are number of trials to select which products to deliver
        int cyclesCount = random.nextInt(ProductType.values().length);
        for (int i = 0; i<cyclesCount; i++) {
            // As the random can return same numbers (eq products), count of products may be less then count of cycles
            int productToDeliverIndex = random.nextInt(ProductType.values().length);
            ProductType productToDeliverType = ProductType.values()[productToDeliverIndex];

            // Quantity is a random from 2 to 7
            int productToDeliverQuantity = random.nextInt(5) + 2;

            productsToDeliver.put(productToDeliverType, productToDeliverQuantity);
        }

        return productsToDeliver;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            doDelivery();
            doPause();
        }
    }

    private void pushMessage(String message) {
        for (ThreadMessageListener listener : messageListeners) {
            listener.receiveMessage(message);
        }
    }

    public synchronized void addMessageListener(ThreadMessageListener listener) {
        messageListeners.add(listener);
    }

    public synchronized void removeMessageListener(ThreadMessageListener listener) {
        messageListeners.remove(listener);
    }

}