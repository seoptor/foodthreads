package net.svideas.techtest.foodthreads.gui;

import net.svideas.techtest.foodthreads.model.ThreadMessageListener;
import net.svideas.techtest.foodthreads.ThreadsManager;
import net.svideas.techtest.foodthreads.model.Cook;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Sergiy Germash <s.germash@gmail.com> on 31.03.15.
 */
public class CookForm extends JDialog implements ThreadMessageListener {

    private Cook cook;
    private ThreadsManager threadsManager;
    private JPanel panel;
    private JTextArea logArea;
    private JButton clearLogButton;

    public CookForm(Cook cook, ThreadsManager threadsManager, JFrame parent) {
        super(parent);
        setModal(false);
        this.cook = cook;
        this.threadsManager = threadsManager;

        setSize(400, 300);
        setTitle(cook.getName());
        add(panel);

        cook.addMessageListener(this);
        DefaultCaret caret = (DefaultCaret)logArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                removeCook();
            }
        });
        clearLogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                logArea.setText("");
            }
        });
    }

    @Override
    public void receiveMessage(String message) {
        logArea.append(message+"\n");
    }

    private void removeCook() {
        cook.removeMessageListener(this);
        threadsManager.killThread(cook);
    }

}
