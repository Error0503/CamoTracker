package com.error0503.camotracker.UI;

import javax.swing.WindowConstants;
import com.error0503.camotracker.FileProcessor;
import com.error0503.camotracker.data.WeaponCategoryObj;

import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

public class UI extends JFrame implements WindowListener {

    private List<WeaponCategoryObj> categories;

    public static void main(String[] args) {
        new UI(FileProcessor.loadAll());
    }

    public UI(List<WeaponCategoryObj> categories) {
        this.categories = categories;

        JTabbedPane tabs = new JTabbedPane();
        categories.forEach(c -> tabs.addTab(c.categoryName(), new WeaponCategory(c)));

        try {
            setIconImage(ImageIO.read(getClass().getClassLoader().getResourceAsStream("res/img/mw2icon.png")));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        addWindowListener(this);
        setTitle("MW2 Camo Tracker");
        setContentPane(tabs);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setSize(new Dimension(getWidth() + 17, getHeight()));
        setResizable(false);
        setVisible(true);

    }

    @Override
    public void windowClosing(WindowEvent e) {
        FileProcessor.saveData(categories);
    }

    public void windowOpened(WindowEvent e) {
    }

    public void windowClosed(WindowEvent e) {
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
    }
}
