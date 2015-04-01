package net.svideas.techtest.foodthreads.gui;

import net.svideas.techtest.foodthreads.*;
import net.svideas.techtest.foodthreads.model.*;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.event.*;
import java.util.*;

/**
 * Created by Sergiy Germash <s.germash@gmail.com>
 * Created by  on 31.03.15.
 */
public class MainForm extends JFrame implements FridgeContentListener {

    private ThreadsManager threadsManager = new ThreadsManager();
    private Fridge fridge;
    private RecipesBook recipesBook;
    private TextAreaMessageWriter mainLogger;

    private JPanel panel;
    private JButton addCookButton;
    private JButton addSupplyButton;
    private JTextArea logArea;
    private JButton clearLogButton;
    private JPanel fridgeProgressPanel;
    private Map<ProductType, JProgressBar> fridgeProgressBars = new HashMap<ProductType, JProgressBar>();

    public MainForm() {
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(panel);
        DefaultCaret caret = (DefaultCaret)logArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        mainLogger = new TextAreaMessageWriter(logArea);
        fridge = new Fridge();
        fridge.addContentListener(this);
        recipesBook = new RecipesBook();

        // Create fridge's contents status bars collection for all product types
        // TODO create only bars here and then call contents updated method to fill with data
        for (ProductType productType : ProductType.values()) {
            JProgressBar productStatusBar = new JProgressBar();
            productStatusBar.setOrientation(SwingConstants.VERTICAL);
            productStatusBar.setStringPainted(true);
            fridgeProgressPanel.add(productStatusBar);
            fridgeProgressBars.put(productType, productStatusBar);
        }
        fridgeContentsUpdated(fridge.getContents());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                threadsManager.stopAllThreads();
                super.windowClosed(e);
            }
        });
        addCookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Cook cook = new Cook("Cook-"+new Random(System.currentTimeMillis()).nextInt(Integer.MAX_VALUE), recipesBook, fridge);
                cook.addMessageListener(mainLogger);
                CookForm cookForm = new CookForm(cook, threadsManager, getDialogParent());
                cookForm.setVisible(true);
                threadsManager.fireThread(cook);
            }
        });
        addSupplyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Supply supply = new Supply("Supply-"+new Random(System.currentTimeMillis()).nextInt(Integer.MAX_VALUE), fridge);
                supply.addMessageListener(mainLogger);
                SupplyForm supplyForm = new SupplyForm(supply, threadsManager, getDialogParent());
                supplyForm.setVisible(true);
                threadsManager.fireThread(supply);
            }
        });
        clearLogButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                logArea.setText("");
            }
        });
    }

    public void fridgeContentsUpdated(Map<ProductType, Integer> contents) {
        for (ProductType productType : fridgeProgressBars.keySet()) {
            JProgressBar productStatusBar = fridgeProgressBars.get(productType);
            Integer productQuantity = contents.get(productType);
            productStatusBar.setValue(productQuantity);
            productStatusBar.setString(productType.name()+" - "+productQuantity);
        }
    }

    private JFrame getDialogParent() {
        return this;
    }

}
