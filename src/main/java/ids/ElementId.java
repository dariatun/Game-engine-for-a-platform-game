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
public enum ElementId implements Ids {
    DOOR(0, DOOR_FUNC),
    SAVE_POINT(1, SAVE_POINT_FUNC);

    private final int num;
    private final String func;

    private ElementId(int num, String func) {
        this.num = num;
        this.func = func;
    }

    /**
     *
     * @return The function of the sprite that is related with this id
     */
    public String getFunc() {
        return func;
    }

    @Override
    public double getWidth() {
        return ELEMENTS_WIDTH[num];
    }

    @Override
    public double getHeight() {
        return ELEMENTS_HEIGHT[num];
    }

    public Image getImage() {
        return ELEMENTS_IMG[num];
    }

    @Override
    public SpriteId getSpriteId() {
        return SpriteId.ELEMENT;
    }

}
