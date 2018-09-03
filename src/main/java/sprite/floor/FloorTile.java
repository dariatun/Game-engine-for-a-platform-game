/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprite.floor;

import static main.Utils.*;
import ids.SpriteId;
import sprite.NotLivingSprite;
import javafx.scene.image.Image;

/**
 *
 * @author dariatunina
 */
public class FloorTile extends NotLivingSprite {

    /**
     * Creates a new instance of FloorTile
     *
     * @param id the name of the sprite
     * @param image the image of the sprite
     */
    public FloorTile(SpriteId id, Image image) {
        super(id, image, FLR_SQR_SIZE, FLR_SQR_SIZE);
    }
}
