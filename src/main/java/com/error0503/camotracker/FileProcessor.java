package com.error0503.camotracker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.error0503.camotracker.data.WeaponCategoryObj;

public class FileProcessor {

    private final static ObjectMapper mapper = new ObjectMapper();

    public static List<WeaponCategoryObj> loadAll() {
        return Arrays.asList(new String[] {
                "res/Assault Rifles.json",
                "res/Battle Rifles.json",
                "res/Handguns.json",
                "res/Launchers.json",
                "res/Light Machine Guns.json",
                "res/Marksman Rifles.json",
                "res/Melee.json",
                "res/Shotguns.json",
                "res/Sniper Rifles.json",
                "res/Sub Machine Guns.json"
        }).stream().map(src -> {
            try {
                File f = new File(src);
                return mapper.readValue(
                        (f.exists()) ? mapper.getFactory().createParser(f)
                                : mapper.getFactory()
                                        .createParser(FileProcessor.class.getClassLoader().getResourceAsStream(src)),
                        WeaponCategoryObj.class);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }).toList();
    }

    public static void saveData(List<WeaponCategoryObj> categories) {
        categories.forEach(category -> {
            try {
                File f = new File("res/" + category.categoryName() + ".json");

                Files.createDirectories(f.getParentFile().toPath());

                FileWriter writer = new FileWriter(f);
                writer.write(mapper.writeValueAsString(category));
                writer.close();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        });
    }
}
