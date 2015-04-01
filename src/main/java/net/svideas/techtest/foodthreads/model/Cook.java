package net.svideas.techtest.foodthreads.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Sergiy Germash <s.germash@gmail.com> on 31.03.15.
 */
public class Cook implements Runnable {

    private String name;
    private RecipesBook recipesBook;
    private Fridge fridge;
    private final int pauseTime = 2000;
    private volatile CookStatus status = CookStatus.IDLE;
    private volatile Recipe currentRecipe = null;
    private volatile List<ThreadMessageListener> messageListeners = new ArrayList<ThreadMessageListener>();

    private Random random;

    public Cook(String name, RecipesBook recipesBook, Fridge fridge) {
        this.name = name;
        this.recipesBook = recipesBook;
        this.fridge = fridge;
        random = new Random(System.currentTimeMillis());
    }

    public CookStatus getStatus() {
        return status;
    }

    public Recipe getCurrentRecipe() {
        return currentRecipe;
    }

    public String getName() {
        return name;
    }

    private synchronized void doCooking() {
        status = CookStatus.COOKING;
        currentRecipe = getRecipeToCook();
        pushMessage(name + ": Trying " + currentRecipe.getDishName() + " recipe: " + currentRecipe.getIngredients());

        if (fridge.pickProducts(currentRecipe.getIngredients())) {
            // Pretend we are cooking
            pushMessage(name + ": Cooking the " + currentRecipe.getDishName() + "...");
            doEmptyWaiting(currentRecipe.getCookingTime());
            pushMessage(name + ": ...Done cooking!");
        }
        else {
            pushMessage(name + ": ...Not enough products for " + currentRecipe.getDishName());
        }

        currentRecipe = null;
    }

    private synchronized void doPause() {
        status = CookStatus.IDLE;

        // Do pause to relax
        pushMessage(name + ": Pausing...");
        doEmptyWaiting(pauseTime);
        pushMessage(name + ": ...Done pausing!");
    }

    private Recipe getRecipeToCook() {
        return recipesBook.getReceips().get(random.nextInt(recipesBook.getReceips().size()));
    }

    private void doEmptyWaiting(int timeToWait) {
        long startingTime = System.currentTimeMillis();
        long currentTime = System.currentTimeMillis();
        while (currentTime - startingTime <= timeToWait) {
            // Relax and wait till pause will end... Close your eyes. Open your mind...
            currentTime = System.currentTimeMillis();
        }
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            doPause();
            doCooking();
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
