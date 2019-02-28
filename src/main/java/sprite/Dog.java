/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprite;

import static main.Utils.*;
import ids.SpriteId;
import managers.Inventory;
import sprite.floor.FloorLine;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author dariatunina
 */
public class Dog extends LivingSprite {

    private Point2D initPoint;
    private boolean jumping;
    private boolean standing;
    private boolean attacking;
    private boolean using;
    private boolean movingLeft;
    private boolean movingRight;
    private boolean digging;
    private boolean seen;
    private final Inventory inventory;
    private Point2D lastStandPos;

    /**
     * Creates a new instance of Dog
     *
     * @param id The name of the dog
     * @param image The image of the dog
     * @param width the width of the dog
     * @param height the height of the dog
     * @param initLife the initial life of the dog
     * @param force the strength of dog's attack
     */
    public Dog(SpriteId id, String image, double width,
            double height, double initLife, double force, String level) {
        super(id, image, width, height, initLife, force);

        standing = false;
        attacking = false;
        using = false;
        movingLeft = false;
        movingRight = false;
        seen = true;
        inventory = new Inventory();
        lastStandPos = getCurrPos();
        try {
            File file = new File(level + "dog.txt");
            Scanner sc = new Scanner(file);
            sc.useLocale(Locale.ENGLISH);
            if (sc.hasNext()) {
                initPoint = new Point2D(sc.nextDouble(), sc.nextDouble());
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Dog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @return True if dog should be painted, false if not
     */
    public boolean isVisable() {
        return seen;
    }

    /**
     *
     * @param visable indicates if dog should be painted or not
     */
    public void setVisable(boolean visable) {
        this.seen = visable;
    }

    /**
     *
     * @return The last upper-left point of dog in which dog was on the ground
     */
    public Point2D getLastStandPos() {
        return lastStandPos;
    }

    /**
     * Sets the specific point
     *
     * @param lastStandPos the last upper-left point of dog in which dog was on
     * the ground
     */
    public void setLastStandPos(Point2D lastStandPos) {
        this.lastStandPos = lastStandPos;
    }

    /**
     *
     * @return True if 'Z' was pressed, false if not
     */
    public boolean isUsing() {
        return using;
    }

    /**
     * Sets true if 'Z' was pressed, false if not
     *
     * @param using the variable that tells if 'Z' was pressed or not
     */
    public void setUsing(boolean using) {
        this.using = using;
    }

    /**
     *
     * @return True if 'C' was pressed, false if not
     */
    public boolean isDigging() {
        return digging;
    }

    /**
     * Sets true if 'C' was pressed, false if not
     *
     * @param digging the variable that tells if 'C' was pressed or not
     */
    public void setDigging(boolean digging) {
        this.digging = digging;
    }

    /**
     *
     * @return the inventory that dog can manage
     * @see Inventory
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     *
     * @return True if 'X' was pressed, false if not
     */
    public boolean isAttacking() {
        return attacking;
    }

    /**
     * Sets true if 'X' was pressed, false if not
     *
     * @param attacking the variable that tells if 'X' was pressed or not
     */
    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    /**
     *
     * @return True if 'LEFT ARROW KEY' was pressed, false if not
     */
    public boolean isMovingLeft() {
        return movingLeft;
    }

    /**
     * Sets true if 'LEFT ARROW KEY' was pressed, false if not
     *
     * @param movingLeft the variable that tells if 'LEFT ARROW KEY' was pressed
     * or not
     */
    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    /**
     *
     * @return True if 'RIGHT ARROW KEY' was pressed, false if not
     */
    public boolean isMovingRight() {
        return movingRight;
    }

    /**
     * Sets true if 'RIGHT ARROW KEY' was pressed, false if not
     *
     * @param movingRight the variable that tells if 'RIGHT ARROW KEY' was
     * pressed or not
     */
    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    /**
     * Paints image of dog's attack. Depends on the side dog is facing.
     *
     * @param gc graphic context of GameCanvas
     */
    public void drawAttack(GraphicsContext gc) {
        double change = !isFacingLeft() ? 0 : this.getWidth();
        int side = !isFacingLeft() ? -1 : 1;
        gc.drawImage(ATTACK,
                this.getX() + this.getMoveX() + change,
                this.getY() + this.getMoveY() - DOG_ATTACK_HEIGHT / 2 + 10,
                side * DOG_ATTACK_WIDTH, DOG_ATTACK_HEIGHT);
    }

    /**
     *
     * @return True if the dog is on the ground, false if it's in the air.
     */
    public boolean isStanding() {
        return standing;
    }

    /**
     * Sets true if the dog on the ground, false if it's in the air.
     *
     * @param standing variable that tells if the dog is on the ground or not
     */
    public void setStanding(boolean standing) {
        this.standing = standing;
    }

    /**
     *
     * @return True if 'SPACE' was pressed, false otherwise
     */
    public boolean isJumping() {
        return jumping;
    }

    /**
     * Sets true if 'SPACE' was pressed, false otherwise
     *
     * @param isJumping variable that tells if 'SPACE' was pressed
     */
    public void setJumping(boolean isJumping) {
        this.jumping = isJumping;
    }

    /**
     * The dog changes position to the one that higher
     */
    public void jumpUp() {
        if (this.getMoveY() != 0 && standing) {
            this.setMoveY(-JUMP);
        }
    }

    /**
     * The dog is under the gravity influence. Changes position to the one that
     * lower
     */
    public void gravityForce() {
        this.setMoveY(this.getMoveY() + GRAVITATION);
    }

    /**
     * Return the range of dog's attack
     *
     * @return the range of dog's attack
     */
    public Rectangle2D getAttackBounds() {
        return new Rectangle2D(
                this.getX() + this.getMoveX()
                + (!this.isFacingLeft() ? -DOG_ATTACK_WIDTH : 0),
                this.getY() + this.getMoveY() - DOG_ATTACK_HEIGHT / 2 + 10,
                this.getWidth() + DOG_ATTACK_WIDTH,
                DOG_ATTACK_HEIGHT);
    }

    /**
     * Sets all variables that show if one or another key was pressed to false
     */
    public void stop() {
        jumping = false;
        attacking = false;
        using = false;
        movingLeft = false;
        movingRight = false;
        digging = false;
    }

    @Override
    public void moveLeft() {
        this.setMoveX(-MOVE_SPEED);
        movingLeft = true;
    }

    @Override
    public void moveRight() {
        this.setMoveX(MOVE_SPEED);
        movingRight = true;
    }

    /**
     * Sets all parameters to initial state
     */
    public void resetToInit() {
        this.setPosition(initPoint);
        this.setMoveY(GRAVITATION);
        this.setLife(DOG_LIFE);
        seen = true;
        getInventory().empty();
    }

    /**
     * s
     * If the dog floats in the air, than it'll be under influence of gravity
     *
     * @param line the specific line of the floor
     * @param inTheAir variable that tells if dog floats in the air
     */
    public void fallingCheck(FloorLine line, boolean inTheAir) {
        if ((!standing || inTheAir) && this.getMoveY() == 0
                && !line.getBounds().intersects(
                        this.getBounds(this.getMoveX(), FLR_SQR_SIZE))) {
            this.setMoveY(GRAVITATION);
        }
    }

    @Override
    public void checkCollisionsOnX(FloorLine line) {
        if (this.getMoveX() != 0
                && line.getBounds().intersects(
                        this.getBounds(this.getMoveX(), 0))) {
            this.setX(this.getMoveX() > 0
                    ? line.getMinX() - 1 - this.getWidth() : line.getMaxX() + 1);
            this.setMoveX(0);
        }
    }

    /**
     * Tests if the dog will interact with the specific line. Decides which
     * direction to move on y, if interacts.
     *
     * @param line the specific line of the floor
     * @param inTheAir variable that tells if dog floats in the air
     * @return True if the dog floats in the air, false otherwise
     */
    public boolean checkCollissionsOnY(FloorLine line, boolean inTheAir) {
        if (line.getBounds().intersects(this.getBounds(0, this.getMoveY()))) {
            inTheAir = false;
            if (this.getMoveY() < 0) {
                this.setMoveY(GRAVITATION);
                this.setY(line.getY() + FLR_SQR_SIZE + 0.1);
                standing = false;
                extraCheckY(line);
            } else {
                this.setMoveY(0);
                standing = true;
            }
        }
        return inTheAir;
    }

    private void extraCheckY(FloorLine line) {
        if (line.getBounds().intersects(this.getBounds(0, this.getMoveY()))) {
            this.setY(line.getY() - this.getHeight() - 0.1);
            standing = true;
        }
    }

    public void changeLevel(int level) {
        try {
            File file = new File("level_" + String.valueOf(level) + "/dog.txt");
            Scanner sc = new Scanner(file);
            sc.useLocale(Locale.ENGLISH);
            initPoint = new Point2D(sc.nextDouble(), sc.nextDouble());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Dog.class.getName()).log(Level.SEVERE, null, ex);
        }
        resetToInit();
    }
}
