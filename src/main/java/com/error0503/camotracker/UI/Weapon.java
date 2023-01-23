package com.error0503.camotracker.UI;

import java.awt.Color;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.error0503.camotracker.data.WeaponObj;

public class Weapon extends JPanel implements CamoStateChangeListener {

    private WeaponObj weapon;
    private List<Camo> standard;
    private List<Camo> mastery;

    public Weapon(WeaponObj weapon) {
        this.weapon = weapon;
        buildUI();
    }

    private void buildUI() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel l = new JLabel(weapon.name(), JLabel.CENTER);
        l.setAlignmentX(CENTER_ALIGNMENT);
        l.setFont(l.getFont().deriveFont(26f));

        if (weapon.camos().stream().anyMatch(c -> c.isUnlocked() == false)) {
            l.setForeground(new Color(235, 255, 218));
        } else if (weapon.masteries().get(2).isUnlocked()) {
            l.setForeground(new Color(107, 45, 234));
        } else if (weapon.masteries().get(1).isUnlocked()) {
            l.setForeground(new Color(152, 152, 141));
        } else if (weapon.masteries().get(0).isUnlocked()) {
            l.setForeground(new Color(255, 224, 85));
        }
        add(l);

        standard = weapon.camos().stream().map(c -> new Camo(c))
                .collect(Collectors.toList());
        mastery = weapon.masteries().stream().map(c -> new Camo(c))
                .collect(Collectors.toList());

        standard.forEach(c -> c.addListener(this));
        mastery.forEach(c -> c.addListener(this));

        if (weapon.camos().stream().anyMatch(c -> c.isUnlocked() == false))
            standard.forEach(this::add);
        else
            mastery.forEach(this::add);

        setBackground(new Color(18, 14, 11));
    }

    @Override
    public void onCamoStateChanged() {
        removeAll();
        buildUI();
    }
}
