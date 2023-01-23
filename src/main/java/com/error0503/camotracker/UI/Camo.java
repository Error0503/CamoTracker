package com.error0503.camotracker.UI;

import java.awt.GridBagLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import com.error0503.camotracker.data.CamoObj;

public class Camo extends JPanel {

    private List<CamoStateChangeListener> listeners = new ArrayList<CamoStateChangeListener>();

    public void addListener(CamoStateChangeListener listener) {
        listeners.add(listener);
    }

    public void notifyListeners() {
        listeners.forEach(l -> l.onCamoStateChanged());
    }

    public Camo(CamoObj camo) {
        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        JButton button = new JButton();

        button.setMinimumSize(new Dimension(100, 100));
        button.setPreferredSize(new Dimension(100, 100));
        button.setMaximumSize(new Dimension(100, 100));
        button.setBackground(getBackground());
        button.setForeground(getBackground());
        button.setBorder(BorderFactory.createEmptyBorder());

        generateButtonIcon(camo, button);

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 3;
        c.insets = new Insets(0, 0, 0, 10);
        add(button, c);

        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 3;
        c.gridheight = 1;
        c.insets = new Insets(10, 0, 0, 0);

        JLabel name = new JLabel(camo.getDisplayName());
        name.setForeground(new Color(235, 255, 218));
        name.setFont(name.getFont().deriveFont(20f));
        add(name, c);

        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 3;
        c.gridheight = 1;
        c.insets = new Insets(10, 0, 0, 0);

        JLabel requirement = new JLabel(camo.getRequirement());
        requirement.setForeground(new Color(235, 255, 218));
        requirement.setFont(requirement.getFont().deriveFont(15f));
        add(requirement, c);

        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.insets = new Insets(0, 0, 0, 0);

        JLabel progression = new JLabel(String.valueOf(camo.getProgression()), JLabel.LEFT);
        progression.setForeground(new Color(235, 255, 218));
        progression.setPreferredSize(new Dimension(16, 18));
        add(progression, c);

        c.gridx = 2;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.insets = new Insets(0, 5, 0, 5);

        JSlider slider = new JSlider(0, camo.getTarget()) {
            @Override
            public void updateUI() {
                setUI(new CustomSlider(this));
            }
        };
        slider.setValue(camo.getProgression());
        slider.setBackground(new Color(18, 14, 11));
        add(slider, c);

        c.gridx = 3;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.insets = new Insets(0, 0, 0, 10);

        JLabel target = new JLabel(String.valueOf(camo.getTarget()), JLabel.RIGHT);
        target.setForeground(new Color(235, 255, 218));
        target.setPreferredSize(new Dimension(16, 18));
        add(target, c);

        button.addActionListener(e -> {
            camo.setUnlocked(!camo.isUnlocked());

            if (camo.isUnlocked()) {
                slider.setEnabled(false);
                slider.setValue(camo.getTarget());
            } else {
                slider.setEnabled(true);
                slider.setValue(0);
            }

            generateButtonIcon(camo, button);
            notifyListeners();
        });

        slider.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (camo.getProgression() == camo.getTarget()) {
                    camo.setUnlocked(true);
                    generateButtonIcon(camo, button);
                    notifyListeners();
                } else if (camo.getProgression() < camo.getTarget()) {
                    camo.setUnlocked(false);
                    generateButtonIcon(camo, button);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

        });

        slider.addChangeListener(e -> {
            camo.setProgression(slider.getValue());
            progression.setText(String.valueOf(camo.getProgression()));
        });

        setBackground(new Color(18, 14, 11));
        setBorder(BorderFactory.createLineBorder(new Color(73, 73, 74)));
    }

    private void generateButtonIcon(CamoObj camo, JButton button) {
        try {
            BufferedImage w = ImageIO
                    .read(getClass().getClassLoader().getResourceAsStream("res/img/" + camo.getName() + ".png"));

            if (camo.isUnlocked()) {
                BufferedImage tick = ImageIO
                        .read(getClass().getClassLoader().getResourceAsStream("res/img/tick.png"));

                Graphics g = w.getGraphics();
                g.drawImage(tick, 0, 0, null);
            }

            button.setIcon(new ImageIcon(w.getScaledInstance(100, 100,
                    Image.SCALE_SMOOTH)));
        } catch (IOException | IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, "Missing texture or invalid reference to texture!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            button.setIcon(null);
            button.setText("MISSING");
            button.setForeground(Color.RED);
        }
    }
}