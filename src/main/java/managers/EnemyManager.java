/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managers;

import ids.EnemyId;
import ids.Ids;
import ids.SpriteId;
import sprite.Enemy;
import sprite.Sprite;

/**
 *
 * @author dariatunina
 */
public class EnemyManager extends BasicManager {

    /**
     * Creates a new instance of EnemyManager
     *
     * @param classId the type of the sprite (EnemyId)
     * @param fileName specific name of the file where are the initial enemies
     */
    public EnemyManager(Class classId, String fileName) {
        super(classId, fileName);
    }

    @Override
    public Enemy[] getSprites() {
        return this.getSpriteMap().values().toArray(
                new Enemy[getSpriteMap().size()]);
    }

    @Override
    public void putSprite(String name, Ids id) {
        getSpriteMap().put(name, new Enemy(
                SpriteId.ENEMY,
                ((EnemyId)id).getImage(),
                id.getWidth(),
                id.getHeight(),
                (EnemyId) id,
                ((EnemyId) id).getLife(),
                ((EnemyId) id).getForce(),
                ((EnemyId) id).getSpeed(),
                ((EnemyId) id).getContrTerrWidth(),
                ((EnemyId) id).getContrTerrHeight())
        );
    }

    @Override
    public void setAdditionalPar(Sprite sprite) {
    }

}
