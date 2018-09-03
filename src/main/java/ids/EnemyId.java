/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ids;

import static main.Utils.*;

/**
 *
 * @author dariatunina
 */
public enum EnemyId implements Ids {
    BAT(0),
    SLIME(1);

    private final int num;

    private EnemyId(int num) {
        this.num = num;
    }

    /**
     *
     * @return The speed of the sprite that is related with this id
     */
    public double getSpeed() {
        return ENEMIES_SPEED[num];
    }

    /**
     *
     * @return The force of the sprite that is related with this id
     */
    public double getForce() {
        return ENEMIES_FORCE[num];
    }

    /**
     *
     * @return Half of the width of enemy's controlling territory
     */
    public double getContrTerrWidth() {
        return CONTR_TERR_WIDTH[num];
    }

    /**
     *
     * @return Half of the height of enemy's controlling territory
     */
    public double getContrTerrHeight() {
        return CONTR_TERR_HEIGHT[num];
    }

    /**
     *
     * @return The value of enemy's health
     */
    public double getLife() {
        return ENEMIES_LIFE[num];
    }
    
    @Override
    public double getWidth() {
        return ENEMIES_WIDTH[num];
    }

    @Override
    public double getHeight() {
        return ENEMIES_HEIGHT[num];
    }

    public String getImage() {
        return ENEMIES_IMG[num];
    }

    @Override
    public SpriteId getSpriteId() {
        return SpriteId.ENEMY;
    }

}
