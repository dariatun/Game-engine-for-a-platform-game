/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprite;

import ids.Ids;
import ids.SpriteId;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 *
 * @author dariatunina
 */
public abstract class Sprite {

    private double x, y;
    private double width, height;
    private final SpriteId spriteId;
    private String name;

    /**
     * Creates a new instance of Sprite
     *
     * @param id the name of the sprite
     * @param width the width of the sprite
     * @param height the height of the sprite
     */
    public Sprite(SpriteId id,  double width, double height) {
        spriteId = id;
        this.width = width;
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return The x coordinate of the center point of the sprite
     */
    public double getCenterX() {
        return x + width / 2;
    }

    /**
     *
     * @return The y coordinate of the center point of the sprite
     */
    public double getCenterY() {
        return y + height / 2;
    }



    /**
     * @return The name (id) of the sprite
     */
    public SpriteId getSpriteId() {
        return spriteId;
    }

    /**
     * @return The x coordinate of the upper-left corner of the sprite
     */
    public double getX() {
        return x;
    }

    /**
     * Set the specific variable to the sprite's upper-left corner point x
     * coordinate
     *
     * @param x the value to be set
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * @return The y coordinate of the upper-left corner of the sprite
     */
    public double getY() {
        return y;
    }

    /**
     * Set the specific variable to the sprite's upper-left corner point y
     * coordinate
     *
     * @param y the value to be set
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * @return The width of the sprite
     */
    public double getWidth() {
        return width;
    }

    /**
     * @return The height of the sprite
     */
    public double getHeight() {
        return height;
    }

    /**
     * Sets the values to the width and to the height of the sprite
     *
     * @param width the value to be set to width
     * @param height the value to be set to height
     */
    public void setSize(double width, double height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Sets the values to the x and to the y coordinate of upper-left corner of
     * the sprite
     *
     * @param x the value to be set to x
     * @param y the value to be set to y
     */
    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Sets the values to the x and to the y coordinate of upper-left corner of
     * the sprite
     *
     * @param position the point in upper-left corner of the sprite
     */
    public void setPosition(Point2D position) {
        this.x = position.getX();
        this.y = position.getY();
    }

    /**
     * @return The point in upper-left corner of the sprite
     */
    public Point2D getCurrPos() {
        return new Point2D(x, y);
    }

    /**
     *
     * @param gc
     */
    public abstract void render(GraphicsContext gc);


    /**
     * Gets rectangle of sprite
     *
     * @return The rectangle that is size of sprite
     */
    public Rectangle2D getBounds() {
        return new Rectangle2D(x, y, width, height);
    }

    /**
     * Returns specific id if it is exist
     *
     * @return EnemyId for Enemy, ItemId for Item, ElementId for Element
     */
    public Ids getSpecificId() {
        return null;
    }

}
