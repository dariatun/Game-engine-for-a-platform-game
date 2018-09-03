/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprite.floor;

import static main.Utils.*;
import ids.SpriteId;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 *
 * @author dariatunina
 */
public class FloorLine {

    private final FloorTile[] floorLine;
    private final int lineLength;
    private final Point2D position;
    private boolean diggable;

    /**
     * Creates a new instance of FloorLine
     *
     * @param lineLength the length of the line
     * @param image the specific image of one tile of the line
     * @param position the point in upper-left corner of the first tile in the
     * line.
     * @param diggable the value that indicates if line can be digged or not
     */
    public FloorLine(int lineLength, Image image, Point2D position, int diggable) {
        if (diggable == 1) {
            this.diggable = true;
            image = DIG_STONES_IMG;
        }
        this.lineLength = lineLength;
        this.position = position;
        floorLine = new FloorTile[lineLength];
        for (int i = 0; i < lineLength; ++i) {
            floorLine[i] = new FloorTile(SpriteId.FLOOR, image);
            floorLine[i].setPosition(position.getX()
                    + i * FLR_SQR_SIZE, position.getY());
        }
    }

    /**
     *
     * @return True if line will be deleted after pressing 'C' above it, false
     * otherwise
     */
    public boolean isDiggable() {
        return diggable;
    }

    /**
     *
     * @return the array of floor tiles that creates the line
     */
    public FloorTile[] getFloorLine() {
        return floorLine;
    }

    /**
     * Paints all of tiles in the line
     *
     * @param gc graphic context of GameCanvas
     */
    public void render(GraphicsContext gc) {
        for (int i = 0; i < lineLength; ++i) {
            floorLine[i].render(gc);
        }
    }

    /**
     *
     * @return the rectangle that is equal to the line
     */
    public Rectangle2D getBounds() {
        return new Rectangle2D(position.getX(), position.getY(),
                lineLength * FLR_SQR_SIZE, FLR_SQR_SIZE);
    }

    /**
     *
     * @return The point in upper-left corner of the first tile in the line
     */
    public Point2D getPosition() {
        return position;
    }

    /**
     *
     * @return the y coordinate in upper-left corner of the first tile in the
     * line
     */
    public double getY() {
        return position.getY();
    }

    /**
     *
     * @return the x coordinate of the upper-left corner of the line
     */
    public double getMinX() {
        return position.getX();
    }

    /**
     *
     * @return the x coordinate of the lower-right corner of the line
     */
    public double getMaxX() {
        return position.getX() + lineLength * FLR_SQR_SIZE;
    }

}
