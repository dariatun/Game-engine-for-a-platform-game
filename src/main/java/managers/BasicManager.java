/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

import ids.Ids;
import sprite.Sprite;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Point2D;

/**
 *
 * @author dariatunina
 */
public abstract class BasicManager {

    private String[] names;
    private final Class classId;
    private final Map<String, Sprite> sprites;
    private static final Logger LOG = Logger.getLogger(BasicManager.class.getName());
    private File file;
    private final String fileName;
    private final Map<String, Point2D> initPositions;

    /**
     * Creates a new instance of BasicManager
     *
     * @param classId type of sprite
     * @param fileName specific name of the file where are the initial sprites
     */
    public BasicManager(Class classId, String fileName) {
        this.fileName = fileName;
        file = new File("level_1/" + fileName);
        this.classId = classId;
        sprites = new HashMap<>();
        initPositions = new HashMap<>();
        try (Scanner sc = new Scanner(file);) {
            sc.useLocale(Locale.ENGLISH);
            readFromFile(sc);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BasicManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @return The array of sprite's names in this manager
     */
    public String[] getNames() {
        return names;
    }

    /**
     *
     * @return Current sprites in the manager
     */
    protected Map<String, Sprite> getSpriteMap() {
        return sprites;
    }

    /**
     * Current type of a name of the manager
     *
     * @return EnemyId for EnemyManager, ItemId for ItemManager, ElementId for
     * ElementManager
     */
    public Class getClassId() {
        return classId;
    }

    /**
     * Removes specific sprite from map of sprites
     *
     * @param name the name of the sprite, that needs to be deleted
     */
    public void deleteSprite(String name) {
        sprites.remove(name);
    }

    /**
     *
     * @param name the name of the sprite
     * @return The specific sprite, that has been requested
     */
    public Sprite getSprite(String name) {
        return sprites.get(name);
    }

    /**
     * Returns sprite of the requested id. If there are several sprites with the
     * same specific id, will return one the last of them. Useful when there is
     * only one sprite of the id.
     *
     * @param id the specific id of the sprite
     * @return The specific sprite, that has been requested
     */
    public Sprite getSprite(Ids id) {
        Sprite ret = null;
        for (Sprite sprite : getSprites()) {
            if (sprite.getSpecificId().equals(id)) {
                ret = sprite;
            }
        }
        return ret;
    }

    /**
     *
     * @return Array of existing sprites in the manager
     */
    public Sprite[] getSprites() {
        return sprites.values().toArray(new Sprite[sprites.size()]);
    }

    private void setParameters(String name) {
        Ids id = ((Ids) Enum.valueOf(classId, name.substring(0, name.length() - 1)));
        sprites.get(name).setName(name);
        sprites.get(name).setPosition(initPositions.get(name));
    }

    /**
     * Sets specific parameters for the sprite
     *
     * @param sprite the sprite that requires additional parameters
     */
    public abstract void setAdditionalPar(Sprite sprite);

    /**
     * Restore deleted sprites with initial parameters.
     */
    public void resetToInit() {
        empty();
        try (Scanner sc = new Scanner(file)) {
            sc.useLocale(Locale.ENGLISH);
            readFromFile(sc);
        } catch (FileNotFoundException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    public void changeLevel(int level) {
        file = new File("level_" + String.valueOf(level) + "/" + fileName);
        resetToInit();
    }

    /**
     * Put names of all existing sprites of the manager
     *
     * @param fw the specific file to write to
     * @param str the specific text that need to write to the file
     */
    public void writeInFile(FileWriter fw, String str) {
        try {
            fw.write(str);
            fw.write(sprites.size() + "\n");
            for (Sprite sprite : getSprites()) {
                fw.write(sprite.getSpecificId() + " " + sprite.getX()
                        + " " + sprite.getY() + "\n");
            }
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    private void empty() {
        for (Sprite sprite : getSprites()) {
            deleteSprite(sprite.getName());
            initPositions.remove(sprite.getName());
        }
    }

    /**
     * Creates new sprite. Sprites are different for each manager. Creates Enemy
     * for EnemyManager, Item for ItemManager, Element for ElementManager.
     *
     * @param name the specific name of the sprite
     * @param id the specific id for sprites (EnemyId for EnemyManager, ItemId
     * for ItemManager, ElementId for ElementManager)
     */
    public abstract void putSprite(String name, Ids id);

    private void readFromFile(Scanner sc) {
        int num = sc.nextInt();
        names = new String[num];
        for (int i = 0; i < num; ++i) {
            Ids id = ((Ids) Enum.valueOf(classId, sc.next()));
            String name = id.toString() + String.valueOf(i);
            names[i] = name;
            putSprite(name, id);
            initPositions.put(name,
                    new Point2D(sc.nextDouble(), sc.nextDouble()));
            setParameters(name);
            setAdditionalPar(sprites.get(name));
        }
    }

    /**
     * Puts sprites which names are given in the file to manager with initial
     * parameters
     *
     * @param sc the specific sc to read information from
     * @param str the specific text that needed to be in the file
     */
    public void readFromSaveFile(Scanner sc, String str) {
        empty();
        if (sc.hasNextLine() && sc.next().equals(str)) {
            readFromFile(sc);
        } else {
            try (Scanner scInit = new Scanner(file)) {
                sc.useLocale(Locale.ENGLISH);
                readFromFile(scInit);
            } catch (FileNotFoundException ex) {
                LOG.log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public String toString() {
        String ret = "";
        for (Sprite sprite : getSprites()) {
            ret += sprite + "\n";
        }
        return sprites.size() + " sprites:\n" + ret;
    }

}
