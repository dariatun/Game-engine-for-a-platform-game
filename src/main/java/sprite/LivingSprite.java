/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprite;

import static main.Utils.*;
import ids.SpriteId;
import static ids.SpriteId.DOG;
import managers.ImageManager;
import sprite.floor.FloorLine;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

/**
 *
 * @author dariatunina
 */
public class LivingSprite extends Sprite {

    private Image img;
    private double moveX;
    private double moveY;
    private double life;
    private final double force;
    private boolean facingLeft;
    private final boolean imgFacing;
    private double initLife;
    private final SpriteAnimation sprAnim;
    private final ImageManager imgMan;

    /**
     * Creates a new instance of LivingSprite
     *
     * @param id the name of the sprite
     * @param fileName the fileName of the sprite
     * @param width the width of the sprite
     * @param height the height of the sprite
     * @param initLife the initial life of the sprite
     * @param force the strength of sprite's attack
     */
    public LivingSprite(
            SpriteId id,
            String fileName,
            double width,
            double height,
            double initLife,
            double force) {
        super(id, width, height);
        imgMan = new ImageManager(fileName);
        sprAnim = new SpriteAnimation(imgMan.getImgsLine());
        moveX = 0;
        moveY = 0;
        imgFacing = imgMan.getFacing();
        facingLeft = imgFacing;
        this.initLife = initLife;
        life = initLife;
        this.force = force;
    }

    public SpriteAnimation getSprAnim() {
        return sprAnim;
    }

    /**
     *
     * @return True if sprite is moving left or was moving left before it
     * stopped, false otherwise
     */
    public boolean isFacingLeft() {
        return facingLeft;
    }

    /**
     * Decides if sprite is looking lest or right
     */
    public void updtFacingSide() {
        if (moveX > 0) {
            facingLeft = imgFacing;
        } else if (moveX < 0) {
            facingLeft = !imgFacing;
        }
    }

    /**
     *
     * @return the value of sprite's attack
     */
    public double getForce() {
        return force;
    }

    /**
     *
     * @return The value of sprite's health
     */
    public double getLife() {
        return life;
    }

    /**
     * Sets the value of sprite's health
     *
     * @param life the value of sprite's health
     */
    public void setLife(double life) {
        initLife = life;
        this.life = life;
    }

    /**
     * Adds specific value to the sprite's life
     *
     * @param amount the specific value
     */
    public void changeLife(double amount) {
        life += amount;
    }

    /**
     * Sets specific value to the x boost variable of sprite
     *
     * @param moveX the boost for sprite on x
     */
    public void setMoveX(double moveX) {
        this.moveX = moveX;
    }

    /**
     * Sets specific value to the y boost variable of sprite
     *
     * @param moveY the boost for sprite on y
     */
    public void setMoveY(double moveY) {
        this.moveY = moveY;
    }

    /**
     *
     * @return the boost for sprite on x
     */
    public double getMoveX() {
        return moveX;
    }

    /**
     *
     * @return the boost for sprite on y
     */
    public double getMoveY() {
        return moveY;
    }

    /**
     * Sets the x boost of sprite to the specific negative value
     */
    public void moveLeft() {
        moveX = -MOVE_SPEED;
    }

    /**
     * Sets the x boost of sprite to the specific positive value
     */
    public void moveRight() {
        moveX = MOVE_SPEED;
    }

    @Override
    public Rectangle2D getBounds() {
        return new Rectangle2D(this.getX() + moveX,
                this.getY() + moveY,
                this.getWidth(), this.getHeight());
    }

    /**
     * Gets rectangle of sprite with the specific x and y boosts
     *
     * @param moveX the x boost
     * @param moveY the y boost
     * @return The rectangle that is size of sprite
     */
    public Rectangle2D getBounds(double moveX, double moveY) {
        return new Rectangle2D(this.getX() + moveX,
                this.getY() + moveY,
                this.getWidth(), this.getHeight());
    }

    @Override
    public void render(GraphicsContext gc) {
        if (facingLeft) {
            gc.drawImage(img, this.getX() + this.getWidth(),
                    this.getY(), -this.getWidth(), this.getHeight());
        } else {
            gc.drawImage(img, this.getX(),
                    this.getY(), this.getWidth(), this.getHeight());
        }
    }

    /**
     * Displays life bar of the sprite. Life bar is small and is above sprite
     * for Enemy. Life bar is bigger and is in the upper-left corner of the
     * screen for Dog.
     *
     * @param gc graphic context of GameCanvas
     */
    public void displayLife(GraphicsContext gc) {
        double imgX = this.getSpriteId() == DOG
                ? 10 : (this.getX() - 7 > 0 ? this.getX() - 7 : 0);
        double imgY = this.getSpriteId() == DOG
                ? 10 : (this.getY() - LIFE_BAR_HEIGHT - 5 > 0
                ? this.getY() - LIFE_BAR_HEIGHT - 5 : 0);
        double imgWidth = (life * LIFE_BAR_FULL_IMG.getWidth()) / initLife;
        double scale = this.getSpriteId() == DOG ? 1 : 0.333;
        Image imgBar = this.getSpriteId() == DOG
                ? LIFE_BAR_FULL_IMG : LIFE_BAR_FULL_RED_IMG;
        gc.drawImage(LIFE_BAR_BLANK, imgX, imgY,
                LIFE_BAR_WIDTH * scale, LIFE_BAR_HEIGHT * scale);
        if (life > 0 && (int) imgWidth > 0 && (int) imgBar.getHeight() > 0) {
            WritableImage wrImg = new WritableImage(imgBar.getPixelReader(),
                    0,
                    0,
                    (int) imgWidth,
                    (int) imgBar.getHeight()
            );
            gc.drawImage(wrImg, imgX, imgY,
                    (imgWidth * LIFE_BAR_WIDTH / LIFE_BAR_FULL_IMG.getWidth()) * scale,
                    LIFE_BAR_HEIGHT * scale);
        }
    }

    /**
     * Tests if the dog will interact with the specific line. Decides which
     * direction to move on x, if interacts.
     *
     * @param line the specific line of the floor
     */
    public void checkCollisionsOnX(FloorLine line) {
    }

    /**
     * Tests if the dog will interact with the specific line. Decides which
     * direction to move on y, if interacts.
     *
     * @param line the specific line of the floor
     */
    public void checkCollisionsOnY(FloorLine line) {
    }

    public void updateImg() {
        sprAnim.changeImg();
        img = sprAnim.getImg();
    }
    
}
