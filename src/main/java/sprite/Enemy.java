/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprite;

import main.Variables;
import static main.Variables.PAUSE;
import static main.Utils.*;
import ids.EnemyId;
import ids.SpriteId;
import runnable.AttentionRunnable;
import sprite.floor.FloorLine;
import static java.lang.Math.abs;
import static java.lang.Math.round;
import java.util.Map;
import java.util.Random;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

/**
 *
 * @author dariatunina
 */
public class Enemy extends LivingSprite {

    private boolean dead;
    private final EnemyId enemyId;
    private Rectangle2D controlTerr;
    private double basicMoveX, basicMoveY;
    private final double contrTerrWidth;
    private final double contrTerrHeight;
    private boolean noticed;
    

    /**
     * Creates new enemy by using Sprite 's constructor
     *
     * @param id sprite id of the enemy
     * @param image image of the enemy
     * @param width the width of the enemy
     * @param height the height of the enemy
     * @param enemyId specific id for enemy
     * @param initLife the initial life of the enemy
     * @param speed the speed of the enemy
     * @param force the strength of enemy's attack
     * @param contrTerrWidth indicates half of the width of controlling
     * territory
     * @param contrTerrHeight indicates half of the height of controlling
     * territory
     */
    public Enemy(SpriteId id, String image, double width,
            double height, EnemyId enemyId, double initLife, double force, double speed,
            double contrTerrWidth, double contrTerrHeight) {
        super(id, image, width, height, initLife, force);
        int[] side = {-1, 1};
        int rnd = new Random().nextInt(2);
        dead = false;
        noticed = false;
        basicMoveX = speed * side[rnd];
        basicMoveY = 0;
        setMoveX(basicMoveX);
        setMoveY(basicMoveY);
        this.contrTerrWidth = contrTerrWidth;
        this.contrTerrHeight = contrTerrHeight;
        this.enemyId = enemyId;
        setControlTerr();
    }

    /**
     * Getter for noticed
     *
     * @return value of variable noticed
     */
    public boolean hasNoticed() {
        return noticed;
    }

    /**
     * Setter for noticed
     *
     * @param noticed indicates existence of dog in enemy's controlling
     * territory
     */
    public void setNoticed(boolean noticed) {
        this.noticed = noticed;
    }

    /**
     * Getter for basicMoveX
     *
     * @return value of variable basicMoveX
     */
    public double getBasicMoveX() {
        return basicMoveX;
    }

    /**
     * Setter for basicMoveX
     *
     * @param basicMoveX how enemy moves without influence of external factors
     */
    public void setBasicMoveX(double basicMoveX) {
        this.basicMoveX = basicMoveX;
    }

    /**
     * Getter for basicMoveY
     *
     * @return value of variable basicMoveY
     */
    public double getBasicMoveY() {
        return basicMoveY;
    }

    /**
     * Setter for basicMoveY
     *
     * @param basicMoveY indicates how enemy moves without influence of external
     * factors
     */
    public void setBasicMoveY(double basicMoveY) {
        this.basicMoveY = basicMoveY;
    }

    private void setControlTerr() {
        controlTerr = new Rectangle2D(
                this.getX() - contrTerrWidth,
                this.getY() - contrTerrHeight,
                this.getWidth() + 2 * contrTerrWidth,
                this.getHeight() + 2 * contrTerrHeight);
    }

    /**
     * Getter for dead
     *
     * @return value of variable dead
     */
    public boolean isDead() {
        return dead;
    }

    /**
     * Setter for dead
     *
     * @param dead indicates if enemy should be on screen or not
     */
    public void setDead(boolean dead) {
        this.dead = dead;
    }

    /**
     * Reverse moveX and basicMoveX
     */
    public void changeDirectionX() {
        this.setMoveX(-this.getMoveX());
        basicMoveX = -basicMoveX;
    }

    /**
     * Reverse moveY and basicMoveY
     */
    public void changeDirectionY() {
        this.setMoveY(-this.getMoveY());
        basicMoveY = -basicMoveY;
    }

    /**
     * Update controlling territory. Enemy move towards the dog, if it's within
     * controlling territory. Enemy continue moving on his track if dog is not
     * (no longer) within controlling territory.
     *
     * @param dog main character
     */
    public void controllingTerr(Dog dog) {
        setControlTerr();
        if (!this.getBounds().intersects(dog.getBounds())) {
            if (controlTerr.intersects(dog.getBounds())) {
                double dogX = dog.getCenterX();
                double dogY = dog.getY() + dog.getHeight();
                double slope = (dogY - this.getCenterY())
                        / (dogX - this.getCenterX());

                if (round(dogX) == round(this.getCenterX())) {
                    this.setMoveX(0);
                    this.setMoveY(dogY < this.getCenterY() ? -0.6 : 0.6);
                } else {
                    if (slope > 1) {
                        slope = 1 / slope;
                        this.setMoveY(calculations(
                                dogY > this.getCenterY() ? 1 : -1, slope));
                        this.setMoveX(this.getMoveY() * slope);
                    } else {
                        this.setMoveX(calculations(
                                dogX > this.getCenterX() ? 1 : -1, slope));
                        this.setMoveY(this.getMoveX() * slope);
                    }
                }
                noticed = true;
            } else {

                this.setMoveX(this.getBasicMoveX());
                this.setMoveY(this.getBasicMoveY());
                noticed = false;
            }
        }
    }

    private double calculations(double value, double slope) {
        while (abs(value * slope) > 1) {
            value /= 2;
        }
        return value;
    }

    @Override
    public void checkCollisionsOnX(FloorLine line) {
        if (line.getBounds().intersects(this.getBounds(this.getMoveX(), 0))) {
            changeDirectionX();
        }
    }

    @Override
    public void checkCollisionsOnY(FloorLine line) {
        if (line.getBounds().intersects(this.getBounds(0, this.getMoveY()))) {
            changeDirectionY();
        }
    }

    /**
     * Paint attention icon on the screen.
     *
     * @param gc graphic context of GameCanvas
     * @param gameVariables variables that can help indicate stage that game in
     */
    public void drawAttentionIcon(GraphicsContext gc,
            Map<Variables, Boolean> gameVariables) {
        gc.drawImage(ATTENTION_IMG, this.getCenterX(),
                this.getY() - 20, 15, 15);
        if (!gameVariables.get(PAUSE)) {
            Thread th = new Thread(new AttentionRunnable(gameVariables, this));
            th.start();
        }
    }

    @Override
    public EnemyId getSpecificId() {
        return enemyId;
    }

    @Override
    public String toString() {
        return enemyId.name() + " on " + this.getX() + ", " + this.getY();
    }

}
