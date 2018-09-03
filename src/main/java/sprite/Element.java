/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprite;

import static main.Utils.*;
import ids.ElementId;
import ids.SpriteId;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

/**
 *
 * @author dariatunina
 */
public class Element extends NotLivingSprite {

    private final ElementId elementId;
    private final String function;

    /**
     * Creates a new instance of Element
     *
     * @param id the name of the element
     * @param image the image of the element
     * @param width the width of the element
     * @param height the height of the element
     * @param elementId specific id for the element
     * @param function specific function that element has
     */
    public Element(SpriteId id, Image image,
            double width, double height, ElementId elementId, String function) {
        super(id, image, width, height);
        this.function = function;
        this.elementId = elementId;
    }

    /**
     *
     * @return The specific function for the element
     */
    public String getFunc() {
        return function;
    }

    /**
     *
     * @param position the position at which element is needed to be.
     * Element 'DOOR' can be in positions 'opened' and 'closed'
     */
    public void changeImg(String position) {
        if (elementId == ElementId.DOOR) {
            WritableImage newImg;
            if (!position.equals("Closed")) {
                newImg = new WritableImage(
                        DOOR_IMG.getPixelReader(), 0, 0, 64, 81);
            } else {
                newImg = new WritableImage(
                        DOOR_IMG.getPixelReader(), 64, 0, 64, 81);
            }
            this.setImage(newImg);
        }
    }

    @Override
    public ElementId getSpecificId() {
        return elementId;
    }
}
