/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprite;

import java.util.Map;
import javafx.scene.image.Image;

/**
 *
 * @author dariatunina
 */
public class SpriteAnimation {

    private Image[] imgs;
    private int currFrame;
    private int framesNum;
    private final Map<String, Image[]> imgsLines;

    /**
     * @param imgsLines     
     */
    public SpriteAnimation(Map<String, Image[]> imgsLines) {
        this.imgsLines = imgsLines;
        imgs = imgsLines.get("walk");
        currFrame = 0;
        framesNum = imgs.length;
    }
    
    public void changeToWalk() {
        imgs = imgsLines.get("walk");
        framesNum = imgs.length;
        currFrame = currFrame >= framesNum ? 0 : currFrame;
    }
    
    public void changeToStand() {
        imgs = imgsLines.get("stand");
        framesNum = imgs.length;
        currFrame = currFrame >= framesNum ? 0 : currFrame;
    }
    
    public Image getImg() {
        return imgs[currFrame];
    }

    public void changeImg() {
        currFrame++;
        if (currFrame == framesNum) {
            currFrame = 0;
        }
    }
}
