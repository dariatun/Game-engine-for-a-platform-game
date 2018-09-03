/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprite;

import ids.ItemId;
import static ids.ItemId.*;
import ids.SpriteId;
import javafx.scene.image.Image;

/**
 *
 * @author dariatunina
 */
public class Item extends NotLivingSprite {

    private final ItemId itemId;
    private final String function;

    /**
     * Creates a new instance of Item
     *
     * @param id the name of the item
     * @param image the image of the item
     * @param width the width of the item
     * @param height the height of the item
     * @param itemId specific id for the item
     * @param function specific function that item has
     */
    public Item(SpriteId id, Image image, double width, double height,
            ItemId itemId, String function) {
        super(id, image, width, height);
        this.function = function;
        this.itemId = itemId;
    }

    /**
     *
     * @return The specific function for the item
     */
    public String getFunc() {
        return function;
    }

    @Override
    public ItemId getSpecificId() {
        return itemId;
    }

    /**
     *
     * @return False if item is key, true otherwise
     */
    public boolean usableTest() {
        boolean ret = true;
        if (itemId == KEY) {
            ret = false;
        }
        return ret;
    }

    @Override
    public String toString() {
        return itemId.name() + " on " + this.getX() + ", " + this.getY();
    }
}
