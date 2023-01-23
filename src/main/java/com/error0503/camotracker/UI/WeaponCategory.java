package com.error0503.camotracker.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.plaf.basic.BasicScrollBarUI;

import com.error0503.camotracker.data.WeaponCategoryObj;
import com.error0503.camotracker.data.WeaponObj;

public class WeaponCategory extends JPanel {
    public WeaponCategory(WeaponCategoryObj category) {

        setLayout(new GridLayout(1, 1));

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        for (WeaponObj w : category.weapons()) {
            p.add(w.name(), new Weapon(w));
        }

        JScrollPane scroll = new JScrollPane(p);
        scroll.setAutoscrolls(true);
        scroll.setPreferredSize(new Dimension((int) Math.round(p.getPreferredSize().getWidth()), 800));
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(205, 221, 237);
                this.trackColor = new Color(55, 55, 80);
            }
        });
        scroll.getVerticalScrollBar().setUnitIncrement(100);
        scroll.getVerticalScrollBar().setBlockIncrement(100);

        scroll.setBorder(BorderFactory.createEmptyBorder());
        
        add(scroll);

        setBackground(new Color(18, 14, 11));
    }
}
