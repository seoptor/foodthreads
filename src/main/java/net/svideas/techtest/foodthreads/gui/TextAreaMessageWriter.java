package net.svideas.techtest.foodthreads.gui;

import net.svideas.techtest.foodthreads.model.ThreadMessageListener;

import javax.swing.*;

/**
 * Created by Sergiy Germash <s.germash@gmail.com> on 31.03.15.
 */
public class TextAreaMessageWriter implements ThreadMessageListener {

    private JTextArea textArea;

    public TextAreaMessageWriter(JTextArea textArea) {
        this.textArea = textArea;
    }


    @Override
    public void receiveMessage(String message) {
        textArea.append(message+"\n");
    }

}