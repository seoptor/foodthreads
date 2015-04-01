package net.svideas.techtest.foodthreads.gui;

import net.svideas.techtest.foodthreads.model.ThreadMessageListener;
import net.svideas.techtest.foodthreads.ThreadsManager;
import net.svideas.techtest.foodthreads.model.Supply;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Sergiy Germash <s.germash@gmail.com> on 31.03.15.
 */
public class SupplyForm extends JDialog implements ThreadMessageListener {

    private Supply supply;
    private ThreadsManager threadsManager;
    private JPanel panel;
    private JTextArea logArea;
    private JButton clearLogButton;

    public SupplyForm(Supply supply, ThreadsManager threadsManager, JFrame parent) {
        super(parent);
        setModal(false);
        this.supply = supply;
        this.threadsManager = threadsManager;

        setSize(400, 300);
        setTitle(supply.getName());
        add(panel);

        supply.addMessageListener(this);
        DefaultCaret caret = (DefaultCaret)logArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                removeSupply();
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

    private void removeSupply() {
        supply.removeMessageListener(this);
        threadsManager.killThread(supply);
    }

}
