/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

import static main.Utils.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

/**
 *
 * @author dariatunina
 */
public class ImageManager {

    private final Map<String, Image[]> imgsLines;
    private Image wholeImg;
    private int width;
    private int height;
    private int indentX;
    private int indentY;
    private final File file;
    private String facing;

    public ImageManager(String fileName) {
        file = new File(fileName);
        imgsLines = new HashMap<>();
        analizeFile();
    }

    private void analizeFile() {
        try {
            Scanner sc = new Scanner(file);
            sc.useLocale(Locale.ENGLISH);
            String imgName = sc.next();
            wholeImg = new Image(GFX_DIR + imgName);
            facing = sc.next();
            width = sc.nextInt();
            height = sc.nextInt();
            indentX = sc.nextInt();
            indentY = sc.nextInt();
            analizeImg(sc);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ImageManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void analizeImg(Scanner sc) {
        int count = 0;
        while (sc.hasNext()) {
            int frameNum = sc.nextInt();
            String action = sc.next();
            Image[] imgs = new Image[frameNum];
            for (int fr = 0; fr < frameNum; ++fr) {
                WritableImage wrImg = new WritableImage(
                        wholeImg.getPixelReader(),
                        (int) Math.floor((width + indentX) * fr),
                        (int) Math.floor((height + indentY) * count),
                        (int) Math.floor(width),
                        (int) Math.floor(height));
                imgs[fr] = wrImg;
            }
            imgsLines.put(action, imgs);
            count++;
        }
    }

    public boolean getFacing() {
        boolean ret = false;
        if (facing.equals("left")) {
            ret = true;
        }
        return ret;
    }

    public Map getImgsLine() {
        return imgsLines;
    }

}
