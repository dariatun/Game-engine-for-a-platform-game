/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ids;

import static main.Utils.*;
import javafx.scene.image.Image;

/**
 *
 * @author dariatunina
 */
public enum ItemId implements Ids {
    KEY(0, KEY_FUNC),
    PIZZA(1, FOOD_FUNC),
    BONE(2, FOOD_FUNC),
    APPLE(3, FOOD_FUNC),
    ICE_CREAM(4, FOOD_FUNC);

    private final int num;
    private final String func;

    private ItemId(int num, String func) {
        this.num = num;
        this.func = func;
    }

    /**
     * The function of the sprite that is related with this id
     *
     * @return The name of the function
     */
    public String getFunc() {
        return func;
    }

    @Override
    public double getWidth() {
        return ITEM_SIZE;
    }

    @Override
    public double getHeight() {
        return ITEM_SIZE;
    }

    public Image getImage() {
        return ITEMS_IMG[num];
    }

    @Override
    public SpriteId getSpriteId() {
        return SpriteId.ITEM;
    }
}
