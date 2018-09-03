/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

import java.util.HashMap;
import java.util.Map;
import sprite.floor.FloorLine;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

/**
 *
 * @author dariatunina
 */
public class Floor {

    private final Map<Point2D, FloorLine> floor;
    private File file;
    private final Map<Point2D, FloorLine> diggableLines;
    private final Image img;

    /**
     * Creates a new instance of BasicManager
     *
     * @param img the specific image of one tile of the floor
     * @param level the name of the folder
     */
    public Floor(Image img, String level) {
        floor = new HashMap<>();
        diggableLines = new HashMap<>();
        this.img = img;
        readFromFile(level + "floor.txt");
    }

    /**
     *
     * @param position the specific point on the screen
     * @return The specific floor tile in the given position
     */
    public FloorLine getFloorSquare(Point2D position) {
        return floor.get(position);
    }

    /**
     *
     * @return The array of every line in the Floor
     */
    public FloorLine[] getFloor() {
        return floor.values().toArray(new FloorLine[floor.size()]);
    }

    /**
     *
     * @return The array of lines that can be digged
     */
    public FloorLine[] getDiggableLines() {
        return diggableLines.values().toArray(new FloorLine[diggableLines.size()]);
    }

    /**
     * Removes line that starts on given position
     *
     * @param position the specific point on the screen
     */
    public void removeLine(Point2D position) {
        floor.remove(position);
    }

    /**
     * Restore deleted lines.
     */
    public void resetToInit() {
        for (FloorLine line : getDiggableLines()) {
            if (!floor.containsValue(line)) {
                floor.put(line.getPosition(), line);
            }
        }
    }
    
    private void deleteFloor() {
        floor.clear();
        diggableLines.clear();
    }
    
    public void changeLevel(int level) {
        deleteFloor();
        readFromFile("level_" + String.valueOf(level) + "/floor.txt");
    }
    
    private void readFromFile(String fileName) {
        try {
            file = new File(fileName);
            Scanner sc = new Scanner(file);
            sc.useLocale(Locale.ENGLISH);
            int num_of_lines = sc.nextInt();
            for (int i = 0; i < num_of_lines; ++i) {
                Point2D position = new Point2D(
                        sc.nextDouble(), sc.nextDouble());
                int length = sc.nextInt();
                int diggable = sc.nextInt();
                FloorLine line = new FloorLine(length, img, position, diggable);
                floor.put(position, line);
                if (diggable == 1) {
                    diggableLines.put(position, line);
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Floor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
