package net.svideas.techtest.foodthreads.console;

import net.svideas.techtest.foodthreads.model.ThreadMessageListener;

/**
 * Created by gans on 31.03.15.
 */
public class ConsoleMessageWriter implements ThreadMessageListener {
    @Override
    public void receiveMessage(String message) {
        System.out.println(message);
    }

}
