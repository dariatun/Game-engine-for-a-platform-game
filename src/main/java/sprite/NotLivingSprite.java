/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprite;

import ids.SpriteId;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 *
 * @author dariatunina
 */
public class NotLivingSprite extends Sprite {

    private Image img;

    public NotLivingSprite(SpriteId id, Image img, double width, double height) {
        super(id, width, height);
        this.img = img;
    }

    /**
     *
     * @return the image of the sprite
     */
    public Image getImage() {
        return img;
    }

    /**
     * Sets specific image
     *
     * @param img the specific image
     */
    public void setImage(Image img) {
        this.img = img;
    }

    /**
     * Paint image of sprite
     *
     * @param gc graphic context of GameCanvas
     */
    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(img, getX(), getY(), getWidth(), getHeight());
    }
}
